package com.example.mediaplayer.medActivity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.mediaplayer.ChatActivity.LoginRegisterActivity;
import com.example.mediaplayer.NewsActivity.NewsActivity;
import com.example.mediaplayer.R;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DataAdapter.OnNoteListener {

    RecyclerView recyclerView ;
    ImageView btPlayTopBar , btPauseTopBar;
    LinearLayout linearLayoutBottomBar ;
    TextView textViewBottomBar;
    SeekBar BottomSeekBar;
    Button main_pageBtn;
    Button newsBtn;
    SeekerUpdater dat;
    ArrayList<String> musicListDir  = new  ArrayList<>();
    Button ChatBtn;

    public static String music_folder = String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));
    private static final int REQUEST_PERMISSIONS = 12345;

    private static MediaPlayer current_mp ;
    private static String current_mp_name ;



    private static  final  String[] PERMISSIONS ={
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE

    };






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && arePermissionsDeniewd()) {
            requestPermissions(PERMISSIONS, REQUEST_PERMISSIONS);
            //return;
        }

        if (current_mp != null){
            linearLayoutBottomBar.setVisibility(View.VISIBLE);
            textViewBottomBar.setText(current_mp_name);
        }


        recyclerView = findViewById(R.id.recucle);
        btPlayTopBar = findViewById(R.id.PlayBottomBar);
        btPauseTopBar = findViewById(R.id.PauseBottomBar);
        linearLayoutBottomBar = findViewById(R.id.LinerLayoutBottomBar);
        textViewBottomBar = findViewById(R.id.textBottomBar);
        BottomSeekBar = findViewById(R.id.BottomSeekBar);
        newsBtn = findViewById(R.id.newsBtn);
        main_pageBtn = findViewById(R.id.main_pageBtn);
        ChatBtn = findViewById(R.id.ChatBtn);


        dat = new SeekerUpdater(BottomSeekBar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        addMusicFilerFrom(music_folder);

        DataAdapter dataAdapter = new DataAdapter(this,this, musicListDir);
        recyclerView.setAdapter(dataAdapter);


        dat.start();

        newsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), NewsActivity.class);
                startActivity(intent);


            }
        });

        ChatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), LoginRegisterActivity.class);
                startActivity(intent);

            }
        });



        BottomSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                if (b == false) return;

                MediaPlayer dmp = dat.getMediaPlayer();
                if(dmp != null ){
                    dmp.seekTo(seekBar.getProgress());
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {


            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }





    @Override
    public void onNoteClick(int position) {

    }



        @SuppressLint("NewApi")
        private boolean arePermissionsDeniewd() {
            for (int i = 0; i < PERMISSIONS.length; i++) {
                if (checkSelfPermission(PERMISSIONS[i]) != PackageManager.PERMISSION_GRANTED) {
                    return true;
                }
            }
            return false;
        }

        @SuppressLint("NewApi")
        @Override
        public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResult) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResult);
            if (arePermissionsDeniewd()) {
                ((ActivityManager) (this.getSystemService(ACTIVITY_SERVICE))).clearApplicationUserData();
                recreate();
            } else {
                onResume();
            }
        }




        private  void addMusicFilerFrom(String dirPath){
            final File musicDir = new File(dirPath);
            if (!musicDir.exists()){
                musicDir.mkdir();
                return;
            }


            if (musicDir.listFiles() != null) {

                final File[] files = musicDir.listFiles();

                for (File file : files) {
                    final String path = file.getAbsolutePath();
                    Log.e("files",path);
                    if (path.endsWith(".mp3")) {
                        musicListDir.add(path);
                    }
                }
            }


        }









    ArrayList<MediaPlayer> musicUsed = new ArrayList<>();
    @Override
    public void onPlayListener(int position,ArrayList<MediaPlayer> mp,ImageView bt_play,ImageView bt_pause) {


        try {



        mp.get(position).start();

        musicUsed.add(mp.get(position));
        dat.setMediaPlayer(mp.get(position));



        if (musicUsed.size() == 2){

                if (musicUsed.get(0).equals(musicUsed.get(1))){
                    musicUsed.get(0).stop();
                    musicUsed.get(0).prepare();
                    musicUsed.get(1).start();
                    musicUsed.remove(0);



                }else {
                    musicUsed.get(0).stop();
                    musicUsed.get(0).prepare();
                    musicUsed.remove(0);
                }
            }


        }catch (Exception e){
            Log.e("error",e.toString());
        }



        String music = musicListDir.get(position);
        String musicName = music.substring(music.lastIndexOf("/") + 1);
        String MusicNameWithoutMp3 = musicName.replace(".mp3","");
        addNotification(MusicNameWithoutMp3);
        BottomBarButtons(MusicNameWithoutMp3,mp.get(position));
        current_mp = mp.get(position);
        current_mp_name = MusicNameWithoutMp3;
    }
    public  void BottomBarButtons(String nameSong, final MediaPlayer mptb){
        linearLayoutBottomBar.setVisibility(View.VISIBLE);
        textViewBottomBar.setText(nameSong);

        btPauseTopBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    mptb.pause();
                    btPauseTopBar.setVisibility(View.GONE);
                    btPlayTopBar.setVisibility(View.VISIBLE);
            }
        });

        btPlayTopBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mptb.start();
                btPauseTopBar.setVisibility(View.VISIBLE);
                btPlayTopBar.setVisibility(View.GONE);
            }
        });

    }


    @Override
    public void onPauseListener(int position,ArrayList<MediaPlayer> mp,ImageView bt_play,ImageView bt_pause) {
            mp.get(position).pause();



    }


    public void addNotification(String textMessage) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_base)
                        .setContentTitle("Media_bitch")
                        .setContentText(textMessage)
                        .addAction(R.drawable.ic_play,"play",null)
                        .addAction(R.drawable.ic_pause,"pause",null);


        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);


        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }





}