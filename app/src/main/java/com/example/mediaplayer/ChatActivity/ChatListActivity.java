package com.example.mediaplayer.ChatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediaplayer.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_list_activity);


        recyclerViewChatList = findViewById(R.id.recyclerViewChatList);
        addChatBtn = findViewById(R.id.addChatBtn);

//        ChatListAdapter chatAdapter = new ChatListAdapter()


            GetChat ch = new GetChat();
            ch.start();




    }



    public class GetChat extends  Thread{


        @Override
        public void run() {

            try {
                String test_url = "http://192.168.1.9:8000/get_chatJson/";
                OkHttpClient client = new OkHttpClient();

                RequestBody formBody = new FormBody.Builder()
                        .build();


                Request request = new Request.Builder()
                        .url(test_url)
                        .post(formBody)
                        .build();

                Call call = client.newCall(request);
                Response response = call.execute();


                String responseAns = Objects.requireNonNull(response.body()).string();

                Log.e("json",responseAns);




            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }


}
