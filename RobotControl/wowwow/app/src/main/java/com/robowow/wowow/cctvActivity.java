package com.robowow.wowow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.SeekBar;

import com.robowow.wowow.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class cctvActivity extends AppCompatActivity {
    private Toolbar toolbar;
    SeekBar seekBarupdown;
    SeekBar seekBarleftright;

    public static String wifiModuleIP = "";
    public static int wifiModulePort = 0;
    public static String CMD = "0";
    //    public static String CMD1 = "0";
    public static int angle = 0;
    public static int angle1 = 0;
    public static String ANGLE = "";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
//    EditText ipAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cctv);


//        ipAddress = findViewById(R.id.ipAddress);
        seekBarupdown = findViewById(R.id.seekBarupdown);
        seekBarleftright = findViewById(R.id.seekBarleftright);

        /** setting up the toolbar  **/
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home CCTV");
        toolbar.setTitleTextColor(getColor(R.color.purple_200));
        setSupportActionBar(toolbar);
        common_menu.getIPandPort();
        // 카메라 통신-----------------

        WebView webView = (WebView)findViewById(R.id.webView);//webView.setPadding(0,0,0,0);
        webView.setInitialScale(100); //  webView의 초기 배율 설정. 0 기본값
        webView.getSettings().setUseWideViewPort(true); // 기본 배율 동작 조정
        webView.getSettings().setLoadWithOverviewMode(true); // ture일 때 webview 컨트롤의 너비에 맞게 콘텐츠 축소

        webView.getSettings().setBuiltInZoomControls(false);  // 줌 작동
        webView.getSettings().setJavaScriptEnabled(true); // WebView에 JavaScript 실행을 사용하도록 지시

        // 0~40까지 신호를 줄때 값에 130을 더해서 전송
        String videoip = common_menu.wifiModuleIP;
        String url ="http://"+videoip+":8090/?action=stream";
        webView.loadUrl(url);


        // seekbar up down------------
        seekBarupdown.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged( SeekBar seekBar, int progress, boolean fromUser ) {
            }
            @Override
            public void onStartTrackingTouch( SeekBar seekBar ) {
            }
            @Override
            public void onStopTrackingTouch( SeekBar seekBar ) {
                common_menu.getIPandPort();
                angle = seekBar.getProgress()+130;
                System.out.println(angle);
                common_menu.CMD = String.format("X %d",angle);
                common_menu.Socket_AsyncTask socket_on = new common_menu.Socket_AsyncTask();
                socket_on.execute();
                DatabaseReference myRef = database.getReference("angle");
                myRef.setValue(angle);
            }
        });

//
//        public void onClick(View v) {
//            common_menu.getIPandPort();
//            common_menu.CMD="Q";
//            common_menu.Socket_AsyncTask socket_on = new common_menu.Socket_AsyncTask();
//            socket_on.execute();
//        }

        // seekbar------------------------
        seekBarleftright.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged( SeekBar seekBar, int progress, boolean fromUser ) {
            }
            @Override
            public void onStartTrackingTouch( SeekBar seekBar ) {
            }
            @Override
            public void onStopTrackingTouch( SeekBar seekBar ) {
                common_menu.getIPandPort();
                angle1 = seekBar.getProgress();
                common_menu.CMD = String.format("W %d",angle1);
                common_menu.Socket_AsyncTask socket_on = new common_menu.Socket_AsyncTask();
                socket_on.execute();
                DatabaseReference myRef = database.getReference("angle1");
                myRef.setValue(angle1);
            }
        });

    }
//
//
//    // 통신=======================================================================
//    public  void getIPandPort()
//    {
//        String iPandPort = ipAddress.getText().toString();
//        Log.d("MY TEST", "IP String: " + iPandPort);
//        String temp[] = iPandPort.split(":");
//        wifiModuleIP = temp[0];
//        wifiModulePort = Integer.valueOf(temp[1]);
//        Log.d("MY TEST", "IP:" + wifiModuleIP);
//        Log.d("MY TEST", "PORT:" + wifiModulePort);
//    }
//
//    public class Socket_AsyncTask extends AsyncTask<Void,Void,Void>
//    {
//        Socket socket;
//        @Override
//        protected Void doInBackground(Void... params)
//        {
//            try
//            {
//                InetAddress inetAddress = InetAddress.getByName(cctvActivity.wifiModuleIP);
//                socket = new java.net.Socket(inetAddress, cctvActivity.wifiModulePort);
//                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
////                dataOutputStream.writeBytes(CMD);
////                dataOutputStream.writeBytes(String.valueOf(angle));
//                dataOutputStream.writeBytes(CMD+" "+ANGLE);
//
////                dataOutputStream.writeByte(angle);
//                dataOutputStream.close();
//                socket.close();
//            }
//            catch (UnknownHostException e){e.printStackTrace();}
//            catch (IOException e){e.printStackTrace();}
//
//            return  null;
//        }
//    }

    // 툴바 ========================================================================
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
    //============================================================================
}