package com.hcmute.finalproject.toeicapp.activities;

import androidx.appcompat.app.AppCompatActivity;

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

import com.google.gson.Gson;
import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.components.common.BackButtonRoundedComponent;
import com.hcmute.finalproject.toeicapp.components.homepage.HomePageListPracticeComponent;
import com.hcmute.finalproject.toeicapp.components.part.ToeicGroupItemViewModel;
import com.hcmute.finalproject.toeicapp.dao.ToeicFullTestDao;
import com.hcmute.finalproject.toeicapp.dao.ToeicPartDao;
import com.hcmute.finalproject.toeicapp.dao.ToeicQuestionGroupDao;
import com.hcmute.finalproject.toeicapp.database.ToeicAppDatabase;
import com.hcmute.finalproject.toeicapp.entities.ToeicFullTest;
import com.hcmute.finalproject.toeicapp.entities.ToeicPart;
import com.hcmute.finalproject.toeicapp.entities.ToeicQuestionGroup;
import com.hcmute.finalproject.toeicapp.model.toeic.TestToeicPart;
import com.hcmute.finalproject.toeicapp.model.toeic.ToeicFullTestItemView;
import com.hcmute.finalproject.toeicapp.model.toeic.ToeicPartItemView;
import com.hcmute.finalproject.toeicapp.services.backend.tests.ToeicTestBackendService;
import com.hcmute.finalproject.toeicapp.services.dialog.DialogSyncService;

import java.util.ArrayList;
import java.util.List;

public class ListTestActivity extends AppCompatActivity {
    private TextView txtTestName;
    private BackButtonRoundedComponent btnBack;
    private ListView listViewTestName;
    private List<ToeicFullTestItemView> toeicFullTestItemViews;
    private List<ToeicPart> toeicParts;
    private Integer partNumber;
    private String testName;
    private ToeicAppDatabase toeicAppDatabase;
    private ToeicTestBackendService toeicTestBackendService;
    private ListTestAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_test);

        this.toeicAppDatabase = ToeicAppDatabase.getInstance(ListTestActivity.this);
        this.toeicTestBackendService = new ToeicTestBackendService(this);

        initView();
    }

    private void initView() {
        this.txtTestName = findViewById(R.id.activity_list_group_test_name);
        this.btnBack = findViewById(R.id.activity_list_test_btn_back);
        this.listViewTestName = findViewById(R.id.activity_list_test_list_view);

        Bundle bundle = getIntent().getExtras();
        String partNameExtra = bundle.getString("partName");
        this.testName = partNameExtra;
        this.partNumber = bundle.getInt("partId");
        txtTestName.setText(partNameExtra);

        toeicFullTestItemViews = new ArrayList<>();
        this.adapter = new ListTestAdapter();
        this.listViewTestName.setAdapter(adapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        this.loadLocalDatabase();
    }

    private void loadLocalDatabase() {
        final ToeicFullTestDao fullTestDAO = this.toeicAppDatabase.getToeicFullTestDao();
        final List<ToeicFullTest> fullTests = fullTestDAO.listFullTest();

        final List<ToeicFullTestItemView> views = new ArrayList<>();
        for (ToeicFullTest toeicFullTest : fullTests) {
            ToeicFullTestItemView itemView = new ToeicFullTestItemView();

            itemView.setFullName(toeicFullTest.getFullName());
            itemView.setServerId(toeicFullTest.getServerId());
            itemView.setId(toeicFullTest.getId());
            //TODO
            itemView.setDownloaded(true);

            views.add(itemView);
        }

        this.loadToeicTestModel(views);
    }

    private void loadToeicTestModel(final List<ToeicFullTestItemView> toeicFullTestItemViews) {
        this.toeicFullTestItemViews.clear();
        this.toeicFullTestItemViews.addAll(toeicFullTestItemViews);
        this.adapter.notifyDataSetChanged();
    }

    private class ListTestAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return toeicFullTestItemViews.size();
        }

        @Override
        public Object getItem(int i) {
            return toeicFullTestItemViews.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            final LayoutInflater inflater = LayoutInflater.from(ListTestActivity.this);
            view = inflater.inflate(R.layout.activity_list_group_questions_item, viewGroup,false);

            final ToeicFullTestItemView toeicFullTestItemView = toeicFullTestItemViews.get(i);
            final TextView txtGroupName = view.findViewById(R.id.activity_list_group_questions_item_text_name_group_question);
            final TextView txtNumberOfQuestions = view.findViewById(R.id.activity_list_group_questions_item_text_number_of_question);
            final ImageView btnDownload = view.findViewById(R.id.activity_list_group_questions_btn_download);

            ToeicPartDao toeicPartDao = toeicAppDatabase.getToeicPartDao();
            ToeicQuestionGroupDao toeicQuestionGroupDao = toeicAppDatabase.getToeicQuestionGroupDao();
            if (partNumber == HomePageListPracticeComponent.FULL_TEST) {
               toeicParts = toeicPartDao.getToeicPartByToeicTestId(toeicFullTestItemView.getId());
            } else if (partNumber == HomePageListPracticeComponent.LISTENING_TEST) {
                toeicParts = toeicPartDao.getToeicPartListeningByToeicTestId(toeicFullTestItemView.getId());
            } else if (partNumber == HomePageListPracticeComponent.READING_TEST) {
                toeicParts = toeicPartDao.getToeicPartReadingByToeicTestId(toeicFullTestItemView.getId());
            }

            Boolean isFullTestDownloaded = true;
            for (ToeicPart toeicPart : toeicParts) {
                if (toeicPart.getDownloaded() == false) {
                    isFullTestDownloaded = false;
                    break;
                }
            }

            if (isFullTestDownloaded == false) {
                btnDownload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(ListTestActivity.this, "click btn", Toast.LENGTH_SHORT).show();
                        for (ToeicPart toeicPart : toeicParts) {
                            if (toeicPart.getDownloaded() == false) {
                                toeicTestBackendService.downloadPartStorageData(
                                        toeicPart.getServerId(),
                                        new ToeicTestBackendService.OnBackupToeicListener() {
                                            @Override
                                            public void prepare() {
                                                DialogSyncService.showDialog(ListTestActivity.this);
                                            }

                                            @Override
                                            public void onSuccess() {
                                                final ToeicPartDao toeicPartDao = toeicAppDatabase.getToeicPartDao();
                                                ToeicPart toeicPartTemp = toeicPartDao.getOne(toeicPart.getId());
                                                toeicPartTemp.setDownloaded(true);
                                                toeicPartDao.update(toeicPartTemp);

                                                loadLocalDatabase();

                                                DialogSyncService.dismissDialog();
                                            }

                                            @Override
                                            public void onUpdateProgress(int progress) {
                                                // TODO: update progress
                                            }

                                            @Override
                                            public void onException(Exception exception) {
                                                Toast.makeText(ListTestActivity.this, "Loi r nha " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                                Log.d("DOWNLOAD_ERR", exception.getMessage());

                                                DialogSyncService.dismissDialog();
                                            }
                                        }
                                );
                            }
                        }
                    }
                });

            } else {
                btnDownload.setVisibility(View.GONE);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        List<ToeicGroupItemViewModel> viewModels = new ArrayList<>();

                        for (ToeicPart toeicPart: toeicParts) {
                            for (ToeicQuestionGroup group : toeicQuestionGroupDao.getGroupsByPartId(toeicPart.getId())) {
                                ToeicGroupItemViewModel viewModel = new ToeicGroupItemViewModel();
                                viewModel.setPartNumber(toeicPart.getPartNumber());
                                viewModel.setGroup(group);
                                viewModels.add(viewModel);
                            }
                        }

                        final Intent intent = new Intent(ListTestActivity.this, ToeicTestListQuestionsActivity.class);
                        final Gson gson = new Gson();
                        intent.putExtra("question-data", gson.toJson(viewModels));
                        intent.putExtra("title-name", toeicFullTestItemView.getFullName());

                        startActivity(intent);
                    }
                });
            }


            txtGroupName.setText(toeicFullTestItemView.getFullName());
//            txtNumberOfQuestions.setText(toeicFullTestItemView.get() + " questions");

            return view;
        }

    }
}