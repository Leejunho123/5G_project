package com.robowow.wowow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.robowow.wowow.R;

public class playingActivity extends AppCompatActivity {
    private Toolbar toolbar;
    Button voice_mode, recording, tv_on, tv_off,channel_button,baby_shark, birthDay, pororo, tong;
    ImageButton pause_button;
    EditText channel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);

        voice_mode = findViewById(R.id.voice_mode);

        tv_on = findViewById(R.id.tv_on);
        tv_off = findViewById(R.id.tv_off);
        channel_button = findViewById(R.id.channel_move);
        channel = findViewById(R.id.channel);

        baby_shark = findViewById(R.id.baby_shark);
        birthDay = findViewById(R.id.birthDay);
        pororo = findViewById(R.id.pororo);
        tong = findViewById(R.id.tong);
        pause_button = findViewById(R.id.pause_button);

        recording = findViewById(R.id.recording);

        voice_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                common_menu.getIPandPort();
                common_menu.CMD="Z";
                common_menu.Socket_AsyncTask socket_on = new common_menu.Socket_AsyncTask();
                socket_on.execute();
            }
        });

        recording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(playingActivity.this, MainRecordingActivity.class);
                startActivity(intent);
            }
        });

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////TV
        tv_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                common_menu.getIPandPort();
                common_menu.CMD="U";
                common_menu.Socket_AsyncTask socket_on = new common_menu.Socket_AsyncTask();
                socket_on.execute();
            }
        });

        tv_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                common_menu.getIPandPort();
                common_menu.CMD="V";
                common_menu.Socket_AsyncTask socket_on = new common_menu.Socket_AsyncTask();
                socket_on.execute();
            }
        });

        channel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String channel_number = "T"+ channel.getText().toString();
                common_menu.CMD = channel_number;
                common_menu.Socket_AsyncTask socket_on = new common_menu.Socket_AsyncTask();
                socket_on.execute();
            }
        });


//////////////////////////////////////////////////////////////////////////////////////////////////////////////Music
        baby_shark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                common_menu.getIPandPort();
                common_menu.CMD="M";
                common_menu.Socket_AsyncTask socket_on = new common_menu.Socket_AsyncTask();
                socket_on.execute();
            }
        });
        birthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                common_menu.getIPandPort();
                common_menu.CMD="O";
                common_menu.Socket_AsyncTask socket_on = new common_menu.Socket_AsyncTask();
                socket_on.execute();
            }
        });
        pororo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                common_menu.getIPandPort();
                common_menu.CMD="P";
                common_menu.Socket_AsyncTask socket_on = new common_menu.Socket_AsyncTask();
                socket_on.execute();
            }
        });
        tong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                common_menu.getIPandPort();
                common_menu.CMD="Q";
                common_menu.Socket_AsyncTask socket_on = new common_menu.Socket_AsyncTask();
                socket_on.execute();
            }
        });
        pause_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                common_menu.getIPandPort();
                common_menu.CMD="N";
                common_menu.Socket_AsyncTask socket_on = new common_menu.Socket_AsyncTask();
                socket_on.execute();
            }
        });


////////////////////////////////////////////////////////////////////////////////////////////////////////toolbar
        /** setting up the toolbar  **/
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Playing");
        toolbar.setTitleTextColor(getColor(R.color.purple_200));
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}