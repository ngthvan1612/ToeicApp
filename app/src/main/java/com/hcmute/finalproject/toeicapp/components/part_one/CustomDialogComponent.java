package com.hcmute.finalproject.toeicapp.components.part_one;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.hcmute.finalproject.toeicapp.R;

public class CustomDialogComponent {
    public void showDialog(Activity activity, String type, String msg){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        if(type == "ERROR") {
            dialog.setContentView(R.layout.component_error_alert_dialog);
        }
        else {
            dialog.setContentView(R.layout.component_success_alert_dialog);
        }

        TextView text = (TextView) dialog.findViewById(R.id.component_alert_dialog_txt_message);
        text.setText(msg);

        Button dialogButton = (Button) dialog.findViewById(R.id.component_alert_dialog_btn_ok);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }
}
