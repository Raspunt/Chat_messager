package com.example.mediaplayer;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mediaplayer.ChatActivity.ChatListActivity;
import com.example.mediaplayer.loginAndRegister.LoginActivity;

public class Settings  {



    public static  String SERVER_NAME = "192.168.1.22:8000";



    public static  String CREATE_MESSAGE_URL = GeneratorURL("http://",SERVER_NAME,"/create_message/");
    public static  String GET_JSON_MESSAGE_URL = GeneratorURL("http://",SERVER_NAME,"/get_chatJson/");
    public static  String GET_MESSAGES_BY_CHAT_ID_URL = GeneratorURL("http://",SERVER_NAME,"/get_MessagesByChatId/");
    public static  String IS_USER_AUTHENTICATED_URL =  GeneratorURL("http://",SERVER_NAME,"/IsUserAuthenticated/");
    public static  String GET_NEWS_JSON_URL = GeneratorURL("http://",SERVER_NAME,"/get_NewsJson/");
    public static  String CREATE_USER_URL = GeneratorURL("http://",SERVER_NAME,"/create_user/");
    public static  String GET_JSON_USER_WS_URL = GeneratorURL("ws://",SERVER_NAME,"/JsonData");
    public static  String CREATE_CHAT_URL = GeneratorURL("http://",SERVER_NAME,"/Create_Chat/");



    private static String GeneratorURL(String protocol, String server_name, String path){

        return  protocol + server_name + path;


    }


}
