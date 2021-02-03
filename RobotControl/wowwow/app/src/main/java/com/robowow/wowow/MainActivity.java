package com.robowow.wowow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.robowow.wowow.R;

public class MainActivity extends AppCompatActivity {
    Button cctv,playing,move;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cctv = findViewById(R.id.cctv);
        playing = findViewById(R.id.playing);
        move = findViewById(R.id.move);

        cctv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,cctvActivity.class);
                startActivity(intent);
            }
        });
        playing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,playingActivity.class);
                startActivity(intent);
            }
        });
        move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,moveActivity.class);
                startActivity(intent);
            }
        });

    }

}