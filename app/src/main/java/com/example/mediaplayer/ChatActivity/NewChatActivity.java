package com.example.mediaplayer.ChatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mediaplayer.PostSender;
import com.example.mediaplayer.R;
import com.example.mediaplayer.Settings;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class NewChatActivity extends AppCompatActivity {

    public EditText ChatName;
    public EditText ChatDisc;
    public Button SubmitButtonNewChat;


    protected void onStop() {
        super.onStop();

        finish();

        Log.e("lololo","Это херня остановилась");

    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_chat_activity);


        ChatName = findViewById(R.id.ChatName);
        ChatDisc = findViewById(R.id.ChatDisc);
        SubmitButtonNewChat = findViewById(R.id.SubmitButtonNewChat);





        SubmitButtonNewChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RequestBody formBody = new FormBody.Builder()
                        .add("title", String.valueOf(ChatName.getText()))
                        .add("disc",String.valueOf(ChatDisc.getText()))
                        .build();

                PostSenderNewChat ps = new PostSenderNewChat();
                ps.send(Settings.CREATE_CHAT_URL,formBody);






            }
        });
    }


    private class PostSenderNewChat extends PostSender{

        @Override
        public void ActionAfterPostReq() {

            if (!ChatName.getText().toString().matches("")&& !ChatDisc.getText().toString().matches("")) {
                Toast.makeText(NewChatActivity.this, "Успешно создан чат", Toast.LENGTH_LONG).show();
                finish();

            }
        }
    }


}
