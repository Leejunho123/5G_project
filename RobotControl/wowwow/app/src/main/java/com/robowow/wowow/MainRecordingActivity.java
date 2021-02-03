package com.robowow.wowow;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.robowow.wowow.R;

import java.io.File;
import java.io.IOException;

public class MainRecordingActivity extends AppCompatActivity implements View.OnClickListener{
    private int RECORD_AUDIO_REQUEST_CODE =123 ;
    public int check = 0;
    private Toolbar toolbar;
    private Chronometer chronometer;
    private ImageView imageViewRecord, imageViewPlay, imageViewStop;
    private SeekBar seekBar;
    private LinearLayout linearLayoutRecorder, linearLayoutPlay;

    private long stopped=0;

    private MediaRecorder mRecorder;
    private MediaPlayer mPlayer;
    private String fileName = null;
    private int lastProgress = 0;
    private Handler mHandler = new Handler();
    private boolean isPlaying = false;
    StorageReference storageRef;
    public String strText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_recording);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            getPermissionToRecordAudio();; //권한받기
        }
        initViews();
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////처음화면
    private void initViews() {

        /** setting up the toolbar  **/
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Voice Recorder");
        toolbar.setTitleTextColor(getColor(R.color.purple_200));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        linearLayoutRecorder = (LinearLayout) findViewById(R.id.linearLayoutRecorder);
        chronometer = (Chronometer) findViewById(R.id.chronometerTimer);
        chronometer.setBase(SystemClock.elapsedRealtime());
        imageViewRecord = (ImageView) findViewById(R.id.imageViewRecord);
        imageViewStop = (ImageView) findViewById(R.id.imageViewStop);
        imageViewPlay = (ImageView) findViewById(R.id.imageViewPlay);
        linearLayoutPlay = (LinearLayout) findViewById(R.id.linearLayoutPlay);
        seekBar = (SeekBar) findViewById(R.id.seekBar);

        imageViewRecord.setOnClickListener(this);
        imageViewStop.setOnClickListener(this);
        imageViewPlay.setOnClickListener(this);

    }

///////////////////////////////////////////////////////////////////////////////////////녹음준비(녹음버튼 누른 후의 View)
    private void prepareforRecording() {
        TransitionManager.beginDelayedTransition(linearLayoutRecorder); //전환 설정
        imageViewRecord.setVisibility(View.GONE);
        imageViewStop.setVisibility(View.VISIBLE);
        linearLayoutPlay.setVisibility(View.GONE);
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////녹음 시작
    private void startRecording() {
        final EditText txtEdit = new EditText(MainRecordingActivity.this);
        AlertDialog.Builder clsBuilder = new AlertDialog.Builder( MainRecordingActivity.this);
        clsBuilder.setTitle( "녹음 파일명 이름" );
        clsBuilder.setView( txtEdit );
        clsBuilder.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        prepareforRecording();
                        strText = txtEdit.getText().toString();
                        Toast.makeText(MainRecordingActivity.this, strText + ".mp3", Toast.LENGTH_SHORT).show();
                        //we use the MediaRecorder class to record
                        mRecorder = new MediaRecorder();
                        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                        /**In the lines below, we create a directory VoiceRecorderSimplifiedCoding/Audios in the phone storage
                         * and the audios are being stored in the Audios folder **/

                        File root = android.os.Environment.getExternalStorageDirectory(); //경로지정
                        File file = new File(root.getAbsolutePath() + "/VoiceRecorderSimplifiedCoding/Audios");
                        if (!file.exists()) { // 만약 폴더가 없으면 폴더생성
                            file.mkdirs();
                        }

                        fileName = root.getAbsolutePath() + "/VoiceRecorderSimplifiedCoding/Audios/" +
                                String.valueOf(strText + ".mp3") ;
                        Log.d("filename", fileName);
                        mRecorder.setOutputFile(fileName);
                        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);

                        try {
                            mRecorder.prepare();
                            mRecorder.start();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        lastProgress = 0;
                        seekBar.setProgress(0); //
                        stopPlaying();
                        //starting the chronometer
                        chronometer.setBase(SystemClock.elapsedRealtime());
                        chronometer.start();
                    }
                });
        clsBuilder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        clsBuilder.show();
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////녹음 정지
    private void stopRecording() {

        try{
            mRecorder.stop();
            mRecorder.release();
        }catch (Exception e){
            e.printStackTrace();
        }
        mRecorder = null;
        //starting the chronometer
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.stop();

        storageRef = FirebaseStorage.getInstance().getReference();
        Uri file = Uri.fromFile(new File(fileName));
        StorageReference musicRef = storageRef.child("Record").child(String.valueOf(strText + ".mp3"));

        musicRef.putFile(file)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });


        //showing the play button
        Toast.makeText(this, "Recording saved successfully.", Toast.LENGTH_SHORT).show();
    }

/////////////////////////////////////////////////////////////////////////////////////////정지버튼을 누르고 녹음이 끝난 View)
    private void prepareforStop() {
        TransitionManager.beginDelayedTransition(linearLayoutRecorder);
        imageViewRecord.setVisibility(View.VISIBLE);
        imageViewStop.setVisibility(View.GONE);
        linearLayoutPlay.setVisibility(View.VISIBLE);
    }

    private void stopPlaying() {
        try{
            mPlayer.release();
        }catch (Exception e){
            e.printStackTrace();
        }
        mPlayer = null;
        //showing the play button
        imageViewPlay.setImageResource(R.drawable.ic_play);
        stopped = chronometer.getBase() - SystemClock.elapsedRealtime();
        chronometer.stop();

        seekUpdation();


    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////// 녹음 재생
    private void startPlaying() {
       mPlayer = new MediaPlayer();
        try {
//fileName is global string. it contains the Uri to the recently recorded audio.
            mPlayer.setDataSource(fileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e("LOG_TAG", "prepare() failed");
        }
        //making the imageview pause button
        imageViewPlay.setImageResource(R.drawable.ic_pause);

        seekBar.setProgress(lastProgress);
        mPlayer.seekTo(lastProgress);

        seekBar.setMax(mPlayer.getDuration()); //미디어의 길이만큼 SeekBar의 길이를 설정

        seekUpdation();

        if(check==0)
        {
            chronometer.setBase(SystemClock.elapsedRealtime());
            check=1;
        }
        else {
            chronometer.setBase(SystemClock.elapsedRealtime() + stopped);
        }
        chronometer.start();


        /** once the audio is complete, timer is stopped here**/
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                imageViewPlay.setImageResource(R.drawable.ic_play);
                mPlayer.seekTo(0);
                isPlaying = false;
                check=0;
                chronometer.stop();
                chronometer.setBase(SystemClock.elapsedRealtime());
            }
        });

        /** moving the track as per the seekBar's position**/
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if( mPlayer!=null && fromUser ){ //사용자가 움직여서 값이 변한거면 true, 아니면 false
                    //here the track's progress is being changed as per the progress bar
                    mPlayer.seekTo(progress); //재생위치 변경
                    //timer is being updated as per the progress of the seekbar
                    //stopped=SystemClock.elapsedRealtime() - mPlayer.getCurrentPosition();
                    chronometer.setBase(SystemClock.elapsedRealtime() - mPlayer.getCurrentPosition());
                    lastProgress = progress;
                }
                else if(mPlayer == null && fromUser && !isPlaying){
                    lastProgress= seekBar.getProgress();
                    chronometer.setBase(SystemClock.elapsedRealtime()-lastProgress);
                    stopped = -lastProgress;
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {


            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (!isPlaying){
                    lastProgress= seekBar.getProgress();
                    chronometer.setBase(SystemClock.elapsedRealtime()-lastProgress);
                    stopped = -lastProgress;


                }
            }
        });
    }
//핸들러 : 대상 스레드의 코드를 실행
    Runnable runnable = new Runnable() { //실행코드가 담긴 객체(seekUpdation)를 핸들러에 전달하기 위해서 사용
        @Override
        public void run() {
            seekUpdation();
        }
    };

    private void seekUpdation() {
        if(mPlayer != null){
            int mCurrentPosition = mPlayer.getCurrentPosition() ;
            seekBar.setProgress(mCurrentPosition);
            lastProgress = mCurrentPosition;

        }

        mHandler.postDelayed(runnable, 100); //현재 시작에서 delayMillis 만큼 시간 후에 Runnable 객체 실행
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////click 설정
    @Override
    public void onClick(View v) {
        if( v == imageViewRecord ){
//            prepareforRecording();
            startRecording();
        }else if( v == imageViewStop ) {
            prepareforStop();
            stopRecording();
        }else if( v == imageViewPlay ){
            if( !isPlaying && fileName != null ){
                isPlaying = true;
                startPlaying();
            }else{
                isPlaying = false;
                stopPlaying();
            }
        }
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////toolbar설정
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.item_list:
                Intent intent = new Intent(this, RecordingListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
            case android.R.id.home:
                Intent intent1 = new Intent(this, playingActivity.class);
                startActivity(intent1);
                finish();
                return true;
            case R.id.server_list:
                Intent intent2 = new Intent(this, serverList.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////권한 설정
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getPermissionToRecordAudio() {
        // 1) Use the support library version ContextCompat.checkSelfPermission(...) to avoid
        // checking the build version since Context.checkSelfPermission(...) is only available
        // in Marshmallow
        // 2) Always check for permission (even if permission has already been granted)
        // since the user can revoke permissions at any time through Settings
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {

            // The permission is NOT already granted.
            // Check if the user has been asked about this permission already and denied
            // it. If so, we want to give more explanation about why the permission is needed.
            // Fire off an async request to actually get the permission
            // This will show the standard permission request dialog UI
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    RECORD_AUDIO_REQUEST_CODE);

        }
    }

    // Callback with the request from calling requestPermissions(...)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        // Make sure it's our original READ_CONTACTS request
        if (requestCode == RECORD_AUDIO_REQUEST_CODE) {
            if (grantResults.length == 3 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                    && grantResults[2] == PackageManager.PERMISSION_GRANTED){

                //Toast.makeText(this, "Record Audio permission granted", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "You must give permissions to use this app. App is exiting.", Toast.LENGTH_SHORT).show();
                finishAffinity();
            }
        }

    }

}