package com.example.mediaplayer;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginRegisterActivity extends AppCompatActivity {


    Button LoginBtn;
    EditText username_text;
    EditText password_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);


        LoginBtn = findViewById(R.id.LoginBtn);
        username_text = findViewById(R.id.username_text);
        password_text = findViewById(R.id.password_text);

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = username_text.getText().toString();
                String password = password_text.getText().toString();



                Create_user cu = new Create_user(username,password);
                cu.start();



            }
        });

    }


    private static class Create_user extends Thread {

        String username;
        String password;

        public Create_user(String username,String password){
            this.username = username;
            this.password = password;
        }


        public void run() {

            try {

                String test_url = "http://192.168.1.9:8000/IsUserAuthenticated/";
                OkHttpClient client = new OkHttpClient();

                RequestBody formBody = new FormBody.Builder()
                        .add("username", username)
                        .add("password", password)
                        .build();

                Request request = new Request.Builder()
                        .url(test_url)
                        .post(formBody)
                        .build();

                Call call = client.newCall(request);
                Response response = call.execute();





//                URL url = new URL("http://192.168.1.9:8000/IsUserAuthenticated/");
//                URLConnection con = url.openConnection();
//                HttpURLConnection http = (HttpURLConnection) con;
//
//                http.setRequestMethod("POST");
//                http.setRequestProperty("Content-Type", "multipart/form-data; boundary=12345");
//                http.setDoOutput(true);
//
//                String data = "username=" + username + ",password=" + password ;
//                byte[] out = data.getBytes(StandardCharsets.UTF_8);
//
//
//                OutputStream stream = http.getOutputStream();
//                stream.write(out);
//
//
//
//                BufferedReader in = new BufferedReader(new InputStreamReader(
//                        http.getInputStream()));
//
//                String AnswerFromServer = readAll(in);
//                Log.e("data",AnswerFromServer);
//
//                http.disconnect();


            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private  String readAll(Reader rd) throws IOException {
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            return sb.toString();
        }

    }
}