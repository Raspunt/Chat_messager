package com.example.mediaplayer.ChatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class WebSocketUpdater extends WebSocketListener {

    private static final int NORMAL_CLOSURE_STATUS = 1000;

    @Override
    public void onOpen(WebSocket webSocket, Response response) {

        System.out.println("ws server open");
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {

        try {
            System.out.println("Receiving: " + text);
            JSONObject jsText = new JSONObject(text);

//            Log.e("lolKek",jsText.get("content").toString());


            JSONObject message_data = new JSONObject(jsText.get("content").toString());
            System.out.println(message_data.get("username"));

            String username = (String) message_data.get("username");
            String message_text = (String) message_data.get("text");
            String date = (String) message_data.get("date");


            MessageObj mes = new MessageObj(username,message_text,date);

            ChatActivity.mesArr.add(mes);

            ChatActivity ca = ChatActivity.context ;

            ca.runOnUiThread(new Runnable() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void run() {
                    ChatActivity.adapter.notifyDataSetChanged();
                }
            });









        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        System.out.println("Receiving: " + bytes.hex());
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        webSocket.close(NORMAL_CLOSURE_STATUS, null);
        System.out.println("Closing: " + code + " " + reason);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        t.printStackTrace();
    }


}
