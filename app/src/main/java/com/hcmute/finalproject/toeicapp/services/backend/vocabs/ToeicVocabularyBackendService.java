package com.hcmute.finalproject.toeicapp.services.backend.vocabs;

import android.content.Context;

import androidx.annotation.NonNull;

import com.hcmute.finalproject.toeicapp.services.base.Service;

@Service
public class ToeicVocabularyBackendService {
    private final Context context;

    public ToeicVocabularyBackendService(
            @NonNull Context context
    ) {
        this.context = context;
    }
}
