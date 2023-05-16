package com.hcmute.finalproject.toeicapp.services.storage;

import android.content.Context;
import android.os.AsyncTask;

import com.hcmute.finalproject.toeicapp.dao.ToeicStorageDao;
import com.hcmute.finalproject.toeicapp.database.ToeicAppDatabase;
import com.hcmute.finalproject.toeicapp.entities.ToeicStorage;
import com.hcmute.finalproject.toeicapp.network.APIDownloadFile;
import com.hcmute.finalproject.toeicapp.services.base.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Service
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

    public void downloadFileAndSaveToInternalStorage(String url, DownloadFileCallback callback) {
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
                        callback.onSuccess(item);
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

    public void downloadFileAndSaveToInternalStorageAsync(String url, DownloadFileCallback<ToeicStorage> callback) {
        DownloadFileServiceAsyncTask downloadFileServiceAsyncTask = new DownloadFileServiceAsyncTask(this.context, callback);
        downloadFileServiceAsyncTask.execute(url);
    }

    public void downloadFileByteArrayAsync(String url, DownloadFileCallback<byte[]> callback) {
        DownloadFileServiceByteArrayAsyncTask downloadFileServiceAsyncTask = new DownloadFileServiceByteArrayAsyncTask(this.context, callback);
        downloadFileServiceAsyncTask.execute(url);
    }

    private static class DownloadFileServiceAsyncTask extends AsyncTask<String, Integer, ToeicStorage> {
        private final Context context;
        private final DownloadFileCallback<ToeicStorage> callback;
        private static final int DOWNLOAD_BUFFER = 4 * 1024;
        private String lastError = null;

        public DownloadFileServiceAsyncTask(Context context, DownloadFileCallback<ToeicStorage> callback) {
            this.context = context;
            this.callback = callback;
        }

        @Override
        protected ToeicStorage doInBackground(String... urls) {
            final File storageRootDirectory = StorageConfiguration.getRootDirectory(this.context);
            final String fileName = UUID.randomUUID() + getExtensions(urls[0]);
            InputStream inputStream = null;
            FileOutputStream outputStream = null;

            try {
                final URL url = new URL(urls[0]);
                final HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    this.lastError = connection.getResponseMessage();
                    return null;
                }

                inputStream = connection.getInputStream();
                outputStream = new FileOutputStream(new File(storageRootDirectory, fileName));

                final int fileLength = connection.getContentLength();
                byte[] buffer = new byte[DOWNLOAD_BUFFER];
                int total = 0;
                int count = 0;

                while ((count = inputStream.read(buffer)) != -1) {
                    if (isCancelled()) {
                        inputStream.close();
                        lastError = "Tải file bị hủy";
                        return null;
                    }
                    total += count;
                    if (fileLength > 0) publishProgress((total * 100) / fileLength);
                    outputStream.write(buffer, 0, count);
                }
            } catch (IOException e) {
                lastError = e.toString();
            }
            finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        lastError = e.toString();
                        return null;
                    }
                }
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        lastError = e.toString();
                        return null;
                    }
                }
            }

            ToeicStorage item = new ToeicStorage();
            item.setFileName(fileName);

            ToeicStorageDao storageDao = ToeicAppDatabase.getInstance(this.context).getToeicStorageDao();
            List<Long> ids = storageDao.insert(item);

            item.setId(Math.toIntExact(ids.get(0)));

            return item;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            this.callback.onProgressUpdate(values[0]);
        }

        @Override
        protected void onPostExecute(ToeicStorage toeicStorage) {
            assert (lastError == null) ^ (toeicStorage == null);
            if (lastError != null) {
                this.callback.onError(lastError);
            }
            else {
                this.callback.onSuccess(toeicStorage);
            }
        }
    }

    private static class DownloadFileServiceByteArrayAsyncTask extends AsyncTask<String, Integer, byte[]> {
        private final Context context;
        private final DownloadFileCallback<byte[]> callback;
        private static final int DOWNLOAD_BUFFER = 4 * 1024;
        private String lastError = null;

        public DownloadFileServiceByteArrayAsyncTask(Context context, DownloadFileCallback<byte[]> callback) {
            this.context = context;
            this.callback = callback;
        }

        @Override
        protected byte[] doInBackground(String... urls) {
            final File storageRootDirectory = StorageConfiguration.getRootDirectory(this.context);
            final String fileName = UUID.randomUUID() + getExtensions(urls[0]);
            InputStream inputStream = null;
            ByteArrayOutputStream outputStream = null;

            try {
                final URL url = new URL(urls[0]);
                final HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    this.lastError = connection.getResponseMessage();
                    return null;
                }

                inputStream = connection.getInputStream();
                outputStream = new ByteArrayOutputStream();

                final int fileLength = connection.getContentLength();
                byte[] buffer = new byte[DOWNLOAD_BUFFER];
                int total = 0;
                int count = 0;

                while ((count = inputStream.read(buffer)) != -1) {
                    if (isCancelled()) {
                        inputStream.close();
                        lastError = "Tải file bị hủy";
                        return null;
                    }
                    total += count;
                    if (fileLength > 0) publishProgress((total * 100) / fileLength);
                    outputStream.write(buffer, 0, count);
                }

                return outputStream.toByteArray();
            } catch (IOException e) {
                lastError = e.toString();
            }
            finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        lastError = e.toString();
                        return null;
                    }
                }
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        lastError = e.toString();
                        return null;
                    }
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            this.callback.onProgressUpdate(values[0]);
        }

        @Override
        protected void onPostExecute(byte[] data) {
            assert (lastError == null) ^ (data == null);
            if (lastError != null) {
                this.callback.onError(lastError);
            }
            else {
                this.callback.onSuccess(data);
            }
        }
    }
}
