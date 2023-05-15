package com.hcmute.finalproject.toeicapp.components.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hcmute.finalproject.toeicapp.R;

public class CommonTestFooterComponent extends LinearLayout {
    public void setOnMenuClickListener(OnMenuClickListener onMenuClickListener) {
        this.onMenuClickListener = onMenuClickListener;
    }

    private OnMenuClickListener onMenuClickListener;
    private BottomNavigationView bottomNavigationView = null;
    public CommonTestFooterComponent(Context context) {
        this(context,null);
    }

    public CommonTestFooterComponent(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }



    public CommonTestFooterComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initComponent(context, attrs, defStyleAttr);
    }

    public CommonTestFooterComponent(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    private void initComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        View view = inflate(context, R.layout.component_common_test_footer, this);
        bottomNavigationView = findViewById(R.id.component_common_test_footer);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                final Integer menuId = item.getItemId();
                switch (menuId) {
                    case R.id.menu_component_test_footer_previous:
                        onMenuClickListener.onPreviousMenuClicked();
                        return true;
                    case R.id.menu_component_test_heart_button:
                        onMenuClickListener.onDictionaryMenuClicked();
                        return true;
                    case R.id.menu_component_test_translate_button:
                        onMenuClickListener.onExplainMenuClicked();
                        return true;
                    case R.id.menu_component_test_next_button:
                        onMenuClickListener.onNextMenuClicked();
                        return true;

                }
                return false;
            }
        });
    }

    public interface OnMenuClickListener {
        void onPreviousMenuClicked();
        void onDictionaryMenuClicked();
        void onExplainMenuClicked();
        void onNextMenuClicked();
    }
}
