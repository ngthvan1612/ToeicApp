package com.hcmute.finalproject.toeicapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.source.BundledExtractorsAdapter;
import com.google.gson.Gson;
import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.components.common.BackButtonRoundedComponent;
import com.hcmute.finalproject.toeicapp.components.part.ToeicGroupItemViewModel;
import com.hcmute.finalproject.toeicapp.dao.ToeicPartDao;
import com.hcmute.finalproject.toeicapp.dao.ToeicQuestionGroupDao;
import com.hcmute.finalproject.toeicapp.database.ToeicAppDatabase;
import com.hcmute.finalproject.toeicapp.entities.ToeicPart;
import com.hcmute.finalproject.toeicapp.entities.ToeicQuestionGroup;
import com.hcmute.finalproject.toeicapp.model.toeic.ToeicPartItemView;
import com.hcmute.finalproject.toeicapp.model.toeic.TestToeicQuestionGroup;
import com.hcmute.finalproject.toeicapp.services.backend.tests.ToeicTestBackendService;
import com.hcmute.finalproject.toeicapp.services.dialog.DialogSyncService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListGroupQuestionsActivity extends GradientActivity {
    private TextView txtPartName;
    private BackButtonRoundedComponent btnBack;
    private ListView listGroupQuestionsName;
    private OnClickBackButton onClickBackButton;
    private List<ToeicPartItemView> listGroupQuestions;
    private ListGroupQuestionAdapter adapter;
    private Integer partNumber;
    private String partName;
    private ToeicAppDatabase toeicAppDatabase;
    private ToeicTestBackendService toeicTestBackendService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_group_questions);

        this.toeicAppDatabase = ToeicAppDatabase.getInstance(ListGroupQuestionsActivity.this);
        this.toeicTestBackendService = new ToeicTestBackendService(this);

        initView();
    }


    private void initView() {
        txtPartName = (TextView) findViewById(R.id.activity_list_group_questions_group_name);
        btnBack = (BackButtonRoundedComponent) findViewById(R.id.activity_list_group_questions_btn_back);
        listGroupQuestionsName = (ListView) findViewById(R.id.activity_list_group_questions_list_questions);

        Bundle bundle = getIntent().getExtras();
        String partNameExtra = bundle.getString("partName");
        this.partName = partNameExtra;
        this.partNumber = bundle.getInt("partId");
        txtPartName.setText(partNameExtra);

        this.listGroupQuestions = new ArrayList<>();
        this.adapter = new ListGroupQuestionAdapter();
        this.listGroupQuestionsName.setAdapter(adapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        this.loadLocalDatabase();
    }

    private void loadLocalDatabase() {
        final ToeicPartDao partDao = this.toeicAppDatabase.getToeicPartDao();
        final List<ToeicPart> parts = partDao.listPartsByPartNumber(this.partNumber);

        final List<ToeicPartItemView> views = new ArrayList<>();
        int counter = 1;
        for (ToeicPart toeicPart : parts) {
            ToeicPartItemView itemView = new ToeicPartItemView();

            itemView.setName(this.partName + " " + counter);
            itemView.setPartNumber(toeicPart.getPartNumber());
            itemView.setServerId(toeicPart.getServerId());
            itemView.setId(toeicPart.getId());
            itemView.setDownloaded(toeicPart.getDownloaded());

            counter++;

            views.add(itemView);
        }

        this.loadToeicPartModel(views);
    }

    private void loadToeicPartModel(final List<ToeicPartItemView> toeicPartItemViews) {
        this.listGroupQuestions.clear();
        this.listGroupQuestions.addAll(toeicPartItemViews);
        this.adapter.notifyDataSetChanged();
    }

    public interface OnClickBackButton {
        void onClick();
    }

    private class ListGroupQuestionAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return listGroupQuestions.size();
        }

        @Override
        public Object getItem(int i) {
            return listGroupQuestions.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            final LayoutInflater inflater = LayoutInflater.from(ListGroupQuestionsActivity.this);
            view = inflater.inflate(R.layout.activity_list_group_questions_item, viewGroup,false);

            final ToeicPartItemView toeicPartItemView = listGroupQuestions.get(i);
            final TextView txtGroupName = view.findViewById(R.id.activity_list_group_questions_item_text_name_group_question);
            final TextView txtNumberOfQuestions = view.findViewById(R.id.activity_list_group_questions_item_text_number_of_question);
            final ImageView btnDownload = view.findViewById(R.id.activity_list_group_questions_btn_download);

            if (toeicPartItemView.isDownloaded()) {
                btnDownload.setVisibility(View.GONE);

                View.OnClickListener onClicked = v -> {
                    Intent intent = new Intent(ListGroupQuestionsActivity.this, ToeicTestListQuestionsActivity.class);
                    List<ToeicGroupItemViewModel> groupViews = new ArrayList<>();
                    final ToeicQuestionGroupDao groupDao = toeicAppDatabase.getToeicQuestionGroupDao();
                    for (ToeicQuestionGroup group : groupDao.getGroupsByPartId(toeicPartItemView.getId())) {
                        ToeicGroupItemViewModel itemView = new ToeicGroupItemViewModel();
                        itemView.setGroup(group);
                        itemView.setPartNumber(partNumber);
                        groupViews.add(itemView);
                    }
                    Gson gson = new Gson();
                    Bundle bundle = getIntent().getExtras();
                    Integer partId = bundle.getInt("partId");
                    intent.putExtra("partId", partId);
                    intent.putExtra("question-data", gson.toJson(groupViews));
                    startActivity(intent);
                };

                view.setOnClickListener(onClicked);
                txtGroupName.setOnClickListener(onClicked);
                txtNumberOfQuestions.setOnClickListener(onClicked);
            }
            else {
                btnDownload.setOnClickListener(v -> {
                    toeicTestBackendService.downloadPartStorageData(
                            toeicPartItemView.getServerId(),
                            new ToeicTestBackendService.OnBackupToeicListener() {
                                @Override
                                public void prepare() {
                                    DialogSyncService.showDialog(ListGroupQuestionsActivity.this);
                                }

                                @Override
                                public void onSuccess() {
                                    final ToeicPartDao toeicPartDao = toeicAppDatabase.getToeicPartDao();
                                    ToeicPart toeicPart = toeicPartDao.getOne(toeicPartItemView.getId());
                                    toeicPart.setDownloaded(true);
                                    toeicPartDao.update(toeicPart);

                                    loadLocalDatabase();

                                    DialogSyncService.dismissDialog();
                                }

                                @Override
                                public void onUpdateProgress(int progress) {
                                    // TODO: update progress
                                }

                                @Override
                                public void onException(Exception exception) {
                                    Toast.makeText(ListGroupQuestionsActivity.this, "Loi r nha " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    Log.d("DOWNLOAD_ERR", exception.getMessage());

                                    DialogSyncService.dismissDialog();
                                }
                            }
                    );
                });
            }

            txtGroupName.setText(toeicPartItemView.getName());
            txtNumberOfQuestions.setText(toeicPartItemView.getNumOfQuestions() + " questions");

            return view;
        }
    }
}