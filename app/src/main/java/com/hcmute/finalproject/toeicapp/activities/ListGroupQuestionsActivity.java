package com.hcmute.finalproject.toeicapp.activities;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;
import java.util.List;

public class ListGroupQuestionsActivity extends AppCompatActivity {
    private TextView txtGroupQuestionsName;
    private BackButtonRoundedComponent btnBack;
    private ListView listGroupQuestionsName;
    private OnClickBackButton onClickBackButton;
    private List<GroupQuestionsModel> listGroupQuestions;
    private ListGroupQuestionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_group_questions);

        initView();
    }

    private void initView() {
        txtGroupQuestionsName = (TextView) findViewById(R.id.activity_list_group_questions_group_name);
        btnBack = (BackButtonRoundedComponent) findViewById(R.id.activity_list_group_questions_btn_back);
        listGroupQuestionsName = (ListView) findViewById(R.id.activity_list_group_questions_list_questions);

        this.listGroupQuestions = new ArrayList<>();
        listGroupQuestions = getListGroupQuestions();

        this.adapter = new ListGroupQuestionAdapter();
        this.listGroupQuestionsName.setAdapter(adapter);
    }

    private List<GroupQuestionsModel> getListGroupQuestions() {
        List<GroupQuestionsModel> listGroups = new ArrayList<>();

        listGroups.add(new GroupQuestionsModel(5, "Photograph 01", 6));
        listGroups.add(new GroupQuestionsModel(5, "Photograph 02", 6));
        listGroups.add(new GroupQuestionsModel(5, "Photograph 03", 6));
        listGroups.add(new GroupQuestionsModel(5, "Photograph 03", 6));
        listGroups.add(new GroupQuestionsModel(5, "Photograph 03", 6));
        listGroups.add(new GroupQuestionsModel(5, "Photograph 03", 6));
        listGroups.add(new GroupQuestionsModel(5, "Photograph 03", 6));
        listGroups.add(new GroupQuestionsModel(5, "Photograph 03", 6));
        listGroups.add(new GroupQuestionsModel(5, "Photograph 03", 6));
        listGroups.add(new GroupQuestionsModel(5, "Photograph 03", 6));
        listGroups.add(new GroupQuestionsModel(5, "Photograph 03", 6));
        listGroups.add(new GroupQuestionsModel(5, "Photograph 03", 6));
        listGroups.add(new GroupQuestionsModel(5, "Photograph 03", 6));
        listGroups.add(new GroupQuestionsModel(5, "Photograph 03", 6));
        listGroups.add(new GroupQuestionsModel(5, "Photograph 03", 6));
        listGroups.add(new GroupQuestionsModel(5, "Photograph 03", 6));
        listGroups.add(new GroupQuestionsModel(5, "Photograph 03", 6));
        listGroups.add(new GroupQuestionsModel(5, "Photograph 03", 6));

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

            final GroupQuestionsModel groupQuestionsModel = listGroupQuestions.get(i);
            final TextView txtGroupName = view.findViewById(R.id.activity_list_group_questions_item_text_name_group_question);
            final TextView txtNumberOfQuestions = view.findViewById(R.id.activity_list_group_questions_item_text_number_of_question);

            txtGroupName.setText(groupQuestionsModel.getGroupQuestionName());
            txtNumberOfQuestions.setText(groupQuestionsModel.getNumberOfQuestions() + " questions");

            return view;
        }
    }

    private class GroupQuestionsModel {
        private int partID;
        private String groupQuestionName;
        private int numberOfQuestions;

        public GroupQuestionsModel(int partID, String groupQuestionName, int numberOfQuestions) {
            this.partID = partID;
            this.groupQuestionName = groupQuestionName;
            this.numberOfQuestions = numberOfQuestions;
        }

        public int getPartID() {
            return partID;
        }

        public void setPartID(int partID) {
            this.partID = partID;
        }

        public String getGroupQuestionName() {
            return groupQuestionName;
        }

        public void setGroupQuestionName(String groupQuestionName) {
            this.groupQuestionName = groupQuestionName;
        }

        public int getNumberOfQuestions() {
            return numberOfQuestions;
        }

        public void setNumberOfQuestions(int numberOfQuestions) {
            this.numberOfQuestions = numberOfQuestions;
        }
    }
}