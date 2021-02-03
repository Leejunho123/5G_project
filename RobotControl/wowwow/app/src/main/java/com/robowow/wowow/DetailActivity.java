package com.robowow.wowow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.robowow.wowow.R;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class DetailActivity extends AppCompatActivity {

    Button default_mode;
    ImageButton stop,forward,backward,left,right,left_up,left_down,right_up,right_down;

    EditText ipAddress;
    public static String wifiModuleIP = "";
    public static int wifiModulePort = 0;
    public static String CMD = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ipAddress = findViewById(R.id.ipAddress);
        default_mode = findViewById(R.id.default_mode);
        stop = findViewById(R.id.stop);
        left_up = findViewById(R.id.left_up);
        left_down = findViewById(R.id.left_down);
        right_up = findViewById(R.id.right_up);
        right_down = findViewById(R.id.right_down);
        left =  findViewById(R.id.left);
        right =  findViewById(R.id.right);
        forward = findViewById(R.id.forward);
        backward = findViewById(R.id.backward);

        default_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(DetailActivity.this,moveActivity.class);
                startActivity(intent2);
            }
        });
        Intent intent = getIntent();
        String str = intent.getStringExtra("str");
        ipAddress.setText(str);



        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getIPandPort();
                CMD="7";
                DetailActivity.Socket_AsyncTask socket_on = new DetailActivity.Socket_AsyncTask();
                socket_on.execute();
            }
        });
        backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getIPandPort();
                CMD="8";
                DetailActivity.Socket_AsyncTask socket_on = new DetailActivity.Socket_AsyncTask();
                socket_on.execute();
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getIPandPort();
                CMD="0";
                DetailActivity.Socket_AsyncTask socket_on = new DetailActivity.Socket_AsyncTask();
                socket_on.execute();
            }
        });

        left_up.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN)
                {
                    left_up.setBackgroundResource(R.drawable.d_select_cross);
                    getIPandPort();
                    CMD="A";
                    DetailActivity.Socket_AsyncTask socket_on = new DetailActivity.Socket_AsyncTask();
                    socket_on.execute();
                }
                else if(event.getAction()==MotionEvent.ACTION_UP)
                {
                    left_up.setBackgroundResource(R.drawable.d_cross_button);
                    getIPandPort();
                    CMD="S";
                    DetailActivity.Socket_AsyncTask socket_on = new DetailActivity.Socket_AsyncTask();
                    socket_on.execute();
                }
                return true;
            }
        });
        left_down.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN)
                {
                    left_down.setBackgroundResource(R.drawable.d_select_cross);
                    getIPandPort();
                    CMD="B";
                    DetailActivity.Socket_AsyncTask socket_on = new DetailActivity.Socket_AsyncTask();
                    socket_on.execute();
                }
                else if(event.getAction()==MotionEvent.ACTION_UP)
                {
                    left_down.setBackgroundResource(R.drawable.d_cross_button);
                    getIPandPort();
                    CMD="S";
                    DetailActivity.Socket_AsyncTask socket_on = new DetailActivity.Socket_AsyncTask();
                    socket_on.execute();
                }
                return true;
            }
        });
        right_up.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN)
                {
                    right_up.setBackgroundResource(R.drawable.d_select_cross);
                    getIPandPort();
                    CMD="C";
                    DetailActivity.Socket_AsyncTask socket_on = new DetailActivity.Socket_AsyncTask();
                    socket_on.execute();
                }
                else if(event.getAction()==MotionEvent.ACTION_UP)
                {
                    right_up.setBackgroundResource(R.drawable.d_cross_button);
                    getIPandPort();
                    CMD="S";
                    DetailActivity.Socket_AsyncTask socket_on = new DetailActivity.Socket_AsyncTask();
                    socket_on.execute();
                }
                return true;
            }
        });
        right_down.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN)
                {
                    right_down.setBackgroundResource(R.drawable.d_select_cross);
                    getIPandPort();
                    CMD="D";
                    DetailActivity.Socket_AsyncTask socket_on = new DetailActivity.Socket_AsyncTask();
                    socket_on.execute();
                }
                else if(event.getAction()==MotionEvent.ACTION_UP)
                {
                    right_down.setBackgroundResource(R.drawable.d_cross_button);
                    getIPandPort();
                    CMD="S";
                    DetailActivity.Socket_AsyncTask socket_on = new DetailActivity.Socket_AsyncTask();
                    socket_on.execute();
                }
                return true;
            }
        });
        left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN)
                {
                    left.setBackgroundResource(R.drawable.d_press_button);
                    getIPandPort();
                    CMD="E";
                    DetailActivity.Socket_AsyncTask socket_on = new DetailActivity.Socket_AsyncTask();
                    socket_on.execute();

                }
                else if(event.getAction()==MotionEvent.ACTION_UP)
                {
                    left.setBackgroundResource(R.drawable.d_arrow_button);
                    getIPandPort();
                    CMD="S";
                    DetailActivity.Socket_AsyncTask socket_on = new DetailActivity.Socket_AsyncTask();
                    socket_on.execute();
                }
                return true;
            }
        });
        right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN)
                {
                    right.setBackgroundResource(R.drawable.d_press_button);
                    getIPandPort();
                    CMD="F";
                    DetailActivity.Socket_AsyncTask socket_on = new DetailActivity.Socket_AsyncTask();
                    socket_on.execute();
                }
                else if(event.getAction()==MotionEvent.ACTION_UP)
                {
                    right.setBackgroundResource(R.drawable.d_arrow_button);
                    getIPandPort();
                    CMD="S";
                    DetailActivity.Socket_AsyncTask socket_on = new DetailActivity.Socket_AsyncTask();
                    socket_on.execute();
                }
                return true;
            }
        });




    }
    public  void getIPandPort(){
        String iPandPort = ipAddress.getText().toString();
        Log.d("MY TEST","IP String: "+ iPandPort);
        String temp[] = iPandPort.split(":");
        wifiModuleIP = temp[0];
        wifiModulePort = Integer.valueOf(temp[1]);
        Log.d("MY TEST", "IP:"+wifiModuleIP);
        Log.d("MY TEST", "PORT:"+wifiModulePort);
    }

    public class Socket_AsyncTask extends AsyncTask<Void,Void,Void> {
        Socket socket;
        @Override
        protected Void doInBackground(Void... params) {
            try{
                InetAddress inetAddress = InetAddress.getByName(moveActivity.wifiModuleIP);
                socket = new java.net.Socket(inetAddress, moveActivity.wifiModulePort);
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeBytes(CMD);
                dataOutputStream.close();
                socket.close();
            }
            catch (UnknownHostException e){e.printStackTrace();}
            catch (IOException e){e.printStackTrace();}

            return  null;
        }
    }

}