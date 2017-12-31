package com.mykmovies.android.japaneselearners;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private AudioManager mAudioManager;
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };
    /**
     * Creating mCompletionListener to release the audio resources
     */
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family);
        String message = getIntent().getStringExtra("family_header_key"); // Now, message has Drawer title
        setTitle(message);
        mAudioManager = (AudioManager) getSystemService(this.AUDIO_SERVICE);
        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("おとうさん", "Father", R.drawable.family_father, R.raw.family_father));
        words.add(new Word("おかあさん", "Mother", R.drawable.family_mother, R.raw.family_mother));
        words.add(new Word("おじいさん", "Grand Father", R.drawable.family_grandfather, R.raw.family_grandfather));
        words.add(new Word("おばあさん", "Grand Mother", R.drawable.family_grandmother, R.raw.family_grandmother));
        words.add(new Word("むすめ", "Daughter", R.drawable.family_daughter, R.raw.family_daughter));
        words.add(new Word("むすこ", "Son", R.drawable.family_son, R.raw.family_son));
        words.add(new Word("おにいさん", "Older Brother", R.drawable.family_older_brother, R.raw.family_older_brother));
        words.add(new Word("おねさん", "Older Sister", R.drawable.family_older_sister, R.raw.family_older_sister));
        words.add(new Word("おとうとさん", "Younger Brother", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        words.add(new Word("いもうとうさん", "Younger Sister", R.drawable.family_younger_sister, R.raw.family_younger_sister));

        WordAdapter wordAdapter = new WordAdapter(this, words, R.color.family_activity_bg_color);
        ListView listView = (ListView) findViewById(R.id.activity_family_listview);
        listView.setAdapter(wordAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = words.get(position);
                releaseMediaPlayer();
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN);
                int result1 = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_RING,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED || result1 == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // Start playback.

                    mediaPlayer = MediaPlayer.create(FamilyActivity.this, word.getmAudioResourceId());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }

        });

    }

    /**
     * Release audio resource once you stop the app
     * override the onStop method
     */
    protected void onStop() {
        super.onStop();// ATTENTION: This was auto-generated to implement the App Indexing API.
        releaseMediaPlayer();
        ;
    }

    /**
     * Cleanup MediaPlayer resources
     * After completion of audio resource playing clearning the audio resources
     */
    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            /**
             * Setting mediaPlayer object to null, the resources has been released
             * will start back with new resource everytime
             */
            mediaPlayer = null;
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }


}

