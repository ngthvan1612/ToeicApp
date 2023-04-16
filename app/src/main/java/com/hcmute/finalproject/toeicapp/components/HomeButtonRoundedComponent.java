package com.hcmute.finalproject.toeicapp.components;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.hcmute.finalproject.toeicapp.R;

public class HomeButtonRoundedComponent extends LinearLayout {
    private TextView txtPartId, txtPartDescription;
    private ImageView imagePart;
    private String description;
    private String title;
    private Drawable imageDrawable;
    private ConstraintLayout mainLayout;
    private View mainView;

    public HomeButtonRoundedComponent(Context context) {
        this(context, null);
    }

    public HomeButtonRoundedComponent(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeButtonRoundedComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public HomeButtonRoundedComponent(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.initComponent(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        mainView = inflate(context, R.layout.component_home_button_rounded, this);

        this.txtPartId = mainView.findViewById(R.id.component_home_button_rounded_text_view_part_id);
        this.txtPartDescription = mainView.findViewById(R.id.component_home_button_rounded_text_view_part_description);
        this.imagePart = mainView.findViewById(R.id.component_home_button_rounded_image_part);
        this.mainLayout = mainView.findViewById(R.id.component_home_button_rounded_main_layout);

        if (attrs != null) {
            final TypedArray typedArrayResources = context.getTheme().obtainStyledAttributes(attrs, R.styleable.HomeButtonRoundedComponent, defStyleAttr, defStyleRes);

            try {
                if (typedArrayResources.hasValue(R.styleable.HomeButtonRoundedComponent_toeic_title))
                    this.setTitle(typedArrayResources.getString(R.styleable.HomeButtonRoundedComponent_toeic_title));
                else
                    this.setTitle("");

                if (typedArrayResources.hasValue(R.styleable.HomeButtonRoundedComponent_toeic_description))
                    this.setDescription(typedArrayResources.getString(R.styleable.HomeButtonRoundedComponent_toeic_description));
                else
                    this.setDescription("");

                if (typedArrayResources.hasValue(R.styleable.HomeButtonRoundedComponent_toeic_image))
                    this.setImageDrawable(typedArrayResources.getDrawable(R.styleable.HomeButtonRoundedComponent_toeic_image));

                if (typedArrayResources.hasValue(R.styleable.HomeButtonRoundedComponent_toeic_background_color))
                    this.setBackgroundColorById(typedArrayResources.getInteger(R.styleable.HomeButtonRoundedComponent_toeic_background_color, 0));
            } catch (Exception ignored) {}
        }

        if (this.isInEditMode()) {
            return;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        this.txtPartId.setText(title);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        this.txtPartDescription.setText(description);
        if (description.isEmpty()) {
            this.txtPartDescription.setVisibility(View.GONE);
        }
    }

    public Drawable getImageDrawable() {
        return imageDrawable;
    }

    public void setImageDrawable(Drawable drawable) {
        this.imageDrawable = drawable;
        this.imagePart.setImageDrawable(drawable);
    }

    private void setBackgroundColorById(int colorId) {
        // TODO: fix hard code
        if (colorId == 0) {
            this.mainLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.component_home_button_rounded_background_blue));
        }
        else if (colorId == 1) {
            this.mainLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.component_home_button_rounded_background_red));
        }
    }
}
