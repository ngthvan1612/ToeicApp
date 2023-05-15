package com.hcmute.finalproject.toeicapp.services.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.hcmute.finalproject.toeicapp.services.base.Service;

import java.util.UUID;

@Service
public class SharedPreferenceService {
    private static String PREF_TOEIC_TEST_CHECK_SUM = "toeic-test-check-sum";
    private static String SHARED_PREFERENCE_NAME = "toeic-app-pref";
    private static SharedPreferences sharedPreferences = null;

    public SharedPreferenceService(
            Context context
    ) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getApplicationContext().getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        }
    }

    public void setPrefToeicTestCheckSum(String newCheckSum) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREF_TOEIC_TEST_CHECK_SUM, newCheckSum);
        editor.apply();
    }

    public String getPrefToeicTestCheckSum() {
        return sharedPreferences.getString(PREF_TOEIC_TEST_CHECK_SUM, UUID.randomUUID() + "");
    }
}
