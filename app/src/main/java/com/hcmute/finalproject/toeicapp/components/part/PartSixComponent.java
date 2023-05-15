package com.hcmute.finalproject.toeicapp.components.part;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.components.AnswerSelectionComponent;
import com.hcmute.finalproject.toeicapp.components.QuestionSentenceComponent;
import com.hcmute.finalproject.toeicapp.components.common.CommonHeaderComponent;
import com.hcmute.finalproject.toeicapp.dao.ToeicAnswerChoiceDao;
import com.hcmute.finalproject.toeicapp.dao.ToeicItemContentDao;
import com.hcmute.finalproject.toeicapp.dao.ToeicQuestionDao;
import com.hcmute.finalproject.toeicapp.dao.ToeicQuestionGroupDao;
import com.hcmute.finalproject.toeicapp.database.ToeicAppDatabase;
import com.hcmute.finalproject.toeicapp.entities.ToeicAnswerChoice;
import com.hcmute.finalproject.toeicapp.entities.ToeicItemContent;
import com.hcmute.finalproject.toeicapp.entities.ToeicQuestion;
import com.hcmute.finalproject.toeicapp.entities.ToeicQuestionGroup;
import com.hcmute.finalproject.toeicapp.testing.van.activities.VanTestRecyclerActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PartSixComponent extends ToeicPartComponentBase {
    private static final int TYPE_QUESTION_HTML = 1;
    private static final int TYPE_QUESTION_SELECTION = 2;
    private List<RenderItem> renderItems = new ArrayList<>();
    private List<ToeicItemContent> itemContents;
    private List<ToeicQuestion> questions;

    private RecyclerView recyclerView;

    private ToeicQuestionDao toeicQuestionDao;
    private ToeicAnswerChoiceDao toeicAnswerChoiceDao;
    private ToeicItemContentDao toeicItemContentDao;
    private ToeicQuestionGroupDao toeicQuestionGroupDao;
    private ListQuestionPartSixAdapter adapter;
    private boolean isExplainShowed = false;
    private List<AnswerSelectionComponent> answerSelectionComponentList = new ArrayList<>();
    public PartSixComponent(Context context) {
        this(context, null);
    }

    public PartSixComponent(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PartSixComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setPartNumber(6);
        this.initComponent(context, attrs, defStyleAttr);

    }
    private void initComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        View view = inflate(context, R.layout.component_part_six, this);
        recyclerView = findViewById(R.id.component_part_six_recyclerview);

        if (this.isInEditMode()) {
            return;
        }

        ToeicAppDatabase toeicAppDatabase = ToeicAppDatabase.getInstance(getContext());
        this.toeicQuestionDao = toeicAppDatabase.getToeicQuestionDao();
        this.toeicQuestionGroupDao = toeicAppDatabase.getToeicQuestionGroupDao();
        this.toeicAnswerChoiceDao = toeicAppDatabase.getToeicAnswerChoiceDao();
        this.toeicItemContentDao = toeicAppDatabase.getToeicItemContentDao();
        this.questions = new ArrayList<>();
        this.itemContents = new ArrayList<>();

        this.adapter = new ListQuestionPartSixAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void loadQuestionGroup(ToeicQuestionGroup toeicQuestionGroup) {
        this.questions.clear();
        this.questions.addAll(
                this.toeicQuestionDao.getToeicQuestionByQuestionGroppId(toeicQuestionGroup.getId())
        );
        this.itemContents.addAll(
                //TODO
                this.toeicItemContentDao.getItemContentByGroupId(149)
        );
        for (ToeicItemContent itemContent: itemContents) {
            RenderItem item = new RenderItem();
            item.setType(PartSixComponent.TYPE_QUESTION_HTML);
            item.setData(itemContent);
            renderItems.add(item);
        }

        for (ToeicQuestion question: questions) {
            RenderItem item = new RenderItem();
            item.setType(PartSixComponent.TYPE_QUESTION_SELECTION);
            item.setData(question);
            renderItems.add(item);
        }

        this.adapter.notifyDataSetChanged();
    }

    @Override
    public void showExplain() {
        for (AnswerSelectionComponent answerSelectionComponent : answerSelectionComponentList) {
            answerSelectionComponent.setShowExplain(true);
        }

        isExplainShowed = true;
    }

    @Override
    public Integer getNumberCorrectAnswer() {
        assert this.questions.size() >= answerSelectionComponentList.size();
        int counter = 0;
        for (AnswerSelectionComponent component : answerSelectionComponentList) {
            final ToeicAnswerChoice currentChoice = component.getCurrentChoice();
            if (currentChoice != null) {
                if (currentChoice.getLabel().equals(component.getCorrectAnswer())) {
                    counter++;
                }
            }
        }
        return counter;
    }

    @Override
    public Integer getTotalQuestions() {
        return this.questions.size();
    }

    private class ListQuestionPartSixAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        @Override
        public int getItemViewType(int position) {
            return renderItems.get(position).getType();
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == TYPE_QUESTION_HTML) {
                QuestionSentenceComponent questionSentenceComponent1 = new QuestionSentenceComponent(getContext());
                questionSentenceComponent1.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ));
                LayoutParams layoutParams = (LayoutParams) questionSentenceComponent1.getLayoutParams();
                layoutParams.setMargins(0, 0, 0, 20);
                return new QuestionContentHolder(questionSentenceComponent1);
            } else {
                AnswerSelectionComponent answerSelectionComponent = new AnswerSelectionComponent(getContext());
                answerSelectionComponent.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ));
                LayoutParams layoutParams = (LayoutParams) answerSelectionComponent.getLayoutParams();
                layoutParams.setMargins(0, 0, 0, 20);
                answerSelectionComponentList.add(answerSelectionComponent);
                addAnswerSelectionComponent(answerSelectionComponent);
                return new ListQuestionPartSixAdapter.ListQuestionPartSixItemHolder(answerSelectionComponent);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (renderItems.get(position).getType() == TYPE_QUESTION_HTML) {
                QuestionContentHolder contentHolder = new QuestionContentHolder(holder.itemView);
                contentHolder.setItemContent((ToeicItemContent) renderItems.get(position).getData(), position);
            } else {
                ListQuestionPartSixItemHolder itemHolder = new ListQuestionPartSixItemHolder(holder.itemView);
                itemHolder.setData((ToeicQuestion) renderItems.get(position).getData(), position);
            }
        }

        @Override
        public int getItemCount() {
            return renderItems.size();
        }

        private class QuestionContentHolder extends RecyclerView.ViewHolder {

            public QuestionContentHolder(@NonNull View itemView) {
                super(itemView);
            }

            public void setItemContent(ToeicItemContent itemContent, int position) {
                //TODO
                QuestionSentenceComponent questionSentenceComponent1 = (QuestionSentenceComponent)this.itemView;
//                if (itemContent.getContent() != null) {
//                    questionSentenceComponent1.setQuestionDescription(itemContent.getContent());
//                }
//                questionSentenceComponent1.setQuestionDescription("<div class=\"context-content context-transcript \">\n" +
//                        " <p><a data-toggle=\"collapse\" href=\"#transcript-99e3fdc8-64ad-4522-b408-166f41abf32f\" aria-expanded=\"false\"> Hiện Transcript <span class=\"fas fa-caret-down ml-1\"></span> </a></p>\n" +
//                        " <div class=\"collapse\" id=\"transcript-99e3fdc8-64ad-4522-b408-166f41abf32f\">\n" +
//                        "  <div>\n" +
//                        "   <p>Hi, Charlie. The driver who usually delivers our merchandise to the J.M. Cuisine store is out today. Do you think you could make his four o'clock delivery for him this afternoon?</p>\n" +
//                        "   <p>Alright, but I've never made any deliveries to that store before, so I'll need some directions. How do I get there from our warehouse?</p>\n" +
//                        "   <p>I suggest taking Route Five and getting off at the Sixth Street exit. Once you're there, don't forget to have the store manager sign the delivery confirmation form.</p>\n" +
//                        "  </div>\n" +
//                        "  <div>\n" +
//                        "   <p>Xin chào, Charlie. Người lái xe thường giao hàng của chúng tôi đến cửa hàng Ẩm thực J.M. đang ra ngoài hôm nay. Bạn có nghĩ rằng bạn có thể giao hàng lúc bốn giờ thay cho anh ấy chiều nay không?</p>\n" +
//                        "   <p>Được rồi, nhưng trước đây tôi chưa bao giờ thực hiện bất kỳ việc giao hàng nào đến cửa hàng đó, vì vậy tôi sẽ cần được chỉ đường. Làm thế nào để tôi đến đó từ kho của chúng ta?</p>\n" +
//                        "   <p>Tôi đề nghị bạn đi Tuyến 5 và xuống ở lối ra Đường thứ Sáu. Khi bạn ở đó, đừng quên để người quản lý cửa hàng ký tên vào mẫu xác nhận giao hàng nhé.</p>\n" +
//                        "  </div>\n" +
//                        " </div>\n" +
//                        "</div>");
                questionSentenceComponent1.setQuestionDescription("<a href=\"https://google.com\">Click here</a>");

            }
        }

        private class ListQuestionPartSixItemHolder extends RecyclerView.ViewHolder {

            public ListQuestionPartSixItemHolder(@NonNull View itemView) {
                super(itemView);
            }

            public void setData(ToeicQuestion question, int position) {
                final List<ToeicAnswerChoice> choices = toeicAnswerChoiceDao.getByQuestionId(question.getId());
                AnswerSelectionComponent component = (AnswerSelectionComponent)this.itemView;
                component.setToeicAnswerChoices(choices);
                assert question.getCorrectAnswer() != null && question.getCorrectAnswer().length() == 1;
                component.setCorrectAnswer(question.getCorrectAnswer());
                component.setToeicQuestion(question);
                component.setQuestionTitle(question.getQuestionNumber() + ". " + question.getContent());

                component.setShowExplain(isExplainShowed);
            }
        }
    }

    private class RenderItem {
        private int type;
        private Object data;

        public RenderItem() { }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }

}
