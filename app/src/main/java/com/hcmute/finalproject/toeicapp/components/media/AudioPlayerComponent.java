package com.hcmute.finalproject.toeicapp.components.media;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.services.media.AudioPlayerBackground;
import com.hcmute.finalproject.toeicapp.services.media.AudioPlayerBackgroundFactory;

import java.io.File;

public class AudioPlayerComponent extends LinearLayout {
    private ImageView imageViewBtnStartPause;
    private SeekBar seekBarStatus, seekBarVolume;
    private TextView txtCurrentTime, txtTotalTime;
    private int currentTime, totalTime;
    private boolean isPlaying, isPrepared;
    private OnAudioPlayerChange onAudioPlayerChange;
    private File audioFile;
    private float currentVolume = 0.5f;
    private AudioPlayerBackground audioPlayerBackground;

    public AudioPlayerComponent(Context context) {
        this(context, null);
    }

    public AudioPlayerComponent(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AudioPlayerComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initComponent(context, attrs, defStyleAttr);
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
        ((Activity)getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                reRenderTextViewStatus();
            }
        });
        this.seekBarStatus.setProgress(currentTime);
    }

    public int getTotalTime() {
        return totalTime;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
        if (playing) {
            this.audioPlayerBackground.start(true);
            this.imageViewBtnStartPause.setImageResource(R.drawable.component_audio_player_icon_pause);
        }
        else {
            this.audioPlayerBackground.pause();
            this.imageViewBtnStartPause.setImageResource(R.drawable.component_audio_player_icon_play);
        }
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
        this.reRenderTextViewStatus();
        this.seekBarStatus.setMax(totalTime);
    }

    public void loadAudioFile(File audioFile) {
        this.audioPlayerBackground.prepareAudioFile(audioFile);
        this.audioPlayerBackground.setVolume(currentVolume);
        audioFile = audioFile;
        isPrepared = true;
    }

    public float getCurrentVolume() {
        return currentVolume;
    }

    public void setCurrentVolume(float currentVolume) {
        this.currentVolume = currentVolume;
        this.audioPlayerBackground.setVolume(currentVolume);
    }

    public boolean isPrepared() {
        return this.isPrepared;
    }

    @SuppressLint("DefaultLocale")
    private static String formatTime(int seconds) {
        return String.format("%02d:%02d", seconds / 60, seconds % 60);
    }

    private void reRenderTextViewStatus() {
        this.txtCurrentTime.setText(formatTime(currentTime));
        this.txtTotalTime.setText(" / " + formatTime(totalTime));
    }

    public OnAudioPlayerChange getOnAudioPlayerChange() {
        return onAudioPlayerChange;
    }

    public void setOnAudioPlayerStateChanged(OnAudioPlayerChange onAudioPlayerChange) {
        this.onAudioPlayerChange = onAudioPlayerChange;
    }

    private void initComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        View view = inflate(context, R.layout.component_audio_player, this);

        this.imageViewBtnStartPause = view.findViewById(R.id.component_audio_player_btn_start_pause);
        this.seekBarStatus = view.findViewById(R.id.component_audio_player_seek_bar_status);
        this.txtCurrentTime = view.findViewById(R.id.component_audio_player_txt_current_time);
        this.txtTotalTime = view.findViewById(R.id.component_audio_player_txt_total_time);
        this.seekBarVolume = view.findViewById(R.id.component_audio_player_seek_bar_volume);

        if (this.isInEditMode()) {
            return;
        }

        this.audioPlayerBackground = AudioPlayerBackgroundFactory.createInstance(AudioPlayerBackgroundFactory.AudioMediaPlayerLibrary.GOOGLE_EXO_PLAYER, context);

        this.seekBarVolume.setMax(100);
        this.seekBarVolume.setProgress(50);
        this.setCurrentTime(0);
        this.setTotalTime(0);

        this.imageViewBtnStartPause.setOnClickListener(v -> {
            if (onAudioPlayerChange != null) {
                if (isPlaying) {
                    if (onAudioPlayerChange.onPauseButtonClicked()) {
                        setPlaying(false);
                    }
                }
                else {
                    if (onAudioPlayerChange.onPlayButtonClicked()) {
                        setPlaying(true);
                    }
                }
            }
        });

        this.initSeekBarStatus();
        this.initSeekBarVolume();
        this.initAudioPlayerBackgroundService();
    }

    private void initSeekBarStatus() {
        this.seekBarStatus.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (!fromUser)
                    return;
                audioPlayerBackground.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //this.audioPlayerBackground.save();
                audioPlayerBackground.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                audioPlayerBackground.start(true);
                //AudioPlayerBackgroundService.seekCurrentAudio(seekBar.getProgress());
                //AudioPlayerBackgroundService.restoreAudioPlayerState();
            }
        });
    }

    private void initSeekBarVolume() {
        this.seekBarVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (!fromUser)
                    return;

                setCurrentVolume((float)(1.0 * progress / seekBar.getMax()));

                if (onAudioPlayerChange != null) {
                    onAudioPlayerChange.onChangeVolume(progress, seekBarVolume.getMax());
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void initAudioPlayerBackgroundService() {
        this.audioPlayerBackground.setOnAudioPlayerRunningEvent(new AudioPlayerBackground.OnAudioPlayerRunningEvent() {
            @Override
            public void afterStarted() {
                ((Activity)getContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imageViewBtnStartPause.setImageResource(R.drawable.component_audio_player_icon_pause);
                    }
                });
            }

            @Override
            public void afterPaused() {
                ((Activity)getContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imageViewBtnStartPause.setImageResource(R.drawable.component_audio_player_icon_play);
                    }
                });
            }

            @Override
            public void onFinished() {
                audioPlayerBackground.seekTo(0);
                setPlaying(false);
                setCurrentTime(0);
            }

            @Override
            public void onException(Exception e) {

            }

            @Override
            public void playing(long currentTime) {
                setCurrentTime((int)currentTime);
            }

            @Override
            public void onReady() {
                setEnabled(true);
                if (getTotalTime() != audioPlayerBackground.getDuration())
                    setTotalTime((int) audioPlayerBackground.getDuration());
            }

            @Override
            public void onProcessing() {
                setEnabled(false);
            }
        });
    }

    public interface OnAudioPlayerChange {
        boolean onPlayButtonClicked();
        boolean onPauseButtonClicked();
        void onChangeVolume(int currentVolumne, int maxVolume);
    }
}
