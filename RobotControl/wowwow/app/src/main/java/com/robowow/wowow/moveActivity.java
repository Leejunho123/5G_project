package com.robowow.wowow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import java.util.ArrayList;

public class moveActivity extends AppCompatActivity implements recyclerAdaptor.recycleViewClickListener {

    ImageButton stop,forward,backward,left,right,left_up,left_down,right_up,right_down;
    EditText ipAddress;
    Button detail_mode;
    private Toolbar toolbar;

    //    Socket myAppSocket = null;
    public static String wifiModuleIP = "";
    public static int wifiModulePort = 0;
    public static String CMD = "0";

    private int[] images;
    private String[] title;
    private ArrayList<itemData> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager= new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);

        /** setting up the toolbar  **/
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("MOVE");
        toolbar.setTitleTextColor(getColor(R.color.purple_200));
        setSupportActionBar(toolbar);

        ipAddress = findViewById(R.id.ipAddress);

        stop = findViewById(R.id.stop);
        left_up = findViewById(R.id.left_up);
        left_down = findViewById(R.id.left_down);
        right_up = findViewById(R.id.right_up);
        right_down = findViewById(R.id.right_down);
        left =  findViewById(R.id.left);
        right =  findViewById(R.id.right);
        forward = findViewById(R.id.forward);
        backward = findViewById(R.id.backward);
        detail_mode = findViewById(R.id.detail_mode);



        images = new int[]
                {
                        R.drawable.robot3,R.drawable.robot4
                };
        title = new String[]
                {
                        "Hi","Cheer Up"

                };

        for(int i=0; i<2; i++){
            dataList.add(new itemData(images[i],title[i]));
        }

        final recyclerAdaptor adaptor = new recyclerAdaptor(dataList);
        recyclerView.setAdapter(adaptor);


        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getIPandPort();
                CMD="0";
                Socket_AsyncTask socket_on = new Socket_AsyncTask();
                socket_on.execute();
            }
        });
        left_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getIPandPort();
                CMD="1";
                Socket_AsyncTask socket_on = new Socket_AsyncTask();
                socket_on.execute();
            }
        });
        left_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getIPandPort();
                CMD="2";
                Socket_AsyncTask socket_on = new Socket_AsyncTask();
                socket_on.execute();
            }
        });
        right_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getIPandPort();
                CMD="3";
                Socket_AsyncTask socket_on = new Socket_AsyncTask();
                socket_on.execute();
            }
        });
        right_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getIPandPort();
                CMD="4";
                Socket_AsyncTask socket_on = new Socket_AsyncTask();
                socket_on.execute();
            }
        });
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getIPandPort();
                CMD="5";
                Socket_AsyncTask socket_on = new Socket_AsyncTask();
                socket_on.execute();
            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getIPandPort();
                CMD="6";
                Socket_AsyncTask socket_on = new Socket_AsyncTask();
                socket_on.execute();
            }
        });
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getIPandPort();
                CMD="7";
                Socket_AsyncTask socket_on = new Socket_AsyncTask();
                socket_on.execute();
            }
        });
        backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getIPandPort();
                CMD="8";
                Socket_AsyncTask socket_on = new Socket_AsyncTask();
                socket_on.execute();
            }
        });

        detail_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = ipAddress.getText().toString();
                Intent intent =  new Intent(moveActivity.this,DetailActivity.class);
                intent.putExtra("str",temp);
                ipAddress.setText(temp);
                startActivity(intent);

            }
        });
        adaptor.setOnClickListener(this);

    }

    @Override
    public void onItemClicked(int position) {
        if(position ==0) {
            getIPandPort();
            CMD="a";
            Socket_AsyncTask socket_on = new Socket_AsyncTask();
            socket_on.execute();
        }
        else if (position ==1)
        {
            getIPandPort();
            CMD="d";
            Socket_AsyncTask socket_on = new Socket_AsyncTask();
            socket_on.execute();
        }
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