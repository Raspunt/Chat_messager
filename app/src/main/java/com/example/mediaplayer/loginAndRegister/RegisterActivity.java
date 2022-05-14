package com.example.mediaplayer.loginAndRegister;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mediaplayer.ChatActivity.ChatAdapter;
import com.example.mediaplayer.ChatActivity.MessageObj;
import com.example.mediaplayer.R;
import com.example.mediaplayer.Settings;

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

public class RegisterActivity extends AppCompatActivity {

    EditText RegisterUsername;
    EditText RegisterPassword ;
    EditText RegisterPasswordConfirm;
    Button RegisterSubmitBtn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_actvity);

        RegisterUsername = findViewById(R.id.RegisterUsername);
        RegisterPassword = findViewById(R.id.RegisterPassword);
        RegisterPasswordConfirm = findViewById(R.id.RegisterPasswordConfirm);
        RegisterSubmitBtn = findViewById(R.id.RegisterSubmitBtn);


        RegisterSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = RegisterUsername.getText().toString();
                String password = RegisterPassword.getText().toString();
                String passwordConfirm = RegisterPasswordConfirm.getText().toString();


                if (username.equals("")){
                    RegisterUsername.setHint("напишите username");
                }

                if (password.equals("")){
                    RegisterPassword.setHint("нашите пароль");
                }

                if (passwordConfirm.equals("")){
                    RegisterPasswordConfirm.setHint("повторите пароль");
                }

                if (password.equals(passwordConfirm)){

                    RequestBody formBody = new FormBody.Builder()
                            .add("username",username)
                            .add("password", password)
                            .build();

                    send_post(Settings.CREATE_USER_URL,formBody);

                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);


                }else {
                    Toast.makeText(RegisterActivity.this, "Пароли не совпадают", Toast.LENGTH_SHORT).show();

                }

















            }
        });


    }


    public void send_post(String test_url, RequestBody formBody){


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



                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RegisterActivity.this, "Пользователь создан!", Toast.LENGTH_LONG).show();

                        }
                    });






                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

}
