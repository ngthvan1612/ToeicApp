package com.hcmute.finalproject.toeicapp.services.storage;

import android.content.Context;

import com.hcmute.finalproject.toeicapp.dao.ToeicStorageDao;
import com.hcmute.finalproject.toeicapp.database.ToeicAppDatabase;
import com.hcmute.finalproject.toeicapp.entities.ToeicStorage;
import com.hcmute.finalproject.toeicapp.network.APIDownloadFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownloadFileService {
    private final Context context;
    private final ToeicAppDatabase toeicAppDatabase;
    private final ToeicStorageDao toeicStorageDao;
    private final APIDownloadFile apiDownloadFile = APIDownloadFile.getInstance();

    public DownloadFileService(Context context) {
        this.context = context;
        this.toeicAppDatabase = ToeicAppDatabase.getInstance(context);
        this.toeicStorageDao = this.toeicAppDatabase.getToeicStorageDao();
    }

    private static String getExtensions(String fileOrUrl) {
        int pos = fileOrUrl.lastIndexOf('.');
        if (pos < 0) return "";
        return fileOrUrl.substring(pos);
    }

    public void downloadFileAndSaveToInternalStorage(String url, DownloadFileCallback<byte[]> callback) {
        apiDownloadFile.downloadGET(url).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    final byte[] buffer = response.body().bytes();

                    final File root = StorageConfiguration.getRootDirectory(context);
                    final String fileName = UUID.randomUUID() + getExtensions(url);
                    final File newFile = new File(root, fileName);

                    FileOutputStream outputStream = new FileOutputStream(newFile);
                    outputStream.write(buffer);
                    outputStream.close();

                    ToeicStorage item = new ToeicStorage();
                    item.setFileName(fileName);
                    toeicStorageDao.insert(item);

                    if (callback != null) {
                        callback.onSuccess(item, buffer);
                    }
                }
                catch (IOException e) {
                    if (callback != null) {
                        callback.onError(e.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (callback != null) {
                    // TODO: fix thanh tieng viet
                    callback.onError(t.toString());
                }
            }
        });
    }
}
