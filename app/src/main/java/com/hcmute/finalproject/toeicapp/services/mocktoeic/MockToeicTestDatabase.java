package com.hcmute.finalproject.toeicapp.services.mocktoeic;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.hcmute.finalproject.toeicapp.activities.ToeicTestListQuestionsActivity;
import com.hcmute.finalproject.toeicapp.dto.GroupQuestionModelResponse;
import com.hcmute.finalproject.toeicapp.model.toeic.GroupQuestionModel;
import com.hcmute.finalproject.toeicapp.model.toeic.ToeicFullTest;
import com.hcmute.finalproject.toeicapp.model.toeic.ToeicPart;
import com.hcmute.finalproject.toeicapp.model.toeic.ToeicQuestionGroup;
import com.hcmute.finalproject.toeicapp.services.storage.DownloadFileCallback;
import com.hcmute.finalproject.toeicapp.services.storage.DownloadFileService;
import com.hcmute.finalproject.toeicapp.services.storage.StorageConfiguration;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

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

    private String joinQuestionGroupIds(ToeicQuestionGroup toeicQuestionGroup) {
        List<String> ids = toeicQuestionGroup.getQuestions()
                .stream().map(q -> q.getQuestionId().toString()).collect(Collectors.toList());

        return String.join("-", ids);
    }

    public byte[] getAudioFromDisk(Integer partId, ToeicQuestionGroup toeicQuestionGroup) {
        File baseDir = new File(this.rootDirectory, "data-part");
        File partsDirectory = new File(baseDir, partId.toString());
        final File partFileRef = new File(partsDirectory, joinQuestionGroupIds(toeicQuestionGroup) + ".mp3");
        final byte[] buffer = new byte[(int) partFileRef.length()];
        try {
            final FileInputStream fileInputStream;
            fileInputStream = new FileInputStream(partFileRef);
            fileInputStream.read(buffer);
            fileInputStream.close();
            return buffer;
        } catch (IOException e) {
            return null;
        }
    }

    public String getAudioAbsPathFromDisk(Integer partId, ToeicQuestionGroup toeicQuestionGroup) {
        File baseDir = new File(this.rootDirectory, "data-part");
        File partsDirectory = new File(baseDir, partId.toString());
        final File partFileRef = new File(partsDirectory, joinQuestionGroupIds(toeicQuestionGroup) + ".mp3");
        return partFileRef.getAbsolutePath();
    }

    public byte[] getImageFromDisk(Integer partId, ToeicQuestionGroup toeicQuestionGroup) {
        File baseDir = new File(this.rootDirectory, "data-part");
        File partsDirectory = new File(baseDir, partId.toString());
        final File partFileRef = new File(partsDirectory, joinQuestionGroupIds(toeicQuestionGroup) + ".png");
        final byte[] buffer = new byte[(int) partFileRef.length()];
        try {
            final FileInputStream fileInputStream;
            fileInputStream = new FileInputStream(partFileRef);
            fileInputStream.read(buffer);
            fileInputStream.close();
            return buffer;
        } catch (IOException e) {
            return null;
        }
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

    public List<ToeicQuestionGroup> loadToeicPartFromDisk(Integer id) throws IOException {
        File baseDir = new File(this.rootDirectory, "data-part");
        File partsDirectory = new File(baseDir, id.toString());
        final File partFileRef = new File(partsDirectory, "config.json");
        final byte[] buffer = new byte[(int) partFileRef.length()];
        final FileInputStream fileInputStream = new FileInputStream(partFileRef);
        fileInputStream.read(buffer);
        final String json = new String(buffer, StandardCharsets.UTF_8);
        Gson gson = new Gson();
        Log.d("F", json);
        return List.of(gson.fromJson(json, ToeicQuestionGroup[].class));
    }

    public boolean checkToeicPartQuestionsDataDownloaded(Integer id) {
        File baseDir = new File(this.rootDirectory, "data-part");
        File partsDirectory = new File(baseDir, id.toString());
        if (!partsDirectory.exists())
            partsDirectory.mkdirs();
        final File partFileRef = new File(partsDirectory, "config.json");
        return partFileRef.exists();
    }

    private byte[] readAllBytes(ZipInputStream stream) throws IOException {
        final byte[] buffer = new byte[1024];
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        int read = 0;
        while ((read = stream.read(buffer)) != -1) {
            output.write(buffer, 0, read);
        }
        return output.toByteArray();
    }

    public void refreshToeicPartQuestionsDataFromInternet(
            Integer id,
            OnMockToeicStateChanged onMockToeicStateChanged
    ) {
        File baseDir = new File(this.rootDirectory, "data-part");
        File partsDirectory = new File(baseDir, id.toString());
        if (!partsDirectory.exists())
            partsDirectory.mkdirs();
        final File partFileRef = new File(partsDirectory, "config.json");
        if (!partFileRef.exists()) {
            downloadFileService.downloadFileByteArrayAsync(
                    "http://toeic-app.uteoj.com/api/toeic-app/practice/part/" + id + "/download",
                    new DownloadFileCallback<byte[]>() {
                        @Override
                        public void onSuccess(byte[] toeicStorage) {
                            try {
                                ZipInputStream zipInputStream = new ZipInputStream(new ByteArrayInputStream(toeicStorage));
                                ZipEntry zipEntry = null;
                                while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                                    if (zipEntry.isDirectory())
                                        continue;
                                    final String fileName = zipEntry.getName();
                                    final byte[] buffer = readAllBytes(zipInputStream);
                                    FileOutputStream fileOutputStream = new FileOutputStream(new File(partsDirectory, fileName).getAbsolutePath());
                                    fileOutputStream.write(buffer);
                                    fileOutputStream.close();
                                    Log.d("ZIP", "wrote " + fileName + ", size = " + zipEntry.getSize());
                                }
                                zipInputStream.close();
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
