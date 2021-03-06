package com.mykmovies.android.japaneselearners;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {
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
    private  MediaPlayer.OnCompletionListener mCompletionListener=new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);
        String message = getIntent().getStringExtra("numbers_header_key"); // Now, message has Drawer title
        setTitle(message);
        mAudioManager = (AudioManager) getSystemService(this.AUDIO_SERVICE);
        final ArrayList<Word> words=new ArrayList<Word>();
        words.add(new Word("いち","One",R.drawable.number_one,R.raw.number_one));
        words.add(new Word("に","Two",R.drawable.number_two,R.raw.number_two));
        words.add(new Word("さん","Three",R.drawable.number_three,R.raw.number_three));
        words.add(new Word("よん","Four",R.drawable.number_four,R.raw.number_four));
        words.add(new Word("ご","Five",R.drawable.number_five,R.raw.number_five));
        words.add(new Word("ろく","Six",R.drawable.number_six,R.raw.number_six));
        words.add(new Word("なな","Seven",R.drawable.number_seven,R.raw.number_seven));
        words.add(new Word("はち","Eight",R.drawable.number_eight,R.raw.number_eight));
        words.add(new Word("きゅう","Nine",R.drawable.number_nine,R.raw.number_nine));
        words.add(new Word("じゅう","Ten",R.drawable.number_ten,R.raw.number_ten));

        final WordAdapter wordAdapter=new WordAdapter(this,words,R.color.number_activity_bg_color);
        ListView listView = (ListView) findViewById(R.id.activity_numbers_listview);
        listView.setAdapter(wordAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word=words.get(position);
                /**
                 * Releasing media player before calling the MediaPlayer
                 */
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
                    mediaPlayer = MediaPlayer.create(NumbersActivity.this, word.getmAudioResourceId());
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
    protected void onStop()
    {
        super.onStop();
        releaseMediaPlayer();;
    }
    /**
    *Cleanup MediaPlayer resources
     * After completion of audio resource playing clearning the audio resources
     */
    private void releaseMediaPlayer()
    {
           if(mediaPlayer!=null)
           {
               mediaPlayer.release();
               /**
                * Setting mediaPlayer object to null, the resources has been released
                * will start back with new resource everytime
                */
               mediaPlayer=null;
               mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
           }
    }

    }
