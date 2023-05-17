package com.hcmute.finalproject.toeicapp.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.services.backend.authentication.AuthenticationService;
import com.hcmute.finalproject.toeicapp.services.backend.favorite.FavoriteVocabService;

public class LoginActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_LOGIN_GOOGLE = 1720;

    private AuthenticationService authenticationService;
    private FavoriteVocabService favoriteVocabService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.authenticationService = new AuthenticationService(this);
        this.favoriteVocabService = new FavoriteVocabService(this);

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
                favoriteVocabService.backupFavoriteVocabFromServer(new FavoriteVocabService.OnFavoriteVocabServiceListener() {
                    @Override
                    public void onSuccess() {
                        Log.d("BACKUP_FAVORITE", "ok login");
                        finish();
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.d("BACKUP_FAVORITE", "error login " + e.getMessage());
                        finish();
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    }
                });
            }
            catch (ApiException e){
                Toast.makeText(this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
            }
        }
    }
}