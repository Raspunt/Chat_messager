package com.example.mediaplayer.ChatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediaplayer.R;
import com.example.mediaplayer.Settings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatListActivity extends AppCompatActivity {


    RecyclerView recyclerViewChatList;
    ImageView addChatBtn;
    ArrayList<Chat> chat_data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_list_activity);


        recyclerViewChatList = findViewById(R.id.recyclerViewChatList);
        addChatBtn = findViewById(R.id.addChatBtn);




            GetChat ch = new GetChat();
            ch.start();

            addChatBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(ChatListActivity.this, NewChatActivity.class);
                    startActivity(intent);

                }
            });

    }



    public class GetChat extends  Thread{


        @Override
        public void run() {

            try {
                OkHttpClient client = new OkHttpClient();

                RequestBody formBody = new FormBody.Builder()
                        .build();


                Request request = new Request.Builder()
                        .url(Settings.GET_JSON_MESSAGE_URL)
                        .post(formBody)
                        .build();

                Call call = client.newCall(request);
                Response response = call.execute();


                String responseAns = Objects.requireNonNull(response.body()).string();

                JSONArray respJson = new JSONArray(responseAns);

                for (int i = 0 ; i < respJson.length();i++){
                    String chat_title = respJson.getJSONObject(i).get("chat_title").toString();
                    String chat_disc = respJson.getJSONObject(i).get("chat_disc").toString();
                    chat_data.add(new Chat(chat_title,chat_disc));
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       ChatListAdapter adapter = new ChatListAdapter(ChatListActivity.this,chat_data);
                       recyclerViewChatList.setAdapter(adapter);


                    }
                });
                








            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }


        }
    }


}
