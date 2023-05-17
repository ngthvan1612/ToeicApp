package com.hcmute.finalproject.toeicapp.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hcmute.finalproject.toeicapp.R;

import java.util.ArrayList;
import java.util.List;

public class StatisticActivity extends GradientActivity{
    private RecyclerView rvStatistic;
    private List<String> statisticList;
    private StatisticAdapter statisticAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        rvStatistic = findViewById(R.id.activity_statistic_recycler_view);
        statisticList = new ArrayList<>();
        statisticList.add("statistic 1");
        statisticList.add("statistic 2");
        statisticList.add("statistic 3");
        statisticList.add("statistic 4");
        statisticList.add("statistic 5");
        statisticList.add("statistic 6");

        statisticAdapter =new StatisticAdapter(this,statisticList);
        rvStatistic.setAdapter(statisticAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvStatistic.setLayoutManager(linearLayoutManager);

    }
}
class StatisticAdapter extends RecyclerView.Adapter<StatisticAdapter.StatisticViewHolder> {
    private List<String> statisticList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    public StatisticAdapter(Context context,List<String> datas) {
        mContext = context;
        statisticList = datas;
        mLayoutInflater = LayoutInflater.from(context);
    }


    class StatisticViewHolder extends RecyclerView.ViewHolder{
        private TextView partName;

        public StatisticViewHolder(@NonNull View itemView) {
            super(itemView);
            partName = (TextView) itemView.findViewById(R.id.component_statistic_text_part_desc);

        }

    }
    @NonNull
    @Override
    public StatisticAdapter.StatisticViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.component_statistic_item,parent,false);
        return new StatisticViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StatisticViewHolder holder, int position) {
        String item =statisticList.get(position);
        holder.partName.setText(item);
    }

    @Override
    public int getItemCount() {
       return statisticList.size();
    }
}