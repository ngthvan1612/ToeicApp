package com.hcmute.finalproject.toeicapp.components.favoritevocab;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.components.dialog.ToeicAlertDialog;

public class AlertEditGroupComponentDialog extends Dialog {
    private ToeicAlertDialog.OnDialogButtonClickedListener onDialogButtonClickedListener;

    private AppCompatButton btnOK, btnCancel;
    private ImageView imgHeader;
    private EditText txtInput;
    private String groupName;

    public AlertEditGroupComponentDialog(@NonNull Context context) {
        super(context);
    }

    public void setOnDialogButtonClickedListener(ToeicAlertDialog.OnDialogButtonClickedListener onDialogButtonClickedListener) {
        this.onDialogButtonClickedListener = onDialogButtonClickedListener;
    }

    public ToeicAlertDialog.OnDialogButtonClickedListener getOnDialogButtonClickedListener() {
        return onDialogButtonClickedListener;
    }

    private void handleChangeGroupName() {
        if (this.txtInput != null) {
            this.txtInput.setText(this.groupName);
        }
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
        this.handleChangeGroupName();
    }

    public String getGroupName() {
        if (this.txtInput != null) {
            this.groupName = this.txtInput.getText().toString();
        }
        return groupName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.component_alert_edit_group_name);
        this.getWindow().getDecorView().setBackgroundColor(android.R.color.transparent);

        this.imgHeader = findViewById(R.id.component_alert_edit_group_name_img_header);
        this.btnOK = findViewById(R.id.component_alert_edit_group_name_btn_ok);
        this.btnCancel = findViewById(R.id.component_alert_edit_group_name_btn_cancel);
        this.txtInput = findViewById(R.id.component_home_page_favorite_vocab_txt_new_group);

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

        this.handleChangeGroupName();

        if (this.onDialogButtonClickedListener == null) {
            this.setOnDialogButtonClickedListener(new ToeicAlertDialog.OnDialogButtonClickedListener() {
                @Override
                public void onOk() {
                    dismiss();
                }

                @Override
                public void onCancel() {
                    dismiss();
                }
            });
        }
    }

    public interface OnDialogButtonClickedListener {
        void onOk();
        void onCancel();
    }
}
