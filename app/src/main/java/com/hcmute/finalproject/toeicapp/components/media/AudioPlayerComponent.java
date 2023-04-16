package com.hcmute.finalproject.toeicapp.components.media;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.hcmute.finalproject.toeicapp.R;

public class AudioPlayerComponent extends LinearLayout {
    private ImageView imageViewBtnStartPause;
    private SeekBar seekBarStatus, seekBarVolume;
    private TextView txtCurrentTime, txtTotalTime;
    private int currentTime, totalTime;
    private boolean isPlaying;

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
        this.reRenderTextViewStatus();
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
            this.imageViewBtnStartPause.setImageResource(R.drawable.component_audio_player_icon_pause);
        }
        else {
            this.imageViewBtnStartPause.setImageResource(R.drawable.component_audio_player_icon_play);
        }
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
        this.reRenderTextViewStatus();
        this.seekBarStatus.setMax(totalTime);
    }

    @SuppressLint("DefaultLocale")
    private static String formatTime(int seconds) {
        return String.format("%02d:%02d", seconds / 60, seconds % 60);
    }

    private void reRenderTextViewStatus() {
        this.txtCurrentTime.setText(formatTime(currentTime));
        this.txtTotalTime.setText(" / " + formatTime(totalTime));
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

        this.seekBarVolume.setMax(100);
        this.seekBarVolume.setProgress(50);
        this.setCurrentTime(0);
        this.setTotalTime(129);

        this.imageViewBtnStartPause.setOnClickListener(v -> {
            setPlaying(!isPlaying);
        });

        this.initSeekBarStatus();
    }

    private void initSeekBarStatus() {
        this.seekBarStatus.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (!fromUser)
                    return;

                setCurrentTime(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                setPlaying(false);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                setPlaying(true);
            }
        });
    }
}
