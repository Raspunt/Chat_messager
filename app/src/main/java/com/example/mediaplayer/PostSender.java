package com.example.mediaplayer;

import com.example.mediaplayer.ChatActivity.ChatAdapter;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PostSender {

    public String responseData ;

    public   void ActionAfterPostReq() {


    }


    public  void send(String test_url, RequestBody formBody){


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

                        responseData = Objects.requireNonNull(response.body()).string();

                        ActionAfterPostReq();


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }



    }


