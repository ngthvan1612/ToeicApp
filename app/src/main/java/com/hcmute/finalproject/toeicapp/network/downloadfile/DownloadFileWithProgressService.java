package com.hcmute.finalproject.toeicapp.network.downloadfile;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.hcmute.finalproject.toeicapp.services.storage.StorageConfiguration;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class DownloadFileWithProgressService {
    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";

    private final File downloadFileCachedDirectory;
    private final Context context;

    public DownloadFileWithProgressService(
            @NonNull Context context
    ) {
        this.context = context;
        this.downloadFileCachedDirectory = new File(StorageConfiguration.getRootDirectory(context), "cache-downloaded");
    }

    public void downloadFileAsync(
            @NonNull String url,
            @NonNull OnDownloadListener<byte[]> onDownloadListener
    ) {
        this.downloadFileAsync(METHOD_GET, url, null, onDownloadListener);
    }

    public void downloadFileAsync(
            @NonNull String method,
            @NonNull String url,
            @Nullable Map<String, Object> data,
            @NonNull OnDownloadListener<byte[]> onDownloadListener
    ) {
        new DownloadFileServiceAsyncTask(
                method,
                data,
                onDownloadListener
        ).execute(url);
    }

    private static class DownloadFileServiceAsyncTask extends AsyncTask<String, Integer, byte[]> {
        private final OnDownloadListener<byte[]> callback;
        private static final int DOWNLOAD_BUFFER = 4 * 1024;
        private Exception exception = null;
        private final String method;
        private final Map<String, Object> data;

        public DownloadFileServiceAsyncTask(
                String method,
                Map<String, Object> data,
                OnDownloadListener<byte[]> callback
        ) {
            this.data = data;
            this.method = method;
            this.callback = callback;
        }

        @Override
        protected byte[] doInBackground(String... urls) {
            InputStream inputStream = null;
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            try {
                final URL url = new URL(urls[0]);
                final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod(this.method);

                if (this.method.equals(METHOD_POST) && this.data != null) {
                    connection.setRequestProperty("Content-Type", "application/json");
                    Gson gson = new Gson();
                    String json = gson.toJson(this.data);
                    DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
                    dataOutputStream.write(json.getBytes(StandardCharsets.UTF_8));
                    dataOutputStream.flush();
                    dataOutputStream.close();
                }

                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    this.exception = new Exception(connection.getResponseMessage());
                    return null;
                }

                inputStream = connection.getInputStream();

                final int fileLength = connection.getContentLength();
                byte[] buffer = new byte[DOWNLOAD_BUFFER];
                int total = 0;
                int count = 0;

                while ((count = inputStream.read(buffer)) != -1) {
                    if (isCancelled()) {
                        inputStream.close();
                        outputStream.close();
                        this.exception = new Exception("Download is cancelled");
                        return null;
                    }
                    total += count;
                    if (fileLength > 0) {
                        publishProgress((total * 100) / fileLength);
                    }
                    outputStream.write(buffer, 0, count);
                }
            } catch (IOException e) {
                exception = e;
            }
            finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        exception = e;
                        return null;
                    }
                }
                try {
                    outputStream.close();
                } catch (IOException e) {
                    exception = e;
                    return null;
                }
            }

            return outputStream.toByteArray();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            this.callback.onProgressUpdate(values[0]);
        }

        @Override
        protected void onPostExecute(byte[] data) {
            assert (exception == null) ^ (data == null);
            if (exception != null) {
                this.callback.onError(exception);
            }
            else {
                this.callback.onSuccess(data);
            }
        }
    }
}
