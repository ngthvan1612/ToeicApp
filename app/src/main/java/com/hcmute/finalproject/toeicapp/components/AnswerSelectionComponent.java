package com.hcmute.finalproject.toeicapp.components;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hcmute.finalproject.toeicapp.R;
import com.hcmute.finalproject.toeicapp.entities.ToeicAnswerChoice;
import com.hcmute.finalproject.toeicapp.entities.ToeicQuestion;
import com.hcmute.finalproject.toeicapp.model.toeic.TestToeicAnswerChoice;

import java.util.ArrayList;
import java.util.List;

public class AnswerSelectionComponent extends LinearLayout {
    private TextView txtTitle;
    private RecyclerView recyclerViewSelection;
    private AnswerSelectionRecyclerViewAdapter adapter;
    private int lastSelectedPosition = -1;
    private ToeicQuestion toeicQuestion;
    private List<ToeicAnswerChoice> toeicAnswerChoices;
    private boolean showExplain;
    private boolean canSelect = true;
    private String correctAnswer;

    private class AnswerSelectionItem {
        private String label;
        private String choice;
        private String explain;
        private boolean showExplain;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getChoice() {
            return choice;
        }

        public void setChoice(String choice) {
            this.choice = choice;
        }

        public String getExplain() {
            return explain;
        }

        public void setExplain(String explain) {
            this.explain = explain;
        }

        public boolean isShowExplain() {
            return showExplain;
        }

        public void setShowExplain(boolean showExplain) {
            this.showExplain = showExplain;
        }
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
        this.setShowExplain(this.isShowExplain());
        this.adapter.notifyDataSetChanged();
    }

    private String getQuestionTitle() {
        return this.txtTitle.getText().toString();
    }

    public void setQuestionTitle(String title) {
        this.txtTitle.setText(title);
    }

    public List<ToeicAnswerChoice> getToeicAnswerChoices() {
        return toeicAnswerChoices;
    }

    public void setToeicAnswerChoices(List<ToeicAnswerChoice> toeicAnswerChoices) {
        this.toeicAnswerChoices = toeicAnswerChoices;
        listItems.clear();

        for (ToeicAnswerChoice choice : toeicAnswerChoices) {
            AnswerSelectionItem item = new AnswerSelectionItem();

            item.setLabel(choice.getLabel());
            item.setChoice(choice.getContent());
            item.setExplain(choice.getExplain());

            listItems.add(item);
        }

        this.recyclerViewSelection.post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }

    public ToeicQuestion getToeicQuestion() {
        return toeicQuestion;
    }

    public void setToeicQuestion(ToeicQuestion toeicQuestion) {
        this.toeicQuestion = toeicQuestion;
    }

    public boolean isShowExplain() {
        return showExplain;
    }

    public void setShowExplain(boolean showExplain) {
        this.showExplain = showExplain;

        if (showExplain)
            canSelect = false;

        for (AnswerSelectionItem item : listItems) {
            item.setShowExplain(showExplain);
        }

        this.recyclerViewSelection.post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }

    private List<AnswerSelectionItem> listItems;

    public AnswerSelectionComponent(Context context) {
        this(context, null);
    }

    public AnswerSelectionComponent(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnswerSelectionComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initComponent(context, attrs, defStyleAttr);
    }

    private void initComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        // Inflate  layout
        View view = inflate(context, R.layout.component_answer_selection, this);

        this.txtTitle = view.findViewById(R.id.component_answer_selection_title);
        this.recyclerViewSelection = view.findViewById(R.id.component_answer_selection_recycler_view);

        if (this.isInEditMode()) {
            return;
        }

        // Binding + Event
        this.listItems = new ArrayList<>();

        this.adapter = new AnswerSelectionRecyclerViewAdapter();
        recyclerViewSelection.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.recyclerViewSelection.setAdapter(this.adapter);
    }

    private void handleAnswerRadioButtonIsChecked(int position) {
        if (lastSelectedPosition == position)
            return;

        lastSelectedPosition = position;
        this.recyclerViewSelection.post(new Runnable() {
            @Override
            public void run() {
                setShowExplain(true);
                adapter.notifyDataSetChanged();
            }
        });
    }

    public ToeicAnswerChoice getCurrentChoice() {
        if (lastSelectedPosition < 0)
            return null;

        return toeicAnswerChoices.get(lastSelectedPosition);
    }

    private class AnswerSelectionRecyclerViewAdapter extends RecyclerView.Adapter<AnswerSelectionRecyclerViewAdapter.AnswerSelectionItemViewHolder> {

        @NonNull
        @Override
        public AnswerSelectionItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            final LayoutInflater layoutInflater = LayoutInflater.from(getContext());

            final View view = layoutInflater.inflate(
                    R.layout.component_answer_selection_item,
                    parent,
                    false
            );

            return new AnswerSelectionItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AnswerSelectionItemViewHolder holder, int position) {
            holder.setData(listItems.get(position), position);
        }

        @Override
        public int getItemCount() {
            return listItems.size();
        }

        private class AnswerSelectionItemViewHolder extends RecyclerView.ViewHolder {
            private final RadioButton radioButton;
            private final TextView txtForRadioButton;
            private final RelativeLayout explainLayout;
            private final TextView txtExplain;

            public AnswerSelectionItemViewHolder(@NonNull View itemView) {
                super(itemView);

                this.txtForRadioButton = itemView.findViewById(R.id.component_answer_selection_item_txt_radio_button);
                this.radioButton = itemView.findViewById(R.id.component_answer_selection_item_radio_button);
                this.explainLayout = itemView.findViewById(R.id.component_answer_selection_item_explain_layout);
                this.txtExplain = itemView.findViewById(R.id.component_answer_selection_item_explain_text);
            }

            private void setChildListener(View parent, View.OnClickListener listener) {
                parent.setOnClickListener(listener);
                if (!(parent instanceof ViewGroup)) {
                    return;
                }

                ViewGroup parentGroup = (ViewGroup) parent;
                for (int i = 0; i < parentGroup.getChildCount(); i++) {
                    setChildListener(parentGroup.getChildAt(i), listener);
                }
            }

            private void initOnClick() {
                if (canSelect) {
                    this.setChildListener(this.itemView, new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!canSelect)
                                return;

                            if (!(v instanceof RadioButton))
                                radioButton.toggle();
                            if (radioButton.isChecked()) {
                                handleAnswerRadioButtonIsChecked(getAdapterPosition());
                            }
                            canSelect = false;
                            setChildListener(itemView, null);
                            radioButton.setEnabled(false);
                        }
                    });
                }
                else {
                    radioButton.setEnabled(false);
                }
            }

            private void setData(AnswerSelectionItem answerSelectionItem, int position) {
                this.radioButton.setChecked(position == lastSelectedPosition);
                this.txtForRadioButton.setText(answerSelectionItem.getLabel() + ". " + answerSelectionItem.getChoice());
                this.txtExplain.setTextColor(getResources().getColor(android.R.color.black));
                this.txtForRadioButton.setTextColor(getResources().getColor(android.R.color.black));

                if (answerSelectionItem.isShowExplain()) {
                    this.explainLayout.setVisibility(View.VISIBLE);
                    this.txtExplain.setText(answerSelectionItem.getExplain());
                    if (getCurrentChoice() != null) {
                        if (getCurrentChoice().getLabel().equals(answerSelectionItem.label)) {
                            this.txtExplain.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                            this.txtForRadioButton.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                        }
                    }

                    if (answerSelectionItem.label.equals(correctAnswer)) {
                        this.txtExplain.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                        this.txtForRadioButton.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                    }
                } else {
                    this.explainLayout.setVisibility(View.GONE);
                }
                this.initOnClick();
            }
        }
    }
}
