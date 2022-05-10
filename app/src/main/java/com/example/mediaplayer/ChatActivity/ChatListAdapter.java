package com.example.mediaplayer.ChatActivity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediaplayer.R;

import java.util.ArrayList;


public class ChatListAdapter extends RecyclerView.Adapter<ChatListViewHolder> {

    ArrayList<Chat> chatListArr ;
    Context context;


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
        holder.chatListTitle.setText(chatListArr.get(position).desc);



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
    }
}



