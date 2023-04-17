package com.hcmute.finalproject.toeicapp.services.storage;

import android.content.Context;
import android.content.ContextWrapper;

import java.io.File;

public class StorageConfiguration {
    private static File rootDirectory = null;
    private static final String ROOT_DIRECTORY = "toeic-app-root";


    public static File getRootDirectory(Context context) {
        if (rootDirectory == null) {
            ContextWrapper contextWrapper = new ContextWrapper(context.getApplicationContext());
            rootDirectory = contextWrapper.getDir(ROOT_DIRECTORY, Context.MODE_PRIVATE);
        }

        return rootDirectory;
    }
}
