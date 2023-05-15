package com.hcmute.finalproject.toeicapp.components.favoritevocab;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.hcmute.finalproject.toeicapp.R;

public class HomePageFavoriteVocabComponent extends LinearLayout {
    public HomePageFavoriteVocabComponent(Context context) {
        this(context, null);
    }

    public HomePageFavoriteVocabComponent(Context context, @Nullable AttributeSet attrs) {
        this(context, null, 0);
    }

    public HomePageFavoriteVocabComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initComponent(context, attrs, defStyleAttr);
    }

    private void initComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        View view = inflate(context, R.layout.component_home_page_favorite_vocab, this);

        if (this.isInEditMode()) {
            return;
        }
    }
}
