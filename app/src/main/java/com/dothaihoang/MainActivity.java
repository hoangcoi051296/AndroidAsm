package com.dothaihoang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.dothaihoang.adapter.ItemAdapter;
import com.dothaihoang.model.Item;
import com.dothaihoang.network.ApiManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    RecyclerView rvPeriod;
    List<Item> listData = new ArrayList<>();
    ItemAdapter adapter;

    TextView tvTemp,tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTemp = findViewById(R.id.tvTemp);
        tvStatus = findViewById(R.id.tvStatus);

        initView();
    }

    private void initView(){
        callApi();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);

        adapter = new ItemAdapter(this,listData);

        rvPeriod = findViewById(R.id.rvPeriod);
        rvPeriod.setLayoutManager(layoutManager);
        rvPeriod.setAdapter(adapter);
    }

    private void callApi(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiManager.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiManager service = retrofit.create(ApiManager.class);

        service.getData().enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                listData = response.body();

                Item firstItem = listData.get(0);
                tvTemp.setText((Double.toString(firstItem.getTemperature().getValue())));
                tvStatus.setText(firstItem.getIconPhrase());

                adapter.reloadData(listData);
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                Log.d("TAG", "Loi: "+t);
            }
        });
    }
}