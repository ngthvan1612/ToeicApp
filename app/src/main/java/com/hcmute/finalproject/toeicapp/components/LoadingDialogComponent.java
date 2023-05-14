package com.hcmute.finalproject.toeicapp.components;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.hcmute.finalproject.toeicapp.R;

import org.w3c.dom.Text;

public class LoadingDialogComponent {
    private Activity activity;
    private TextView tv;
//    Dialog dialog = new Dialog(activity,android.R.style.Theme_Light);
    public LoadingDialogComponent(Activity myActivity) {
        activity = myActivity;
    }
    public void startLoadingDialog(Dialog dialog,String message) {
        Log.d("dialog",message);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView =inflater.inflate(R.layout.component_custom_dialog,null);
        tv = (TextView)(dialogView).findViewById(R.id.dialog_textView);
        tv.setText(message);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogView);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();
    }
    public void dismissDialog(Dialog dialog) {
        dialog.dismiss();
    }
}
