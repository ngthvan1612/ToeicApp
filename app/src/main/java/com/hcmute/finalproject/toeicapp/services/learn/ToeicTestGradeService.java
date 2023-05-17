package com.hcmute.finalproject.toeicapp.services.learn;

import androidx.annotation.NonNull;

import com.hcmute.finalproject.toeicapp.services.base.Service;
import com.hcmute.finalproject.toeicapp.services.learn.model.GradeToeicResult;
import com.hcmute.finalproject.toeicapp.services.learn.model.GradeToeicPayload;
import com.hcmute.finalproject.toeicapp.services.learn.model.GradeToeicRate;

import java.util.List;

@Service
public class ToeicTestGradeService {
    public ToeicTestGradeService() {

    }

    public GradeToeicResult gradePart(
            @NonNull List<GradeToeicPayload> payload,
            @NonNull Integer totalQuestions
    ) {
        GradeToeicResult result = new GradeToeicResult();
        int correct = 0, skip = 0;

        result.setTotalQuestions(totalQuestions);

        for (GradeToeicPayload gradeToeicPayload : payload) {
            if (gradeToeicPayload.getUserAnswer() == null || gradeToeicPayload.getUserAnswer().equals("")) {
                skip++;
                continue;
            }
            if (gradeToeicPayload.getUserAnswer().equals(gradeToeicPayload.getCorrectAnswer())) {
                correct++;
            }
        }

        skip += totalQuestions - payload.size();

        result.setNumberOfCorrectQuestions(correct);
        result.setNumberOfSkipQuestions(skip);

        if (2 * correct >= payload.size()) result.setRate(GradeToeicRate.GOOD);
        else result.setRate(GradeToeicRate.BAD);

        return result;
    }

    public GradeToeicResult mergeResult(
            @NonNull List<GradeToeicResult> results
    ) {
        GradeToeicResult merged = new GradeToeicResult();

        int totalQuestions = 0;
        int skipQuestions = 0;
        int correctQuestions = 0;

        for (GradeToeicResult result : results) {
            totalQuestions += result.getTotalQuestions();
            correctQuestions += result.getNumberOfCorrectQuestions();
            skipQuestions += result.getNumberOfSkipQuestions();
        }

        merged.setTotalQuestions(totalQuestions);
        merged.setNumberOfCorrectQuestions(correctQuestions);
        merged.setNumberOfSkipQuestions(skipQuestions);

        if (2 * correctQuestions >= totalQuestions) merged.setRate(GradeToeicRate.GOOD);
        else merged.setRate(GradeToeicRate.BAD);

        return merged;
    }
}
