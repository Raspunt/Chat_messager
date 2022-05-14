package com.example.mediaplayer.ChatActivity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediaplayer.NewsActivity.NewsActivity;
import com.example.mediaplayer.R;

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


public class ChatAdapter extends RecyclerView.Adapter<ChatViewHolder> {

    ArrayList<MessageObj> MessageListArr ;
    public static Context context;


    public ChatAdapter (Context ct,ArrayList<MessageObj> MessageListArr){

        this.context = ct;
        this.MessageListArr = MessageListArr;

    }




    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View myMessage = inflater.inflate(R.layout.my_message_item,parent,false);




        return new ChatViewHolder(myMessage);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {

        holder.UsernamePlace.setText(MessageListArr.get(position).username);
        holder.TextPlace.setText(MessageListArr.get(position).text);
        holder.DatePlace.setText(MessageListArr.get(position).date);

        if (ChatActivity.current_user.equals(MessageListArr.get(position).username)){
            holder.messageLayout.setBackgroundResource(R.drawable.my_message_background);
        }else {
            holder.messageLayout.setBackgroundResource(R.drawable.received_message_background);
        }



    }


    @Override
    public int getItemCount() {
        return MessageListArr.size();
    }
}



class ChatViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView TextPlace;
    TextView DatePlace;
    TextView UsernamePlace;
    ConstraintLayout messageLayout;

    public ChatViewHolder(@NonNull View itemView) {
        super(itemView);

        TextPlace = itemView.findViewById(R.id.TextPlace);
        DatePlace = itemView.findViewById(R.id.DatePlace);
        UsernamePlace = itemView.findViewById(R.id.UsernamePlace);
        messageLayout = itemView.findViewById(R.id.messageLayout);

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {


    }

}




