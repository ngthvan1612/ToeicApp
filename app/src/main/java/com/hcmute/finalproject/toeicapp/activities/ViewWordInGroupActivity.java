package com.hcmute.finalproject.toeicapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.components.common.CommonHeaderComponent;
import com.hcmute.finalproject.toeicapp.components.dialog.ToeicAlertDialog;
import com.hcmute.finalproject.toeicapp.components.favoritevocab.EditableFavoriteVocabDialog;
import com.hcmute.finalproject.toeicapp.dao.FavoriteVocabWordDao;
import com.hcmute.finalproject.toeicapp.database.ToeicAppDatabase;
import com.hcmute.finalproject.toeicapp.entities.FavoriteVocabWord;

import java.util.ArrayList;
import java.util.List;

public class ViewWordInGroupActivity extends AppCompatActivity {
    private FavoriteVocabWordDao favoriteVocabWordDao;
    private List<FavoriteVocabWord> favoriteVocabWordList;
    private WordAdapter adapter;
    private String groupName;
    private Integer groupId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_word_in_group);

        this.favoriteVocabWordDao = ToeicAppDatabase
                .getInstance(this).getFavoriteVocabWordDao();
        this.favoriteVocabWordList = new ArrayList<>();
        this.adapter = new WordAdapter();

        this.groupName = getIntent().getStringExtra("groupName");
        this.groupId = getIntent().getIntExtra("groupId", 0);
        ((CommonHeaderComponent)findViewById(R.id.activity_view_word_in_group_header_title)).setTitle(groupName);

        RecyclerView recyclerView = findViewById(R.id.activity_view_word_in_group_rcView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(this.adapter);

        this.reloadListWords();

        findViewById(R.id.activity_view_word_in_group_btn_add_word).setOnClickListener(view -> {
            EditableFavoriteVocabDialog dialog = new EditableFavoriteVocabDialog(ViewWordInGroupActivity.this);
            dialog.show();
            dialog.setOnButtonClickedListener(new EditableFavoriteVocabDialog.OnButtonClickedListener() {
                @Override
                public void onOk() {
                    final String word = dialog.getWord().trim();
                    final String meaning = dialog.getMeaning().trim();

                    if (word.length() == 0) {
                        ToeicAlertDialog errorDialog = new ToeicAlertDialog(ViewWordInGroupActivity.this);
                        errorDialog.setDialogMode(ToeicAlertDialog.MODE_ERROR);
                        errorDialog.setMessage("Word cannot be empty");
                        errorDialog.show();
                        return;
                    }

                    if (meaning.length() == 0) {
                        ToeicAlertDialog errorDialog = new ToeicAlertDialog(ViewWordInGroupActivity.this);
                        errorDialog.setDialogMode(ToeicAlertDialog.MODE_ERROR);
                        errorDialog.setMessage("Meaning cannot be empty");
                        errorDialog.show();
                        return;
                    }

                    FavoriteVocabWord favoriteVocabWord = new FavoriteVocabWord();
                    favoriteVocabWord.setGroupId(groupId);
                    favoriteVocabWord.setMeaning(meaning);
                    favoriteVocabWord.setWord(word);

                    favoriteVocabWordDao.insert(favoriteVocabWord);
                    reloadListWords();
                    dialog.dismiss();

                    ToeicAlertDialog successDialog = new ToeicAlertDialog(ViewWordInGroupActivity.this);
                    successDialog.setDialogMode(ToeicAlertDialog.MODE_SUCCESS);
                    successDialog.setMessage("Create word successfully");
                    successDialog.show();
                }

                @Override
                public void onCancel() {
                    dialog.dismiss();
                }
            });
        });
    }

    private void reloadListWords() {
        final List<FavoriteVocabWord> words = this.favoriteVocabWordDao.getAll();
        this.favoriteVocabWordList.clear();
        this.favoriteVocabWordList.addAll(words);
        this.adapter.notifyDataSetChanged();
    }

    private class WordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(ViewWordInGroupActivity.this);
            View view = inflater.inflate(R.layout.activity_view_word_in_group_item, parent, false);
            return new WordItemHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            final FavoriteVocabWord word = favoriteVocabWordList.get(position);
            ((WordItemHolder)holder).setData(word, position);
        }

        @Override
        public int getItemCount() {
            return favoriteVocabWordList.size();
        }

        private class WordItemHolder extends RecyclerView.ViewHolder {
            private TextView txtWord, txtNumber;
            private ImageView btnEdit, btnDelete;

            public WordItemHolder(@NonNull View itemView) {
                super(itemView);

                this.txtNumber = itemView.findViewById(R.id.activity_view_word_in_group_item_txtNumber);
                this.txtWord = itemView.findViewById(R.id.activity_view_word_in_group_item_txtWord);
                this.btnEdit = itemView.findViewById(R.id.activity_view_word_in_group_item_btnEdit);
                this.btnDelete = itemView.findViewById(R.id.activity_view_word_in_group_item_btnRemove);
            }

            public void setData(FavoriteVocabWord wordRef, Integer position) {
                this.txtNumber.setText((position + 1) + "");

                this.btnEdit.setOnClickListener(view -> {
                    EditableFavoriteVocabDialog dialog = new EditableFavoriteVocabDialog(ViewWordInGroupActivity.this);
                    dialog.setMeaning(wordRef.getMeaning());
                    dialog.setWord(wordRef.getWord());
                    dialog.show();
                    dialog.setOnButtonClickedListener(new EditableFavoriteVocabDialog.OnButtonClickedListener() {
                        @Override
                        public void onOk() {
                            final String word = dialog.getWord().trim();
                            final String meaning = dialog.getMeaning().trim();

                            if (word.length() == 0) {
                                ToeicAlertDialog errorDialog = new ToeicAlertDialog(ViewWordInGroupActivity.this);
                                errorDialog.setDialogMode(ToeicAlertDialog.MODE_ERROR);
                                errorDialog.setMessage("Word cannot be empty");
                                errorDialog.show();
                                return;
                            }

                            if (meaning.length() == 0) {
                                ToeicAlertDialog errorDialog = new ToeicAlertDialog(ViewWordInGroupActivity.this);
                                errorDialog.setDialogMode(ToeicAlertDialog.MODE_ERROR);
                                errorDialog.setMessage("Meaning cannot be empty");
                                errorDialog.show();
                                return;
                            }

                            FavoriteVocabWord favoriteVocabWord = new FavoriteVocabWord();
                            favoriteVocabWord.setId(wordRef.getId());
                            favoriteVocabWord.setGroupId(groupId);
                            favoriteVocabWord.setMeaning(meaning);
                            favoriteVocabWord.setWord(word);

                            favoriteVocabWordDao.update(favoriteVocabWord);
                            reloadListWords();
                            dialog.dismiss();

                            ToeicAlertDialog successDialog = new ToeicAlertDialog(ViewWordInGroupActivity.this);
                            successDialog.setDialogMode(ToeicAlertDialog.MODE_SUCCESS);
                            successDialog.setMessage("Update word successfully");
                            successDialog.show();
                        }

                        @Override
                        public void onCancel() {
                            dialog.dismiss();
                        }
                    });
                });

                this.btnDelete.setOnClickListener(view -> {
                    ToeicAlertDialog warningDialog = new ToeicAlertDialog(ViewWordInGroupActivity.this);
                    warningDialog.setDialogMode(ToeicAlertDialog.MODE_QUESTION);
                    warningDialog.setMessage("Are you sure?");
                    warningDialog.show();
                    warningDialog.setOnDialogButtonClickedListener(new ToeicAlertDialog.OnDialogButtonClickedListener() {
                        @Override
                        public void onOk() {
                            favoriteVocabWordDao.delete(wordRef);
                            reloadListWords();
                            warningDialog.dismiss();
                            ToeicAlertDialog successDialog = new ToeicAlertDialog(ViewWordInGroupActivity.this);
                            successDialog.setDialogMode(ToeicAlertDialog.MODE_SUCCESS);
                            successDialog.setMessage("Delete word successfully");
                            successDialog.show();
                        }

                        @Override
                        public void onCancel() {
                            warningDialog.dismiss();
                        }
                    });
                });

                this.txtWord.setText(wordRef.getWord());
            }
        }
    }
}