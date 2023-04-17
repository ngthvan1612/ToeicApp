package com.hcmute.finalproject.toeicapp.services.media;

import android.content.Context;

import java.io.File;

public class AudioPlayerBackgroundService {
    private static AudioPlayerBackground instance = null;
    private static AudioPlayerBackground.OnAudioPlayerRunningEvent onAudioPlayerRunningEventInstance;

    private static AudioPlayerBackground getInstance(Context context) {
        if (instance != null) {
            try {
                instance.setOnAudioPlayerRunningEvent(null);
                if (instance.isPlaying())
                    instance.pause();
                instance.stop();
                instance.release();
            }
            catch (Exception ignored) { }
        }

        instance = AudioPlayerBackgroundFactory.createInstance(
                AudioPlayerBackgroundFactory.AudioMediaPlayerLibrary.GOOGLE_EXO_PLAYER,
                context
        );

        instance.setOnAudioPlayerRunningEvent(onAudioPlayerRunningEventInstance);

        return instance;
    }

    public static void setOnAudioPlayerRunningEvent(AudioPlayerBackground.OnAudioPlayerRunningEvent onAudioPlayerRunningEvent) {
        onAudioPlayerRunningEventInstance = onAudioPlayerRunningEvent;
        if (instance != null) {
            instance.setOnAudioPlayerRunningEvent(onAudioPlayerRunningEventInstance);
        }
    }

    private static boolean storePlaying = false;

    public static void backupAudioPlayerState() {
        storePlaying = false;

        if (instance != null) {
            storePlaying = instance.isPlaying();
            instance.pause();
        }
    }

    public static void restoreAudioPlayerState() {
        if (storePlaying && instance != null) {
            instance.start(true);
        }
    }

    public static void prepareAudio(Context context, File file) {
        AudioPlayerBackground audioPlayer = getInstance(context);
        audioPlayer.prepareAudioFile(file);
    }

    public static void continueCurrentAudio() {
        if (instance != null) {
            instance.start(true);
        }
    }

    public static int getCurrentAudioDuration() {
        if (instance == null) return 0;
        return (int)instance.getDuration();
    }

    public static void pauseCurrentAudio() {
        if (instance != null) {
            instance.pause();
        }
    }

    public static void seekCurrentAudio(int time) {
        if (instance != null)
            instance.seekTo(time);
    }

    public static void setVolume(float volume) {
        if (instance != null)
            instance.setVolume(volume);
    }
}
