package com.example.mynails.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mynails.R;
import com.example.mynails.model.Config;
import com.example.mynails.model.Schedule;

import java.util.List;

public class ScheduleTimeViewAdapter extends RecyclerView.Adapter<ScheduleTimeViewAdapter.MyViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Schedule item);
    }

    private Context mContext;
    private List<Schedule> cardData;
    private final OnItemClickListener listener;


    private String api_host = "";


    public ScheduleTimeViewAdapter(Context mContext, List<Schedule> cardData, OnItemClickListener listener) {
        this.mContext = mContext;
        this.cardData = cardData;
        this.listener = listener;

        Config config = new Config();
        api_host = config.getApi_host();

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View currentView = inflater.inflate(R.layout.schedule_time_item, viewGroup, false);

        return new MyViewHolder(currentView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int position) {

        String title = cardData.get(position).getHour() + ":" + cardData.get(position).getMinute();

        myViewHolder.item_title.setText(title);

        myViewHolder.bind(cardData.get(position), listener);

    }



    @Override
    public int getItemCount() {
        return cardData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView item_title;


        public MyViewHolder(final View itemView) {
            super(itemView);


            item_title = itemView.findViewById(R.id.item_title);


        }

        public void bind(final Schedule item, final OnItemClickListener listener) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onItemClick(item);

                }
            });
        }

    }


}
