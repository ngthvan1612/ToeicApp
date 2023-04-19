package com.hcmute.finalproject.toeicapp.services.mocktoeic;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.hcmute.finalproject.toeicapp.dto.GroupQuestionModelResponse;
import com.hcmute.finalproject.toeicapp.model.toeic.GroupQuestionModel;
import com.hcmute.finalproject.toeicapp.model.toeic.ToeicFullTest;
import com.hcmute.finalproject.toeicapp.services.storage.DownloadFileCallback;
import com.hcmute.finalproject.toeicapp.services.storage.DownloadFileService;
import com.hcmute.finalproject.toeicapp.services.storage.StorageConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class MockToeicTestDatabase {
    private final Context context;
    private final File rootDirectory;
    private final DownloadFileService downloadFileService;
    private static List<ToeicFullTest> toeicFullTests = null;

    public MockToeicTestDatabase(Context context) {
        this.context = context;
        this.rootDirectory = StorageConfiguration.getRootDirectory(context);
        this.downloadFileService = new DownloadFileService(context);
    }

    private String getToeicFileNameJson(Integer partId) {
        return "part-" + partId + ".json";
    }

    private File getToeicFileReference(Integer partId) {
        return new File(this.rootDirectory, this.getToeicFileNameJson(partId));
    }

    public void refreshToeicDataFromInternet(
            Integer partId,
            OnMockToeicStateChanged onMockToeicStateChanged
    ) {
        final File partFileRef = this.getToeicFileReference(partId);
        if (!partFileRef.exists()) {
            downloadFileService.downloadFileByteArrayAsync(
                    "http://toeic-app.uteoj.com/api/toeic-app/practice/" + partId,
                    new DownloadFileCallback<byte[]>() {
                        @Override
                        public void onSuccess(byte[] toeicStorage) {
                            FileOutputStream fileOutputStream = null;
                            try {
                                fileOutputStream = new FileOutputStream(partFileRef);
                                Gson gson = new Gson();
                                Log.d("AF", new String(toeicStorage, StandardCharsets.UTF_8));
                                GroupQuestionModelResponse response = gson.fromJson(new String(toeicStorage, StandardCharsets.UTF_8), GroupQuestionModelResponse.class);
                                if (!response.isSuccess()) {
                                    onMockToeicStateChanged.onError(response.getMessage());
                                    return;
                                }
                                fileOutputStream.write(gson.toJson(response.getData()).getBytes(StandardCharsets.UTF_8));
                                fileOutputStream.close();
                                onMockToeicStateChanged.onSuccess("Tải dữ liệu thành công");
                            } catch (IOException e) {
                                onMockToeicStateChanged.onError("Tải dữ liệu lỗi");
                            }
                        }

                        @Override
                        public void onProgressUpdate(int percent) {
                            onMockToeicStateChanged.onProgress(percent);
                        }

                        @Override
                        public void onError(String error) {
                            onMockToeicStateChanged.onError(error);
                        }
                    }
            );
        }
    }

    public List<GroupQuestionModel> getToeicPartDataFromDisk(Integer partId, OnMockToeicStateChanged onMockToeicStateChanged) {
        final File partFileRef = this.getToeicFileReference(partId);
        try {
            FileInputStream fileInputStream = new FileInputStream(partFileRef);
            final byte[] buffer = new byte[(int) partFileRef.length()];
            fileInputStream.read(buffer);
            final String json = new String(buffer, StandardCharsets.UTF_8);
            fileInputStream.close();
            Gson gson = new Gson();
            List<GroupQuestionModel> result = List.of(gson.fromJson(json, GroupQuestionModel[].class));
            if (onMockToeicStateChanged != null) {
                onMockToeicStateChanged.onSuccess("Đọc file thành công");
            }
            return result;
        } catch (IOException e) {
            if (onMockToeicStateChanged != null) {
                onMockToeicStateChanged.onError("Đọc file lỗi: " + e);
            }
            return null;
        }
    }

    public interface OnMockToeicStateChanged {
        void onSuccess(String message);
        void onProgress(int progress);
        void onError(String error);
    }
}
