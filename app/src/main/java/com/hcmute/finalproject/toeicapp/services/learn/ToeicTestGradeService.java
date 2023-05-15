package com.hcmute.finalproject.toeicapp.services.learn;

import androidx.annotation.NonNull;

import com.hcmute.finalproject.toeicapp.services.base.Service;
import com.hcmute.finalproject.toeicapp.services.learn.model.GradeToeicPartResult;
import com.hcmute.finalproject.toeicapp.services.learn.model.GradeToeicPayload;
import com.hcmute.finalproject.toeicapp.services.learn.model.GradeToeicRate;

import java.util.List;

@Service
public class ToeicTestGradeService {
    public ToeicTestGradeService() {

    }

    public GradeToeicPartResult gradePart(
            @NonNull List<GradeToeicPayload> payload
    ) {
        GradeToeicPartResult result = new GradeToeicPartResult();
        int correct = 0;

        result.setTotalQuestions(payload.size());

        for (GradeToeicPayload gradeToeicPayload : payload) {
            if (gradeToeicPayload.getUserAnswer().equals(gradeToeicPayload.getCorrectAnswer())) {
                correct++;
            }
        }

        result.setNumberOfCorrectQuestions(correct);

        if (2 * correct >= payload.size()) result.setRate(GradeToeicRate.GOOD);
        else result.setRate(GradeToeicRate.BAD);

        return result;
    }
}
