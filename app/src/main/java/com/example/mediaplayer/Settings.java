package com.example.mediaplayer;

public class Settings {
//    "http://143.198.231.162:8000/" Global ip
//    "http://192.168.1.9:8000/" local ip
    public static String SERVER_URL = "http://192.168.1.9:8000/";


    public static  String CREATE_MESSAGE_URL = SERVER_URL + "create_message/";
    public static  String GET_JSON_MESSAGE_URL = SERVER_URL + "get_chatJson/";
    public static  String GET_MESSAGES_BY_CHAT_ID_URL = SERVER_URL + "get_MessagesByChatId/";
    public static  String IS_USER_AUTHENTICATED_URL = SERVER_URL + "IsUserAuthenticated/";
    public static  String GET_NEWS_JSON_URL = SERVER_URL + "get_NewsJson/";
    public static  String CREATE_USER_URL = SERVER_URL + "create_user/";


}
