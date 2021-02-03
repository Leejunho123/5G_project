package com.robowow.wowow;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class serverList extends AppCompatActivity {

    private Toolbar toolbar;
    ListView list;
    List<String> LIST = new ArrayList<>();
    StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String TAG = "main";
        setContentView(R.layout.activity_server_list);
        list = findViewById(R.id.list);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////list 설정
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,LIST);
        storageRef = FirebaseStorage.getInstance().getReference().child("Record");
        storageRef.listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {

                        Log.d(TAG,"Success");
                        for (StorageReference prefix : listResult.getPrefixes()) {
                            // This will give you a folder name
                            // You may call listAll() recursively on them.
                        }

                        for (StorageReference item : listResult.getItems()) {
                            Log.d(TAG,item.getName());
                            LIST.add(item.getName());
                            // All the items under listRef.
                            adapter.notifyDataSetInvalidated();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG,"sad");
                        // Uh-oh, an error occurred!
                    }
                });
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                storageRef = FirebaseStorage.getInstance().getReference().child("Record");
                String strText = LIST.get(position);
                Log.d(TAG,"XXXX"+strText);
                StorageReference musicRef = storageRef.child(strText);
                File root = android.os.Environment.getExternalStorageDirectory(); //경로지정
                File file = new File(root.getAbsolutePath() + "/VoiceRecorderSimplifiedCoding/Audios");
                if (!file.exists()) { // 만약 폴더가 없으면 폴더생성
                    file.mkdirs();
                }

                String fileName = root.getAbsolutePath() + "/VoiceRecorderSimplifiedCoding/Audios/" + strText ;
                Uri Urifile = Uri.fromFile(new File(fileName));
                musicRef.getFile(Urifile)
                        .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                // Successfully downloaded data to local file
                                // ...
                                // 보내기! RecordPlay
                                storageRef = FirebaseStorage.getInstance().getReference();
                                StorageReference musicRef = storageRef.child(String.valueOf("RecordPlay.mp3"));

                                musicRef.putFile(Urifile)
                                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                // Get a URL to the uploaded content
                                                Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                                                common_menu.getIPandPort();
                                                common_menu.CMD="R";
                                                common_menu.Socket_AsyncTask socket_on = new common_menu.Socket_AsyncTask();
                                                socket_on.execute();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception exception) {
                                                // Handle unsuccessful uploads
                                                // ...
                                            }
                                        });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle failed download
                        // ...
                    }
                });

            }
        });

        initViews();
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////기본화면
    private void initViews() {
//        /** setting up the toolbar  **/
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Server List");
        setSupportActionBar(toolbar);

//        /** enabling back button ***/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


///////////////////////////////////////////////////////////////////////////////////////////////////////////////toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
//
    }

}