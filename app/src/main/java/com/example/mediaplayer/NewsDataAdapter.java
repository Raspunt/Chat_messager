package com.example.mediaplayer;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NewsDataAdapter  extends RecyclerView.Adapter<NewsViewHolder> {

    ArrayList<String> titleList,dateList ,imgList ;
    Context context;

    public NewsDataAdapter(Context ct , ArrayList<String> titleList, ArrayList<String> dateList, ArrayList<String> imgList){
        this.titleList = titleList;
        this.dateList = dateList;
        this.imgList = imgList;
        this.context = ct ;

    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.listitemnews,parent,false);

        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        holder.titleItem.setText(titleList.get(position));
        holder.dateItem.setText(dateList.get(position));

    }

    @Override
    public int getItemCount() {
        return titleList.size();
    }

}


class NewsViewHolder extends  RecyclerView.ViewHolder {

    TextView titleItem , dateItem;
    ImageView imgItem ;


    public NewsViewHolder(@NonNull View itemView) {
        super(itemView);
        titleItem = itemView.findViewById(R.id.titleItem);
        dateItem = itemView.findViewById(R.id.dateItem);


    }
}
