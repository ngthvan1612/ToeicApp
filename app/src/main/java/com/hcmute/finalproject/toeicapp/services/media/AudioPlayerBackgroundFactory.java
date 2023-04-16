package com.hcmute.finalproject.toeicapp.services.media;

import android.content.Context;

public class AudioPlayerBackgroundFactory {
    public enum AudioMediaPlayerLibrary {
        ANDROID_EMBED_MEDIA_PLAYER,
        GOOGLE_EXO_PLAYER
    }

    public static AudioPlayerBackground createInstance(
            AudioMediaPlayerLibrary library,
            Context context
    ) {
        if (library == AudioMediaPlayerLibrary.ANDROID_EMBED_MEDIA_PLAYER) {
            return new AudioPlayerBackgroundMediaPlayer(context);
        } else {
            return new AudioPlayerBackgroundExoPlayer(context);
        }
    }
}
