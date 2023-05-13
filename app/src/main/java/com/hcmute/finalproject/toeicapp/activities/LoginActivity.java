package com.hcmute.finalproject.toeicapp.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.services.authentication.AuthenticationService;

public class LoginActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_LOGIN_GOOGLE = 1720;

    private AuthenticationService authenticationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.authenticationService = new AuthenticationService(this);

        if (this.authenticationService.isAuthenticated()) {
            finish();
            startActivity(new Intent(this, HomeActivity.class));
            return;
        }

        View.OnClickListener onBtnLoginClicked = view -> {
            Intent intent = this.authenticationService.getGoogleLoginIntentActivity();
            startActivityForResult(intent, REQUEST_CODE_LOGIN_GOOGLE);
        };

        findViewById(R.id.activity_login_btn_login).setOnClickListener(onBtnLoginClicked);
        findViewById(R.id.activity_login_gooogle_image).setOnClickListener(onBtnLoginClicked);
        findViewById(R.id.activity_login_txt_login).setOnClickListener(onBtnLoginClicked);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_LOGIN_GOOGLE) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Toast.makeText(this, "Xin chào " + account.getEmail(), Toast.LENGTH_SHORT).show();

                finish();
                startActivity(new Intent(this, HomeActivity.class));
            }
            catch (ApiException e){
                Toast.makeText(this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
            }
        }
    }
}