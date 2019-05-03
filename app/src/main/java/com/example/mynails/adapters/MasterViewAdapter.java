package com.example.mynails.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mynails.R;
import com.example.mynails.activities.MasterDetail;
import com.example.mynails.model.Config;
import com.example.mynails.model.Master;

import java.util.List;

public class MasterViewAdapter extends RecyclerView.Adapter<MasterViewAdapter.MyViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Master item);
    }

    private Context mContext;
    private List<Master> cardData;
    private final OnItemClickListener listener;

    private RequestOptions req_options;

    private String api_host = "";


    public MasterViewAdapter(Context mContext, List<Master> cardData, OnItemClickListener listener) {
        this.mContext = mContext;
        this.cardData = cardData;
        this.listener = listener;

        Config config = new Config();
        api_host = config.getApi_host();


        // Описание запросов для Glide
        req_options = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View currentView = inflater.inflate(R.layout.master_row_item, viewGroup, false);

        return new MyViewHolder(currentView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {

        myViewHolder.item_title.setText(cardData.get(position).getName());

        myViewHolder.bind(cardData.get(position), listener);

        // Подгрузка изображения
        Glide.with(mContext).load(api_host + cardData.get(position).getImage()).apply(req_options).into(myViewHolder.item_image);

    }



    @Override
    public int getItemCount() {
        return cardData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView item_title;
        ImageView item_image;

        public MyViewHolder(View itemView) {
            super(itemView);


            item_title = (TextView) itemView.findViewById(R.id.item_title);
            item_image = (ImageView) itemView.findViewById(R.id.item_image);

        }

        public void bind(final Master item, final OnItemClickListener listener) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {

                    listener.onItemClick(item);

                }
            });
        }

    }


}
