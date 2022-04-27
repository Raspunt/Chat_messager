package com.example.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingsActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settingsactivity);



         Button MainPageBtn = findViewById(R.id.main_pageBtn);
         Button ChangeMusicFolder = findViewById(R.id.ChangeMusicFolder);




         ChangeMusicFolder.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent()
                         .setType("*/*")
                         .setAction(Intent.ACTION_GET_CONTENT);
                 startActivityForResult(Intent.createChooser(intent, "Select a file"), 123);
             }
         });



        MainPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(),MainActivity.class);
                startActivity(intent);

            }
        });

    }




    
}