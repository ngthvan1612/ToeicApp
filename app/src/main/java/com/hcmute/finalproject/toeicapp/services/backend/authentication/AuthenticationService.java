package com.hcmute.finalproject.toeicapp.services.backend.authentication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.hcmute.finalproject.toeicapp.services.backend.authentication.model.ToeicUserSignupRequest;
import com.hcmute.finalproject.toeicapp.services.backend.authentication.model.ToeicUserSignupResponse;
import com.hcmute.finalproject.toeicapp.services.base.Service;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Service
public class AuthenticationService {
    private static Context contextInstance;
    private GoogleSignInAccount googleSignInAccount;
    private GoogleSignInOptions googleSignInOptions;
    private GoogleSignInClient googleSignInClient;

    public AuthenticationService(
            @NonNull Context context
    ) {
        if (contextInstance == null) {
            contextInstance = context;
        }

        if (this.googleSignInAccount == null) {
            this.googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .requestProfile()
                    .build();
            this.googleSignInClient = GoogleSignIn.getClient(context, this.googleSignInOptions);
            this.googleSignInAccount = GoogleSignIn.getLastSignedInAccount(context);
        }
    }

    public void LogoutAsync(
            @NonNull OnAccountLogoutAsyncEvent logoutAsyncEvent
    ) {
        this.googleSignInClient.signOut().addOnCompleteListener(task ->{
            contextInstance = null;
            logoutAsyncEvent.onSuccess();
        }).addOnFailureListener(task -> {
            logoutAsyncEvent.onFailure(task.getMessage());
        });
    }

    public void refreshRegisterUserWithServer(
            @NonNull OnRefreshUserListener listener
    ) {
        ToeicUserSignupRequest request = new ToeicUserSignupRequest();
        request.setGmail(this.getUserEmail());
        request.setFullName(this.getUserDisplayName());

        APIAuthentication
                .getInstance()
                .refreshRegisterUser(request)
                .enqueue(new Callback<ToeicUserSignupResponse>() {
                    @Override
                    public void onResponse(Call<ToeicUserSignupResponse> call, Response<ToeicUserSignupResponse> response) {
                        Log.d("REGISTER_USER", "ok " + response.body().getMessage());
                        listener.onSuccess();
                    }

                    @Override
                    public void onFailure(Call<ToeicUserSignupResponse> call, Throwable t) {
                        Log.d("REGISTER_USER", "fail");
                        Log.d("REGISTER_USER", Log.getStackTraceString(t));
                    }
                });
    }

    public Intent getGoogleLoginIntentActivity() {
        return this.googleSignInClient.getSignInIntent();
    }

    public boolean isAuthenticated() {
        return this.googleSignInAccount != null;
    }

    public String getUserDisplayName() {
        return this.googleSignInAccount.getDisplayName();
    }

    public String getUserEmail() {
        return this.googleSignInAccount.getEmail();
    }

    public Uri getPhotoUrl() {
        return this.googleSignInAccount.getPhotoUrl();
    }

    public interface OnAccountLogoutAsyncEvent {
        void onSuccess();
        void onFailure(String message);
    }

    public interface OnRefreshUserListener {
        void onSuccess();
    }
}
