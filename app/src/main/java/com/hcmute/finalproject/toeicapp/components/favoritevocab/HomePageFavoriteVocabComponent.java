package com.hcmute.finalproject.toeicapp.components.favoritevocab;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.activities.ViewWordInGroupActivity;
import com.hcmute.finalproject.toeicapp.components.dialog.ToeicAlertDialog;
import com.hcmute.finalproject.toeicapp.dao.FavoriteVocabGroupDao;
import com.hcmute.finalproject.toeicapp.database.ToeicAppDatabase;
import com.hcmute.finalproject.toeicapp.entities.FavoriteVocabGroup;
import com.hcmute.finalproject.toeicapp.testing.duy.activities.DuyTestAddNewWordActivity;

import java.util.ArrayList;
import java.util.List;

public class HomePageFavoriteVocabComponent extends LinearLayout {
    private GroupItemAdapter adapter;
    private ToeicAppDatabase toeicAppDatabase;
    private FavoriteVocabGroupDao favoriteVocabGroupDao;
    private static List<FavoriteVocabGroup> favoriteVocabGroups;

    public HomePageFavoriteVocabComponent(Context context) {
        this(context, null);
    }

    public HomePageFavoriteVocabComponent(Context context, @Nullable AttributeSet attrs) {
        this(context, null, 0);
    }

    public HomePageFavoriteVocabComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initComponent(context, attrs, defStyleAttr);
    }

    private void initComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        View view = inflate(context, R.layout.component_home_page_favorite_vocab, this);

        this.toeicAppDatabase = ToeicAppDatabase.getInstance(getContext());
        this.favoriteVocabGroupDao = this.toeicAppDatabase.getFavoriteVocabGroupDao();

        if (this.isInEditMode()) {
            return;
        }

        this.favoriteVocabGroups = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.component_home_page_favorite_vocab_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.adapter = new GroupItemAdapter();
        recyclerView.setAdapter(this.adapter);

        this.reloadListFavoriteGroups();

        view.findViewById(R.id.component_home_page_favorite_vocab_layout_new_group).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextView txtGroupName = view.findViewById(R.id.component_home_page_favorite_vocab_txt_new_group);
                final String newGroupName = txtGroupName.getText().toString().trim();
                if (newGroupName.length() == 0) {
                    ToeicAlertDialog errorDialog = new ToeicAlertDialog(getContext());
                    errorDialog.setMessage("Group name cannot be empty");
                    errorDialog.setDialogMode(ToeicAlertDialog.MODE_ERROR);
                    errorDialog.show();
                    return;
                }

                FavoriteVocabGroup newGroup = new FavoriteVocabGroup();
                newGroup.setGroupName(newGroupName);
                favoriteVocabGroupDao.insert(newGroup);

                reloadListFavoriteGroups();

                ToeicAlertDialog successDialog = new ToeicAlertDialog(getContext());
                successDialog.setDialogMode(ToeicAlertDialog.MODE_SUCCESS);
                successDialog.setMessage("Added successfully");
                successDialog.show();

                txtGroupName.setText("");
            }
        });
    }

    private void reloadListFavoriteGroups() {
        List<FavoriteVocabGroup> groups = this.favoriteVocabGroupDao.getAll();

        this.favoriteVocabGroups.clear();
        this.favoriteVocabGroups.addAll(groups);

        this.adapter.notifyDataSetChanged();
    }

    private class GroupItemAdapter extends RecyclerView.Adapter {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.activity_home_page_favorite_vocab_group_item, parent, false);
            return new GroupItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            GroupItemViewHolder groupItemViewHolder = (GroupItemViewHolder)holder;
            groupItemViewHolder.setData(favoriteVocabGroups.get(position));
        }

        @Override
        public int getItemCount() {
            return favoriteVocabGroups.size();
        }

        private class GroupItemViewHolder extends RecyclerView.ViewHolder {
            private TextView txtGroupName;
            private ImageView btnEdit, btnDelete;

            public GroupItemViewHolder(@NonNull View itemView) {
                super(itemView);
                this.btnDelete = itemView.findViewById(R.id.activity_home_page_favorite_vocab_group_item_btn_delete);
                this.btnEdit = itemView.findViewById(R.id.activity_home_page_favorite_vocab_group_item_btn_edit);
                this.txtGroupName = itemView.findViewById(R.id.activity_home_page_favorite_vocab_group_item_txt);
            }

            public void setData(FavoriteVocabGroup group) {
                this.txtGroupName.setText(group.getGroupName());
                itemView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), ViewWordInGroupActivity.class);
                        intent.putExtra("groupName", group.getGroupName());
                        intent.putExtra("groupId", group.getId());
                        ((Activity)getContext()).startActivity(intent);
                    }
                });
                this.btnEdit.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertEditGroupComponentDialog dialog = new AlertEditGroupComponentDialog(getContext());
                        dialog.setGroupName(group.getGroupName());
                        dialog.setOnDialogButtonClickedListener(new ToeicAlertDialog.OnDialogButtonClickedListener() {
                            @Override
                            public void onOk() {
                                group.setGroupName(dialog.getGroupName());
                                favoriteVocabGroupDao.update(group);
                                reloadListFavoriteGroups();
                                adapter.notifyDataSetChanged();
                                dialog.dismiss();

                                ToeicAlertDialog successDialog = new ToeicAlertDialog(getContext());
                                successDialog.setDialogMode(ToeicAlertDialog.MODE_SUCCESS);
                                successDialog.setMessage("Update group successfully");
                                successDialog.show();
                            }

                            @Override
                            public void onCancel() {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }
                });
                this.btnDelete.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToeicAlertDialog dialog = new ToeicAlertDialog(getContext());
                        dialog.setDialogMode(ToeicAlertDialog.MODE_QUESTION);
                        dialog.setMessage("Are you sure?");
                        dialog.setOnDialogButtonClickedListener(new ToeicAlertDialog.OnDialogButtonClickedListener() {
                            @Override
                            public void onOk() {
                                favoriteVocabGroupDao.delete(group);
                                reloadListFavoriteGroups();
                                adapter.notifyDataSetChanged();
                                dialog.dismiss();
                                ToeicAlertDialog successDialog = new ToeicAlertDialog(getContext());
                                successDialog.setDialogMode(ToeicAlertDialog.MODE_SUCCESS);
                                successDialog.setMessage("Deleted successfully");
                                successDialog.show();
                            }

                            @Override
                            public void onCancel() {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }
                });
            }
        }
    }
}
