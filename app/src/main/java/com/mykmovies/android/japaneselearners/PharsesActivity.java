package com.mykmovies.android.japaneselearners;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class PharsesActivity extends AppCompatActivity {
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
     * Once complete the audio playing, resource has been released
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
        setContentView(R.layout.activity_pharses);
        String message = getIntent().getStringExtra("pharses_header_key"); // Now, message has Drawer title
        setTitle(message);
        mAudioManager = (AudioManager) getSystemService(this.AUDIO_SERVICE);
        final ArrayList<Word> words=new ArrayList<Word>();
        words.add(new Word("すみません。","Excuse me",R.raw.phrases_excuseme));
        words.add(new Word("ごめんなさい。","I am sorry",R.raw.phrases_sorry));
        words.add(new Word("おはようございます。","Good morning.",R.raw.phrases_goodmorning));
        words.add(new Word("こんにちは","Good Afternoon.",R.raw.phrases_goodafternoon));
        words.add(new Word("こんばんは。","Good evening.",R.raw.phrases_goodevening));
        words.add(new Word("おやすみなさい。","Good Night",R.raw.phrases_goodnight));
        words.add(new Word("おなまえはなんですか。","What is your name?",R.raw.phrases_namequestion));
        words.add(new Word("わたしのなまえは　ヤスワントです。","My name is Yeswanth.",R.raw.phrases_nameanswer));
        words.add(new Word("おげんきですか?。","How are you?",R.raw.phrases_howareyou));
        words.add(new Word("はい、げんきです。","I'm fine. Thank you.",R.raw.phrases_howareyouanswer));
        words.add(new Word("おあいできて　うれしいです。","I am very glad to meet you.",R.raw.phrases_gladtomeet));

        WordAdapter wordAdapter=new WordAdapter(this,words,R.color.phrases_activity_bg_color);
        ListView listView = (ListView) findViewById(R.id.activity_pharses_listview);
        listView.setAdapter(wordAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word=words.get(position);
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

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED || result1 == AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
                {
                    mediaPlayer = MediaPlayer.create(PharsesActivity.this, word.getmAudioResourceId());
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

