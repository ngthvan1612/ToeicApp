package com.hcmute.finalproject.toeicapp.components.homepage;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.dao.StatisticDao;
import com.hcmute.finalproject.toeicapp.database.ToeicAppDatabase;
import com.hcmute.finalproject.toeicapp.entities.Statistic;
import com.hcmute.finalproject.toeicapp.services.statistic.StatisticService;
import com.hcmute.finalproject.toeicapp.services.statistic.model.StatisticView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HomePageStatisticComponent extends LinearLayout {
    private RecyclerView recyclerView;
    private StatisticAdapter adapter;
    private StatisticDao statisticDao;
    private List<StatisticView> statisticViewList;

    public HomePageStatisticComponent(Context context) {
        this(context, null);
    }

    public HomePageStatisticComponent(Context context, @Nullable AttributeSet attrs) {
        this(context, null, 0);
    }

    public HomePageStatisticComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initComponent(context, attrs, defStyleAttr);
    }

    private void initComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        View view = inflate(context, R.layout.activity_statistic, this);
        this.recyclerView = view.findViewById(R.id.activity_statistic_recycler_view);

        if (this.isInEditMode()) {
            return;
        }

        this.statisticDao = ToeicAppDatabase
                .getInstance(context)
                .getStatisticDao();
        this.statisticViewList = new ArrayList<>();

        this.adapter = new StatisticAdapter(getContext(), this.statisticViewList);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.recyclerView.setAdapter(this.adapter);

        this.reloadListStatistic();
    }

    public void reloadListStatistic() {
        this.statisticViewList.clear();
        final List<Statistic> statistics = this.statisticDao.getAll();
        this.statisticViewList.addAll(statistics
                .stream()
                .map(StatisticView::new)
                .collect(Collectors.toList())
        );
        this.adapter.notifyDataSetChanged();
    }

    private class StatisticAdapter extends RecyclerView.Adapter<StatisticAdapter.StatisticViewHolder> {
        private List<StatisticView> statisticList;
        private Context mContext;
        private LayoutInflater mLayoutInflater;

        public StatisticAdapter(Context context,List<StatisticView> datas) {
            mContext = context;
            statisticList = datas;
            mLayoutInflater = LayoutInflater.from(context);
        }


        private class StatisticViewHolder extends RecyclerView.ViewHolder {
            private TextView txtHour, txtDate, txtTestType, txtTestName;
            private TextView txtCorrect, txtWrong, txtNoSelected;

            public StatisticViewHolder(@NonNull View itemView) {
                super(itemView);

                this.txtHour = itemView.findViewById(R.id.component_statistic_text_time);
                this.txtDate = itemView.findViewById(R.id.component_statistic_text_date);
                this.txtTestType = itemView.findViewById(R.id.component_statistic_text_part_number);
                this.txtTestName = itemView.findViewById(R.id.component_statistic_text_part_desc);
                this.txtCorrect = itemView.findViewById(R.id.component_statistic_correct_answer_number);
                this.txtWrong = itemView.findViewById(R.id.component_statistic_text_wrong_answer_number);
                this.txtNoSelected = itemView.findViewById(R.id.component_statistic_text_selected_number);
            }

            public void bindingData(StatisticView data) {
                this.txtHour.setText(data.getHours());
                this.txtDate.setText(data.getDate());
                this.txtTestType.setText(data.getType());
                this.txtTestName.setText(data.getName());
                this.txtCorrect.setText(data.getCorrect() + "");
                this.txtWrong.setText(data.getWrong() + "");
                this.txtNoSelected.setText(data.getNoSelected() + "");
            }
        }
        @NonNull
        @Override
        public StatisticAdapter.StatisticViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = mLayoutInflater.inflate(R.layout.component_statistic_item,parent,false);
            return new StatisticAdapter.StatisticViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull StatisticAdapter.StatisticViewHolder holder, int position) {
            final StatisticView item = statisticList.get(position);
            holder.bindingData(item);
        }

        @Override
        public int getItemCount() {
            return statisticList.size();
        }
    }
}
