package com.example.mediaplayer.loginAndRegister;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mediaplayer.ChatActivity.ChatActivity;
import com.example.mediaplayer.ChatActivity.ChatListActivity;
import com.example.mediaplayer.R;
import com.example.mediaplayer.Settings;

import java.io.IOException;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    Button RegisterLogSubmit ;
    Button LoginBtn;
    EditText username_text;
    EditText password_text;
    Context ctn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        RegisterLogSubmit = findViewById(R.id.RegisterLogSubmit);

        LoginBtn = findViewById(R.id.LoginBtn);
        username_text = findViewById(R.id.username_text);
        password_text = findViewById(R.id.password_text);

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = username_text.getText().toString();
                String password = password_text.getText().toString();
                ctn = view.getContext();



                Create_user cu = new Create_user(username,password);
                cu.start();



            }
        });


        RegisterLogSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });

    }


    private class Create_user extends Thread {

        String username;
        String password;

        public Create_user(String username,String password){
            this.username = username;
            this.password = password;
        }


        public void run() {

            try {

                OkHttpClient client = new OkHttpClient();

                RequestBody formBody = new FormBody.Builder()
                        .add("username", username)
                        .add("password", password)
                        .build();

                Request request = new Request.Builder()
                        .url(Settings.IS_USER_AUTHENTICATED_URL)
                        .post(formBody)
                        .build();

                Call call = client.newCall(request);
                Response response = call.execute();



                String responseAns = Objects.requireNonNull(response.body()).string();


                switch (responseAns){

                    case "passwordWrong":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast tt = Toast.makeText(LoginActivity.this,"пароль не правильный",Toast.LENGTH_LONG);
                                tt.show();
                            }
                        });
                        break;

                    case "passwordRight":
                        ChatActivity.current_user = username;
                        Intent intent = new Intent(ctn, ChatListActivity.class);
                        startActivity(intent);
                        break;

                    case "UserNotExists":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast tt = Toast.makeText(LoginActivity.this,"пользователь не правильный",Toast.LENGTH_LONG);
                                tt.show();
                            }
                        });
                        break;


                    case "DataNotFound":
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast tt = Toast.makeText(LoginActivity.this,"Введите информаию",Toast.LENGTH_LONG);
                                tt.show();
                            }
                        });
                        break;

                }



            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}