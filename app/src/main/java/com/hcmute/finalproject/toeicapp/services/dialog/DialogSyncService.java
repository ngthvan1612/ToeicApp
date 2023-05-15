package com.hcmute.finalproject.toeicapp.services.dialog;

import android.app.Activity;
import android.app.Dialog;

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
            this.counter -= value;
        }
    }

    private static SemaphoreCounter semaphoreCounterInstance = new SemaphoreCounter();
    private static LoadingDialogComponent loadingDialogComponentInstance;
    private static Dialog dialogInstance;

    public synchronized static void showDialog(
            @NonNull Activity activity
    ) {
        if (semaphoreCounterInstance.getCounter() == 0) {
            dialogInstance = new Dialog(activity, android.R.style.Theme_Light);
            loadingDialogComponentInstance = new LoadingDialogComponent(activity);
            loadingDialogComponentInstance.startLoadingDialog(dialogInstance, "Đang đồng bộ");
        }
        semaphoreCounterInstance.increaseCounter(1);
    }

    public synchronized static void dismissDialog() {
        semaphoreCounterInstance.decreaseCounter(1);
        if (semaphoreCounterInstance.getCounter() == 0) {
            loadingDialogComponentInstance.dismissDialog(dialogInstance);
            loadingDialogComponentInstance = null;
            dialogInstance = null;
        }
    }
}
