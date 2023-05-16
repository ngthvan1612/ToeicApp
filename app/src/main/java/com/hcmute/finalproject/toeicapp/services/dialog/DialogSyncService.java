package com.hcmute.finalproject.toeicapp.services.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;

import androidx.annotation.NonNull;

import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.components.LoadingDialogComponent;
import com.hcmute.finalproject.toeicapp.services.base.Service;

@Service
public class DialogSyncService {
    private static class SemaphoreCounter {
        private Integer counter = 0;

        public synchronized Integer getCounter() {
            return counter;
        }

        public synchronized void setCounter(Integer counter) {
            this.counter = counter;
        }

        public synchronized void increaseCounter(Integer value) {
            this.counter += value;
        }

        public synchronized void decreaseCounter(Integer value) {
            this.counter = Math.max(this.counter - value, 0);
        }
    }

    private static SemaphoreCounter semaphoreCounterInstance = new SemaphoreCounter();
    private static LoadingDialogComponent loadingDialogComponentInstance;
    private static Dialog dialogInstance;

    public synchronized static void showDialog(
            @NonNull Activity activity
    ) {
        semaphoreCounterInstance.increaseCounter(1);
        if (semaphoreCounterInstance.getCounter() == 1) {
            dialogInstance = new Dialog(activity, android.R.style.Theme_Light);
            loadingDialogComponentInstance = new LoadingDialogComponent(activity);
            loadingDialogComponentInstance.startLoadingDialog(dialogInstance, "Đang tải");
        }
        Log.d("DIALOG_SERVICE", "create from " + activity);

    }

    public synchronized static void dismissDialog() {

        if (semaphoreCounterInstance.getCounter() == 1) {
            loadingDialogComponentInstance.dismissDialog(dialogInstance);
            loadingDialogComponentInstance = null;
            dialogInstance = null;
        }
        Log.d("DIALOG_SERVICE", "dispose");
        semaphoreCounterInstance.decreaseCounter(1);
    }
}
