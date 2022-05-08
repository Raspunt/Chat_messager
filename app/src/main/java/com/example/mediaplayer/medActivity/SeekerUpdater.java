package com.example.mediaplayer.medActivity;

import android.media.MediaPlayer;
import android.util.Log;
import android.widget.SeekBar;

import java.util.ArrayList;

class SeekerUpdater extends Thread  {

    MediaPlayer mp;
    SeekBar seekBar ;

    public SeekerUpdater(SeekBar seek){
        this.seekBar = seek ;
    }

    public void run() {
        boolean setupReady = false;
        while (true){

            if (mp == null){

            }else {

                seekBar.setMax(mp.getDuration());

                seekBar.setProgress(mp.getCurrentPosition());

            }


        }



    }


    public  void setMediaPlayer(MediaPlayer mp){
        this.mp = mp;
    }

    public MediaPlayer getMediaPlayer(){
        return mp;
    }

    public  void setSeekBar(SeekBar seekBar){
        this.seekBar = seekBar;
    }





}
