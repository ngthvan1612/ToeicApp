package com.hcmute.finalproject.toeicapp.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.hcmute.finalproject.toeicapp.R;

public class HomeButtonRoundedComponent extends LinearLayout {
    private TextView txtPartId, txtPartDescription;
    private ImageView imagePart;

    public HomeButtonRoundedComponent(Context context) {
        this(context, null);
    }

    public HomeButtonRoundedComponent(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.initComponent(context, attrs);
    }

    private void initComponent(Context context, @Nullable AttributeSet attrs) {
        View view = inflate(context, R.layout.component_home_button_rounded, this);

        this.txtPartId = view.findViewById(R.id.component_home_button_rounded_text_view_part_id);

        if (attrs != null) {
            final TypedArray typedArray = context.obtainStyledAttributes(attrs, new int[] { R.attr.part_id });
            final int partId = typedArray.getInteger(R.styleable.HomeButtonRoundedComponent_part_id, 0);

            this.txtPartId.setText("Part " + partId);
        }

        if (this.isInEditMode()) {
            return;
        }

        Toast.makeText(this.getContext(), "partId = ", Toast.LENGTH_SHORT).show();

        //TODO: code event, ...
    }
}
