package com.hcmute.finalproject.toeicapp.components.part_one;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.model.toeic.TestToeicPart;

import java.util.List;

public class PartOneTestListComponent extends LinearLayout {
    private RecyclerView rvTestList;
    private List<TestToeicPart> toeicPartList;
    public PartOneTestListComponent(Context context) {
        this(context,null);
        this.initComponent(context);
    }

    public PartOneTestListComponent(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PartOneTestListComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PartOneTestListComponent(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initComponent(Context context) {
        View view = inflate(context, R.layout.component_test_list,this);
    }

}
