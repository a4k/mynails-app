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
import com.example.mynails.model.Service;

import java.util.List;

public class ServiceViewAdapter extends RecyclerView.Adapter<ServiceViewAdapter.MyViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Service item);
    }

    private Context mContext;
    private List<Service> cardData;
    private final OnItemClickListener listener;



    public ServiceViewAdapter(Context mContext, List<Service> cardData, OnItemClickListener listener) {
        this.mContext = mContext;
        this.cardData = cardData;
        this.listener = listener;



    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View currentView = inflater.inflate(R.layout.service_row_item, viewGroup, false);

        return new MyViewHolder(currentView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int position) {

        myViewHolder.item_title.setText(cardData.get(position).getName());

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


            item_title = (TextView) itemView.findViewById(R.id.item_title);


        }

        public void bind(final Service item, final OnItemClickListener listener) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onItemClick(item);

                }
            });
        }

    }


}
