package com.example.mediaplayer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    TextView tv ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settingsactivity);



         Button MainPageBtn = findViewById(R.id.main_pageBtn);
         Button ChangeMusicFolder = findViewById(R.id.ChangeMusicFolder);
         tv = findViewById(R.id.pathToDir);




         ChangeMusicFolder.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent()
                         .setAction(Intent.ACTION_OPEN_DOCUMENT_TREE);

                 intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, "/");

                 startActivityForResult(Intent.createChooser(intent, "Select a file"),224);






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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        tv.setText(data.getDataString());
        MainActivity.music_folder = data.getDataString();
    }
}