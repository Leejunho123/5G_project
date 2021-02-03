package com.robowow.wowow;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.robowow.wowow.R;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class common_menu extends Fragment {
    Button cctv, playing, move;
    static EditText ipAddress;

    public static String wifiModuleIP = "";
    public static int wifiModulePort = 0;
    public static String CMD = "0";

    public common_menu() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_common_menu, container, false);
        cctv = rootView.findViewById(R.id.cctv);
        playing = rootView.findViewById(R.id.playing);
        move = rootView.findViewById(R.id.move);
        ipAddress = rootView.findViewById(R.id.ipAddress);


        cctv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), cctvActivity.class);
                startActivity(intent);
            }
        });
        playing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), playingActivity.class);
                startActivity(intent);
            }
        });
        move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), moveActivity.class);
                startActivity(intent);
            }
        });
        // Inflate the layout for this fragment
        return rootView;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////wifi
    public static void getIPandPort(){

        String iPandPort = ipAddress.getText().toString();
        Log.d("MY TEST","IP String: "+ iPandPort);
        String temp[] = iPandPort.split(":");
        wifiModuleIP = temp[0];
        wifiModulePort = Integer.valueOf(temp[1]);
        Log.d("MY TEST", "IP:"+wifiModuleIP);
        Log.d("MY TEST", "PORT:"+wifiModulePort);
    }
    public static class Socket_AsyncTask extends AsyncTask<Void,Void,Void> {
        Socket socket;
        @Override
        protected Void doInBackground(Void... params) {
            try{
                InetAddress inetAddress = InetAddress.getByName(common_menu.wifiModuleIP);
                socket = new java.net.Socket(inetAddress, common_menu.wifiModulePort);
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

