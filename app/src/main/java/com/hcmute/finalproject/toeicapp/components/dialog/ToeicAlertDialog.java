package com.hcmute.finalproject.toeicapp.components.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.hcmute.finalproject.toeicapp.R;

public class ToeicAlertDialog extends Dialog {
    public static final int MODE_ERROR = 1;
    public static final int MODE_SUCCESS = 2;
    public static final int MODE_QUESTION = 3;

    private static final int COLOR_SUCCESS = 0xFF50D04E;
    private static final int COLOR_ERROR = 0xFFDA5F6A;
    private static final int COLOR_QUESTION = 0xFF1089FF;

    private OnDialogButtonClickedListener onDialogButtonClickedListener;

    private AppCompatButton btnOK, btnCancel;
    private ImageView imgHeader;
    private TextView txtMessage;

    public ToeicAlertDialog(@NonNull Context context) {
        super(context);
    }

    private int dialogMode = MODE_ERROR;

    public void setDialogMode(int dialogMode) {
        this.dialogMode = dialogMode;
        switch (this.dialogMode) {
            case MODE_ERROR:
                this.btnOK.setVisibility(View.VISIBLE);
                this.btnCancel.setVisibility(View.GONE);
                this.imgHeader.setBackgroundColor(COLOR_ERROR);
                this.btnOK.setBackgroundColor(COLOR_ERROR);
                break;
            case MODE_SUCCESS:
                this.btnOK.setVisibility(View.VISIBLE);
                this.btnCancel.setVisibility(View.GONE);
                this.imgHeader.setBackgroundColor(COLOR_SUCCESS);
                this.btnOK.setBackgroundColor(COLOR_SUCCESS);
                break;
            case MODE_QUESTION:
                this.btnOK.setVisibility(View.VISIBLE);
                this.btnCancel.setVisibility(View.VISIBLE);
                this.imgHeader.setBackgroundColor(COLOR_QUESTION);
                this.btnOK.setBackgroundColor(COLOR_QUESTION);
                break;
        }
    }

    public void setOnDialogButtonClickedListener(OnDialogButtonClickedListener onDialogButtonClickedListener) {
        this.onDialogButtonClickedListener = onDialogButtonClickedListener;
    }

    public OnDialogButtonClickedListener getOnDialogButtonClickedListener() {
        return onDialogButtonClickedListener;
    }

    public int getDialogMode() {
        return this.dialogMode;
    }

    public void setMessage(String message) {
        this.txtMessage.setText(message);
    }

    public String getMessage(String message) {
        return this.txtMessage.getText().toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.component_alert_dialog);
        this.getWindow().getDecorView().setBackgroundColor(android.R.color.transparent);

        this.imgHeader = findViewById(R.id.component_alert_dialog_image_view);
        this.btnOK = findViewById(R.id.component_alert_dialog_btn_ok);
        this.btnCancel = findViewById(R.id.component_alert_dialog_btn_cancel);
        this.txtMessage = findViewById(R.id.component_alert_dialog_txt_message);

        this.btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDialogButtonClickedListener != null) {
                    onDialogButtonClickedListener.onOk();
                }
            }
        });

        this.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDialogButtonClickedListener != null) {
                    onDialogButtonClickedListener.onCancel();
                }
            }
        });
    }

    public interface OnDialogButtonClickedListener {
        void onOk();
        void onCancel();
    }
}
