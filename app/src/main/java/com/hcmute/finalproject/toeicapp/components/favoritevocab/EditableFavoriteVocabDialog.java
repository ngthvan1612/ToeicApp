package com.hcmute.finalproject.toeicapp.components.favoritevocab;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import com.hcmute.finalproject.toeicapp.R;

public class EditableFavoriteVocabDialog extends Dialog {
    private EditText txtWord, txtMeaning;
    private AppCompatButton btnOk, btnCancel;

    public EditableFavoriteVocabDialog(@NonNull Context context) {
        super(context);
    }

    private String word, meaning;

    public String getWord() {
        if (this.txtWord != null) {
            this.word = this.txtWord.getText().toString();
        }

        return this.word;
    }

    public String getMeaning() {
        if (this.txtMeaning != null) {
            this.meaning = this.txtMeaning.getText().toString();
        }

        return this.meaning;
    }

    public void setWord(String word) {
        this.word = word;
        this.handleChangWord();
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
        this.handleChangeMeaning();
    }

    private void handleChangWord() {
        if (this.txtWord != null) {
            this.txtWord.setText(this.word);
        }
    }

    private void handleChangeMeaning() {
        if (this.txtMeaning != null) {
            this.txtMeaning.setText(this.meaning);
        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_editable_favorite_vocab);
        this.getWindow().getDecorView().setBackgroundColor(android.R.color.transparent);

        this.txtWord = findViewById(R.id.component_home_page_favorite_vocab_txt_word);
        this.txtMeaning = findViewById(R.id.component_home_page_favorite_vocab_txt_meaning);
        this.btnOk = findViewById(R.id.dialog_create_favorite_vocab_btn_ok);
        this.btnCancel = findViewById(R.id.dialog_create_favorite_vocab_btn_cancel);

        if (this.onButtonClickedListener != null) {
            this.setOnButtonClickedListener(new OnButtonClickedListener() {
                @Override
                public void onOk() {
                    dismiss();
                }

                @Override
                public void onCancel() {
                    dismiss();
                }
            });
        }

        this.handleChangeMeaning();
        this.handleChangWord();

        this.btnOk.setOnClickListener(view -> {
            if (this.onButtonClickedListener != null) {
                this.onButtonClickedListener.onOk();
            }
        });

        this.btnCancel.setOnClickListener(view -> {
            if (this.onButtonClickedListener != null) {
                this.onButtonClickedListener.onCancel();
            }
        });
    }

    public void setOnButtonClickedListener(OnButtonClickedListener onButtonClickedListener) {
        this.onButtonClickedListener = onButtonClickedListener;
    }

    public OnButtonClickedListener getOnButtonClickedListener() {
        return onButtonClickedListener;
    }

    private OnButtonClickedListener onButtonClickedListener;

    public interface OnButtonClickedListener {
        void onOk();
        void onCancel();
    }
}
