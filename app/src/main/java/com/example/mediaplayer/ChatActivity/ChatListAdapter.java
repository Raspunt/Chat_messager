package com.example.mediaplayer.ChatActivity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediaplayer.NewsActivity.NewsActivity;
import com.example.mediaplayer.R;
import com.example.mediaplayer.Settings;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class ChatListAdapter extends RecyclerView.Adapter<ChatListViewHolder> {

    ArrayList<Chat> chatListArr ;
    public static Context context;


    public ChatListAdapter (Context ct,ArrayList<Chat> chatArr){

        this.context = ct;
        this.chatListArr = chatArr;

    }




    @NonNull
    @Override
    public ChatListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.chat_list_item,parent,false);
        return new ChatListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListViewHolder holder, int position) {
        holder.chatListTitle.setText(chatListArr.get(position).title);
        holder.chatListDesc.setText(chatListArr.get(position).desc);



    }




    @Override
    public int getItemCount() {
        return chatListArr.size();
    }
}



class ChatListViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView chatListTitle;
    TextView chatListDesc;

    public ChatListViewHolder(@NonNull View itemView) {
        super(itemView);

        chatListTitle = itemView.findViewById(R.id.ChatListTitle);
        chatListDesc = itemView.findViewById(R.id.ChatListDisc);

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Log.e("position", String.valueOf(getAdapterPosition()));

        Get_messages messages = new Get_messages(getAdapterPosition());
        messages.start();
        ChatActivity.chat_name = chatListTitle.getText().toString();

    }


    private class Get_messages extends Thread {

        int chat_id;

        public Get_messages(int chat_id) {
            this.chat_id = chat_id;
        }


        public void run() {

            try {

                OkHttpClient client = new OkHttpClient();

                RequestBody formBody = new FormBody.Builder()
                        .add("chat_id",Integer.toString(chat_id))
                        .build();

                Request request = new Request.Builder()
                        .url(Settings.GET_MESSAGES_BY_CHAT_ID_URL)
                        .post(formBody)
                        .build();

                Call call = client.newCall(request);
                Response response = call.execute();


                String responseAns = Objects.requireNonNull(response.body()).string();

                Log.e("ee",responseAns.toString());
                ChatActivity.messageObjs = new JSONArray(responseAns);
                ChatActivity.current_chat_id = chat_id ;


                Intent intent = new Intent(ChatListAdapter.context, ChatActivity.class);
                ChatListAdapter.context.startActivity(intent);




            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }
    }
}




