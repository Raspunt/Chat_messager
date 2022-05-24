package com.example.mediaplayer.ChatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
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
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class ChatActivity extends AppCompatActivity {

    public static JSONArray messageObjs;
    public static String current_user;
    public static int current_chat_id;
    public static ChatAdapter adapter;
    public static ChatActivity context ;
    public static AppCompatImageView addChatBtn;
    public static  WebSocket ws;

    public static String chat_name ;

    public static ArrayList<MessageObj> mesArr = new ArrayList<>();
    RecyclerView messagesList;
    ImageView SendButtonChatActiv;
    TextView inputMessageChatActiv;
    TextView ChatNameChatActivity;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);



        context = this ;
        messagesList = findViewById(R.id.chatRecyclerView);
        SendButtonChatActiv = findViewById(R.id.SendButtonChatActiv);
        inputMessageChatActiv = findViewById(R.id.inputMessageChatActiv);
        ChatNameChatActivity = findViewById(R.id.ChatNameChatActivity);

        ChatNameChatActivity.setText(chat_name);

        mesArr.clear();
        for (int i = 0; i < messageObjs.length(); i++) {
            try {

                String username = messageObjs.getJSONObject(i).get("author").toString();
                String text = messageObjs.getJSONObject(i).get("text").toString();
                String date = messageObjs.getJSONObject(i).get("date").toString();
                MessageObj mes = new MessageObj(username, text, date);

                mesArr.add(mes);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        adapter = new ChatAdapter(this, mesArr);
        messagesList.setAdapter(adapter);


        SendButtonChatActiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestBody formBody = new FormBody.Builder()
                        .add("chat_id", String.valueOf(current_chat_id))
                        .add("username", current_user)
                        .add("text", inputMessageChatActiv.getText().toString())
                        .build();

                send_post(Settings.CREATE_MESSAGE_URL, formBody, adapter);
                inputMessageChatActiv.setText("");
            }
        });

        Start_listeningWs();




    }


//    RequestBody formBody = new FormBody.Builder()
//            .build();


    public void send_post(String test_url, RequestBody formBody, ChatAdapter adapter) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    OkHttpClient client = new OkHttpClient();


                    Request request = new Request.Builder()
                            .url(test_url)
                            .post(formBody)
                            .build();

                    Call call = client.newCall(request);
                    Response response = call.execute();




                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


    public static void Start_listeningWs(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(Settings.GET_JSON_USER_WS_URL).build();
                WebSocketUpdater listener = new WebSocketUpdater();
                ws = client.newWebSocket(request, listener);

                Log.e("Ws start","Server start");
            }
        }).start();




    }


    @Override
    protected void onStop() {
        super.onStop();

        ws.cancel();


    }
}
