package com.hcmute.finalproject.toeicapp.components.part;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.hcmute.finalproject.toeicapp.activities.ToeicTestListQuestionsActivity;

public class ToeicPartComponentFactory {
    public static ToeicPartComponentBase createInstance(
            final @NonNull ToeicGroupItemViewModel viewModel,
            final @NonNull Activity activity
    ) {
        ToeicPartComponentBase component = null;
        if (viewModel.getPartNumber() == 1) {
            component = new PartOnePhotographsComponent(activity);
        }
        else if (viewModel.getPartNumber() == 2) {
            component = new PartTwoComponent(activity);
        }

        // Khong co gi het nua a

        component.loadQuestionGroup(viewModel.getGroup());
        return component;
    }
}
