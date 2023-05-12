package com.hcmute.finalproject.toeicapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.components.common.BackButtonRoundedComponent;
import com.hcmute.finalproject.toeicapp.model.toeic.GroupQuestionModel;
import com.hcmute.finalproject.toeicapp.model.toeic.ToeicQuestionGroup;
import com.hcmute.finalproject.toeicapp.services.mocktoeic.MockToeicTestDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListGroupQuestionsActivity extends GradientActivity {
    private TextView txtPartName;
    private BackButtonRoundedComponent btnBack;
    private ListView listGroupQuestionsName;
    private OnClickBackButton onClickBackButton;
    private List<GroupQuestionModel> listGroupQuestions;
    private ListGroupQuestionAdapter adapter;
    private Integer partNumber;
    private MockToeicTestDatabase mockToeicTestDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_group_questions);
        this.mockToeicTestDatabase = new MockToeicTestDatabase(this);
        initView();
    }


    private void initView() {
        txtPartName = (TextView) findViewById(R.id.activity_list_group_questions_group_name);
        btnBack = (BackButtonRoundedComponent) findViewById(R.id.activity_list_group_questions_btn_back);
        listGroupQuestionsName = (ListView) findViewById(R.id.activity_list_group_questions_list_questions);

        Bundle bundle = getIntent().getExtras();
        String partNameExtra = bundle.getString("partName");
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

        loadToeicPartModel(this.getSampleListGroupQuestions());
    }

    private void loadToeicPartModel(final List<GroupQuestionModel> groupQuestionModels) {
        this.listGroupQuestions.clear();
        this.listGroupQuestions.addAll(groupQuestionModels);
        this.adapter.notifyDataSetChanged();
    }

    private void fetchListGroupQuestions() {
        List<GroupQuestionModel> toeicPart = this.mockToeicTestDatabase.getToeicPartDataFromDisk(this.partNumber, null);

        if (toeicPart != null) {
            this.loadToeicPartModel(toeicPart);
            return;
        }

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Đang tải");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();

        this.mockToeicTestDatabase.refreshToeicDataFromInternet(
                this.partNumber,
                new MockToeicTestDatabase.OnMockToeicStateChanged() {
                    @Override
                    public void onSuccess(String message) {
                        Toast.makeText(ListGroupQuestionsActivity.this, message, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                        List<GroupQuestionModel> questionModelList = mockToeicTestDatabase.getToeicPartDataFromDisk(partNumber, null);
                        assert questionModelList != null;
                        loadToeicPartModel(questionModelList);

                        progressDialog.setProgress(100);
                    }

                    @Override
                    public void onProgress(int progress) {
                        progressDialog.setProgress(progress);
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(ListGroupQuestionsActivity.this, error, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
        );
    }

    private List<GroupQuestionModel> getSampleListGroupQuestions() {
        List<GroupQuestionModel> listGroups = new ArrayList<>();

        listGroups.add(new GroupQuestionModel(5, "Photograph 01", 6));
        listGroups.add(new GroupQuestionModel(5, "Photograph 02", 6));
        listGroups.add(new GroupQuestionModel(5, "Photograph 03", 6));
        listGroups.add(new GroupQuestionModel(5, "Photograph 03", 6));
        listGroups.add(new GroupQuestionModel(5, "Photograph 03", 6));
        listGroups.add(new GroupQuestionModel(5, "Photograph 03", 6));
        listGroups.add(new GroupQuestionModel(5, "Photograph 03", 6));
        listGroups.add(new GroupQuestionModel(5, "Photograph 03", 6));
        listGroups.add(new GroupQuestionModel(5, "Photograph 03", 6));
        listGroups.add(new GroupQuestionModel(5, "Photograph 03", 6));
        listGroups.add(new GroupQuestionModel(5, "Photograph 03", 6));
        listGroups.add(new GroupQuestionModel(5, "Photograph 03", 6));
        listGroups.add(new GroupQuestionModel(5, "Photograph 03", 6));
        listGroups.add(new GroupQuestionModel(5, "Photograph 03", 6));
        listGroups.add(new GroupQuestionModel(5, "Photograph 03", 6));
        listGroups.add(new GroupQuestionModel(5, "Photograph 03", 6));
        listGroups.add(new GroupQuestionModel(5, "Photograph 03", 6));
        listGroups.add(new GroupQuestionModel(5, "Photograph 03", 6));

        return listGroups;
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

            final GroupQuestionModel groupQuestionModel = listGroupQuestions.get(i);
            final TextView txtGroupName = view.findViewById(R.id.activity_list_group_questions_item_text_name_group_question);
            final TextView txtNumberOfQuestions = view.findViewById(R.id.activity_list_group_questions_item_text_number_of_question);

            txtGroupName.setText(groupQuestionModel.getName());
            txtNumberOfQuestions.setText(groupQuestionModel.getNumOfQuestions() + " questions");

            view.setOnClickListener(new View.OnClickListener() {
                private void loadListToeicQuestionGroups(Integer partId, List<ToeicQuestionGroup> toeicQuestionGroups) {
                    for (ToeicQuestionGroup group : toeicQuestionGroups) {
                        group.setType(partNumber.toString());
                    }
                    ArrayList<ToeicQuestionGroup> toeicQuestionGroupsFixed = new ArrayList<>(Collections.unmodifiableList(toeicQuestionGroups));
                    Intent intent = new Intent(ListGroupQuestionsActivity.this, ToeicTestListQuestionsActivity.class);
                    intent.putExtra("part-id", partId);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("toeic-group-questions", toeicQuestionGroupsFixed);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

                @Override
                public void onClick(View v) {
                    final Integer partId = groupQuestionModel.getPartId();

                    if (mockToeicTestDatabase.checkToeicPartQuestionsDataDownloaded(partId)) {
                        try {
                            final List<ToeicQuestionGroup> toeicQuestionGroups = mockToeicTestDatabase.loadToeicPartFromDisk(partId);
                            loadListToeicQuestionGroups(partId, toeicQuestionGroups);
                        } catch (IOException e) {
                            Toast.makeText(ListGroupQuestionsActivity.this, "Load file lỗi: " + e, Toast.LENGTH_SHORT).show();
                        }
                        return;
                    }

                    ProgressDialog progressDialog = new ProgressDialog(ListGroupQuestionsActivity.this);
                    progressDialog.setTitle("Đang tải");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progressDialog.setMax(100);
                    progressDialog.show();

                    mockToeicTestDatabase.refreshToeicPartQuestionsDataFromInternet(partId, new MockToeicTestDatabase.OnMockToeicStateChanged() {
                        @Override
                        public void onSuccess(String message) {
                            try {
                                final List<ToeicQuestionGroup> toeicQuestionGroups = mockToeicTestDatabase.loadToeicPartFromDisk(partId);
                                loadListToeicQuestionGroups(partId, new ArrayList<>(toeicQuestionGroups));
                                Toast.makeText(ListGroupQuestionsActivity.this, message, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            } catch (IOException e) {
                                Toast.makeText(ListGroupQuestionsActivity.this, "Load file lỗi: " + e, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onProgress(int progress) {
                            progressDialog.setProgress(progress);
                        }

                        @Override
                        public void onError(String error) {
                            Toast.makeText(ListGroupQuestionsActivity.this, error, Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
                }
            });

            return view;
        }
    }
}