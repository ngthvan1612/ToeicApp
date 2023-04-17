package com.hcmute.finalproject.toeicapp.components;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
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

    @SuppressLint("ResourceType")
    private void initComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this.setOrientation(VERTICAL);
        mainView = inflate(context, R.layout.component_home_button_rounded, this);

        this.txtPartId = mainView.findViewById(R.id.component_home_button_rounded_text_view_part_id);
        this.txtPartDescription = mainView.findViewById(R.id.component_home_button_rounded_text_view_part_description);
        this.imagePart = mainView.findViewById(R.id.component_home_button_rounded_image_part);
        this.mainLayout = mainView.findViewById(R.id.component_home_button_rounded_main_layout);

        try {
            setBackground(ViewUtils.generateBackgroundWithShadow(this.mainLayout, R.color.white, R.dimen.component_home_page_list_practice_dimen_ele, R.color.component_home_page_list_practice_color_blue, R.dimen.component_home_page_list_practice_dimen_radius, Gravity.CENTER));
        }
        catch (Exception ignored) { }

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

    public static class ViewUtils {

        public static Drawable generateBackgroundWithShadow(View view, @ColorRes int backgroundColor,
                                                            @DimenRes int cornerRadius,
                                                            @ColorRes int shadowColor,
                                                            @DimenRes int elevation,
                                                            int shadowGravity) {
            float cornerRadiusValue = view.getContext().getResources().getDimension(cornerRadius);
            int elevationValue = (int) view.getContext().getResources().getDimension(elevation);
            int shadowColorValue = ContextCompat.getColor(view.getContext(),shadowColor);
            int backgroundColorValue = ContextCompat.getColor(view.getContext(),backgroundColor);

            float[] outerRadius = {cornerRadiusValue, cornerRadiusValue, cornerRadiusValue,
                    cornerRadiusValue, cornerRadiusValue, cornerRadiusValue, cornerRadiusValue,
                    cornerRadiusValue};

            Paint backgroundPaint = new Paint();
            backgroundPaint.setStyle(Paint.Style.FILL);
            backgroundPaint.setShadowLayer(cornerRadiusValue, 0, 0, 0);

            Rect shapeDrawablePadding = new Rect();
            shapeDrawablePadding.left = elevationValue;
            shapeDrawablePadding.right = elevationValue;

            int DY;
            switch (shadowGravity) {
                case Gravity.CENTER:
                    shapeDrawablePadding.top = elevationValue;
                    shapeDrawablePadding.bottom = elevationValue;
                    DY = 0;
                    break;
                case Gravity.TOP:
                    shapeDrawablePadding.top = elevationValue*2;
                    shapeDrawablePadding.bottom = elevationValue;
                    DY = -1*elevationValue/3;
                    break;
                default:
                case Gravity.BOTTOM:
                    shapeDrawablePadding.top = elevationValue;
                    shapeDrawablePadding.bottom = elevationValue*2;
                    DY = elevationValue/3;
                    break;
            }

            ShapeDrawable shapeDrawable = new ShapeDrawable();
            shapeDrawable.setPadding(shapeDrawablePadding);

            shapeDrawable.getPaint().setColor(backgroundColorValue);
            shapeDrawable.getPaint().setShadowLayer(cornerRadiusValue, 0, DY, shadowColorValue);

            view.setLayerType(LAYER_TYPE_SOFTWARE, shapeDrawable.getPaint());

            shapeDrawable.setShape(new RoundRectShape(outerRadius, null, null));

            LayerDrawable drawable = new LayerDrawable(new Drawable[]{ shapeDrawable });
            drawable.setLayerInset(0, elevationValue, elevationValue, elevationValue, elevationValue);

            return drawable;

        }
    }

}
