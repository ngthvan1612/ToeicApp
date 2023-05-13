package com.hcmute.finalproject.toeicapp.components.homepage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.bumptech.glide.Glide;
import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.activities.LoginActivity;
import com.hcmute.finalproject.toeicapp.services.authentication.AuthenticationService;

public class HomePageUserProfileComponent extends LinearLayout {
    private ImageView imgUserPhoto;
    private TextView txtDisplayName, txtEmail;
    private AppCompatButton btnLogout;
    private AuthenticationService authenticationService;

    public HomePageUserProfileComponent(Context context) {
        this(context, null);
    }

    public HomePageUserProfileComponent(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomePageUserProfileComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initComponent(context, attrs, defStyleAttr);
    }

    private void initComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        View view = inflate(context, R.layout.component_home_page_user_profile, this);

        this.imgUserPhoto = view.findViewById(R.id.component_home_page_user_profile_user_image);
        this.txtDisplayName = view.findViewById(R.id.component_home_page_user_profile_txt_full_name);
        this.txtEmail = view.findViewById(R.id.component_home_page_user_profile_txt_email);
        this.btnLogout = view.findViewById(R.id.component_home_page_user_profile_btn_logout);

        if (this.isInEditMode()) {
            return;
        }

        this.authenticationService = new AuthenticationService(this.getContext());

        this.txtDisplayName.setText(this.authenticationService.getUserDisplayName());
        this.txtEmail.setText(this.authenticationService.getUserEmail());

        if (this.authenticationService.getPhotoUrl() == null) {
            Glide.with(this.getContext())
                    .load(R.drawable.component_home_page_user_profile_default_avatar)
                    .circleCrop()
                    .into(this.imgUserPhoto);
        }
        else {
            Glide.with(this.getContext())
                    .load(this.authenticationService.getPhotoUrl())
                    .circleCrop()
                    .into(this.imgUserPhoto);
        }

        this.btnLogout.setOnClickListener(v -> {
            ProgressDialog dialog = new ProgressDialog(getContext());
            dialog.setMessage("Đang xử lý...");
            dialog.show();

            this.authenticationService.LogoutAsync(new AuthenticationService.OnAccountLogoutAsyncEvent() {
                @Override
                public void onSuccess() {
                    dialog.dismiss();
                    Activity activity = (Activity)getContext();
                    activity.finish();

                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    activity.startActivity(intent);
                }

                @Override
                public void onFailure(String message) {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
        });
    }
}
