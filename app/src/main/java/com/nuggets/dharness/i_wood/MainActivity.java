package com.nuggets.dharness.i_wood;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends Activity{

    protected static final int REQUEST_OK = 1;
    MediaPlayer mediaPlayer;
    MediaPlayer giggle_mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getActionBar();
        actionBar.setElevation(0);
        actionBar.setDisplayShowTitleEnabled(false);
        findViewById(R.id.btn_ask).setOnClickListener(questionHandler);
        findViewById(R.id.btn_poke).setOnClickListener(pokeHandler);
        this.mediaPlayer = MediaPlayer.create(this, R.raw.iwould);
        this.giggle_mediaPlayer = MediaPlayer.create(this, R.raw.giggle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    View.OnClickListener questionHandler = new View.OnClickListener() {
        public void onClick(View v) {
            Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
            try {
                startActivityForResult(i, REQUEST_OK);
            } catch (Exception e) {
//                Toast.makeText(questionHandler, "Error initializing speech to text engine.", Toast.LENGTH_LONG).show();
            }
        }
    };

    View.OnClickListener pokeHandler = new View.OnClickListener() {
        public void onClick(View v) {
            giggle_mediaPlayer.start();
        }
    };



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_OK  && resultCode==RESULT_OK) {
            ArrayList<String> thingsYouSaid = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            String questionForIan = thingsYouSaid.get(0);
            ((TextView)findViewById(R.id.fname)).setText(questionForIan);
            if(questionForIan.contains("wood") || questionForIan.contains("would") || questionForIan.contains("Wood") || questionForIan.contains("Would")){
                this.mediaPlayer.start();
            }

        }
    }
}
