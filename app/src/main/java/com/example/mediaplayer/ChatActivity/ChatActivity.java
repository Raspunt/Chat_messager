package com.example.mediaplayer.ChatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

public class ChatActivity extends AppCompatActivity {

    public static JSONArray messageObjs ;
    public static String current_user ;
    public static int  current_chat_id;

    public static   ArrayList<MessageObj> mesArr = new ArrayList<>();
    RecyclerView messagesList ;
    ImageView SendButtonChatActiv;
    TextView inputMessageChatActiv;





    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);


        messagesList = findViewById(R.id.chatRecyclerView);
        SendButtonChatActiv = findViewById(R.id.SendButtonChatActiv);
        inputMessageChatActiv = findViewById(R.id.inputMessageChatActiv);




        for (int i = 0 ; i < messageObjs.length();i++){
            try {

                String username = messageObjs.getJSONObject(i).get("author").toString();
                String text = messageObjs.getJSONObject(i).get("text").toString();
                String date = messageObjs.getJSONObject(i).get("date").toString();
                MessageObj mes = new MessageObj(username,text,date);

                mesArr.add(mes);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }





        ChatAdapter adapter = new ChatAdapter(this,mesArr);
        messagesList.setAdapter(adapter);


        SendButtonChatActiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestBody formBody = new FormBody.Builder()
                        .add("chat_id",String.valueOf(current_chat_id))
                        .add("username",current_user)
                        .add("text", inputMessageChatActiv.getText().toString())
                    .build();

                send_post(Settings.CREATE_MESSAGE_URL,formBody,ChatActivity.this,adapter);
                inputMessageChatActiv.setText("");
            }
        });



    }


//    RequestBody formBody = new FormBody.Builder()
//            .build();

    public void send_post(String test_url, RequestBody formBody, Context context,ChatAdapter adapter){


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


                    String responseAns = Objects.requireNonNull(response.body()).string();

                    JSONObject lastMessage = new JSONObject(responseAns);


                    String username = lastMessage.get("username").toString();
                    String text = lastMessage.get("text").toString();
                    String date = lastMessage.get("date").toString();

                    Log.e("new Data",username+" " + text);

                    mesArr.add(new MessageObj(username,text,date));


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });




                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }




}
