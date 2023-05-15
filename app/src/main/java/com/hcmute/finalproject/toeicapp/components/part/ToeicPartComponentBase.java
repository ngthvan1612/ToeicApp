package com.hcmute.finalproject.toeicapp.components.part;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public abstract class ToeicPartComponentBase extends LinearLayout implements ToeicPartComponent {
    public ToeicPartComponentBase(Context context) {
        this(context, null);
    }

    public ToeicPartComponentBase(Context context, @Nullable AttributeSet attrs) {
        this(context, null, 0);
    }

    public ToeicPartComponentBase(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
