package com.hcmute.finalproject.toeicapp.services.media;

import java.io.File;

public interface AudioPlayerBackground {
    void setOnAudioPlayerRunningEvent(OnAudioPlayerRunningEvent onAudioPlayerRunningEvent);

    void prepareAudioFile(File file);

    long getDuration();

    boolean isPlaying();

    long getCurrentTime();

    void seekTo(int seconds);

    void start(boolean pauseAllAnotherPlayers);

    void stop();

    void release();

    void pause();

    void pauseAllPlayersExceptMe();

    public interface OnAudioPlayerRunningEvent {
        void afterStarted();

        void afterPaused();

        void onFinished();

        void onException(Exception e);

        void playing(long currentTime);

        void onReady();

        void onProcessing();
    }
}
