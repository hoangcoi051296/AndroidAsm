package com.dothaihoang.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dothaihoang.R;
import com.dothaihoang.model.Item;
import com.dothaihoang.network.ApiManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter {
    Activity activity;
    List<Item> listData;

    public ItemAdapter(Activity activity, List<Item> listData) {
        this.activity = activity;
        this.listData = listData;
    }

    public void reloadData(List<Item> list){
        this.listData = list;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = activity.getLayoutInflater().inflate(R.layout.item_layout,parent,false);
        ItemHolder holder = new ItemHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Item model = listData.get(position);
        ItemHolder itemHolder = (ItemHolder) holder;

        itemHolder.tvTime.setText(convertTime(model.getDateTime()));
        itemHolder.tvTemperature.setText((Double.toString(model.getTemperature().getValue())));
        Glide.with(activity).load(getLinkIcon(model.getWeatherIcon())).into(itemHolder.ivCover);

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        TextView tvTime,tvTemperature;
        ImageView ivCover;
        public ItemHolder(@NonNull View itemView) {
            super(itemView);

            tvTime = itemView.findViewById(R.id.tvTime);
            tvTemperature = itemView.findViewById(R.id.tvTemperature);
            ivCover = itemView.findViewById(R.id.ivCover);
        }
    }

    public String convertTime(String inputTime) {
        SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = null;
        try {
            date = inFormat.parse(inputTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat outFormat = new SimpleDateFormat("ha");
        String goal = outFormat.format(date);
        return goal;
    }

    public String getLinkIcon(int inputIconCode){
        String code = Integer.toString(inputIconCode);
        if (inputIconCode <10){
            code = "0"+code;
        }
        return  ApiManager.URL_ICON+code+"-s.png";
    };

}
