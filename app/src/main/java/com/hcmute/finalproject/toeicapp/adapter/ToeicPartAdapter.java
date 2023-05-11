package com.hcmute.finalproject.toeicapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.model.toeic.ToeicPart;

import java.util.List;

public class ToeicPartAdapter extends RecyclerView.Adapter<ToeicPartAdapter.ToeicPartViewHolder> {
    private static final String TAG = "ToeicPartAdapter";
    private List<ToeicPart> mToeicPartList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public ToeicPartAdapter(Context context, List<ToeicPart> data) {
        mContext = context;
        mToeicPartList = data;
        mLayoutInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public ToeicPartAdapter.ToeicPartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.component_test_list_item, parent, false);
        return new ToeicPartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ToeicPartAdapter.ToeicPartViewHolder holder, int position) {
        ToeicPart toeicPart = mToeicPartList.get(position);
//        bind data to view holder
        holder.tvTestName.setText("Part "+toeicPart.getPartId().toString());
        holder.tvQuestionAmount.setText("2");

    }

    @Override
    public int getItemCount() {
        return mToeicPartList.size();
    }

    class ToeicPartViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTestName;
        private TextView tvQuestionAmount;

        public ToeicPartViewHolder(View itemView) {
            super(itemView);
            tvTestName = (TextView) itemView.findViewById(R.id.component_test_list_item_test_name);
            tvQuestionAmount = (TextView) itemView.findViewById((R.id.component_test_list_item_question_amount));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

    }
}
