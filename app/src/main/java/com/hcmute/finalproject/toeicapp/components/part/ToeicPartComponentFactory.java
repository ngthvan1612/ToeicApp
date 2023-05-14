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
        else if (viewModel.getPartNumber() == 3) {
            component = new PartThreeComponent(activity);
        }
        else if (viewModel.getPartNumber() == 4) {
            component = new PartFourComponent(activity);
        }
        else if (viewModel.getPartNumber() == 5) {
            component = new PartFiveComponent(activity);
        }
        else if (viewModel.getPartNumber() == 6) {
            component = new PartSixComponent(activity);
        }
        else if (viewModel.getPartNumber() == 7) {
            component = new PartSevenComponent(activity);
        }

        component.loadQuestionGroup(viewModel.getGroup());
        return component;
    }
}
