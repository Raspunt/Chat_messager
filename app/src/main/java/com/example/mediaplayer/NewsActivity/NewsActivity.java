package com.example.mediaplayer.NewsActivity;

import static java.nio.file.Paths.get;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mediaplayer.R;
import com.example.mediaplayer.Settings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {

    TextView tv;
    RecyclerView listNews ;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newsactivity);


        Button MainPageBtn = findViewById(R.id.main_pageBtn);
        listNews = findViewById(R.id.listNews);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);



             Thread sp = new send_post();

             sp.start();

        }

    @RequiresApi(api = Build.VERSION_CODES.O)


    private class send_post extends Thread{




    public  void run() {

        try {

            URL url = new URL(Settings.GET_NEWS_JSON_URL);
            URLConnection con = url.openConnection();
            HttpURLConnection http = (HttpURLConnection) con;
            http.setRequestMethod("POST"); // PUT is another valid option
            http.setDoOutput(true);

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    http.getInputStream()));

            String jsonStr = readAll(in);
            JSONObject jsonData = new JSONObject(jsonStr);

            JSONArray jarr = (JSONArray) jsonData.get("metalica");
            JSONArray jarrAcdc = (JSONArray) jsonData.get("AcDc");

            ArrayList<String> titles = new ArrayList<>();
            ArrayList<String> dates = new ArrayList<>();
            ArrayList<String> images = new ArrayList<>();

            for (int i = 0; i < jarr.length(); i++) {
                String tit = ((JSONObject) jarr.get(i)).get("title").toString();
                String date = ((JSONObject) jarr.get(i)).get("date").toString();
                String img = ((JSONObject) jarr.get(i)).get("img").toString();

                titles.add("metalica:"+tit);
                dates.add(date);
                images.add(img);
                //Log.e("file:", tit +" "+ date +" "+ img);
            }

            for (int i = 0; i < jarrAcdc.length(); i++) {
                String tit = ((JSONObject) jarr.get(i)).get("title").toString();
                String date = ((JSONObject) jarr.get(i)).get("date").toString();
                String img = ((JSONObject) jarr.get(i)).get("img").toString();

                titles.add("AC/DC:"+tit);
                dates.add(date);
                images.add(img);
                //Log.e("file:", tit +" "+ date +" "+ img);
            }





            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    NewsDataAdapter adapter = new NewsDataAdapter(NewsActivity.this,titles,dates,images);
                    listNews.setAdapter(adapter);
                    LinearLayoutManager llm = new LinearLayoutManager(NewsActivity.this);
                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                    listNews.setLayoutManager(llm);


                }
            });



        } catch (ProtocolException protocolException) {
            protocolException.printStackTrace();
        } catch (MalformedURLException malformedURLException) {
            malformedURLException.printStackTrace();
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
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

