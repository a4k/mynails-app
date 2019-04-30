package com.example.mynails.adapters;

import android.content.Context;
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
import com.example.mynails.model.Master;

import org.w3c.dom.Text;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<Master> masterData;

    private RequestOptions req_options;


    public RecyclerViewAdapter(Context mContext, List<Master> masterData) {
        this.mContext = mContext;
        this.masterData = masterData;


        // Описание запросов для Glide
        req_options = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View currentView;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        currentView = inflater.inflate(R.layout.master_row_item, viewGroup, false);


        return new MyViewHolder(currentView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.master_title.setText(masterData.get(i).getName());

        // Подгрузка изображения
        Glide.with(mContext).load(masterData.get(i).getImage()).apply(req_options).into(myViewHolder.master_image);

    }

    @Override
    public int getItemCount() {
        return masterData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView master_title;
        ImageView master_image;

        public MyViewHolder(View itemView) {
            super(itemView);


            master_title = (TextView) itemView.findViewById(R.id.item_title);
            master_image = (ImageView) itemView.findViewById(R.id.item_image);

        }
    }

}
