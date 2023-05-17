package com.hcmute.finalproject.toeicapp.services.learn;

import androidx.annotation.NonNull;

import com.google.android.material.color.utilities.Score;
import com.hcmute.finalproject.toeicapp.services.base.Service;
import com.hcmute.finalproject.toeicapp.services.learn.model.GradeToeicFullTestResult;
import com.hcmute.finalproject.toeicapp.services.learn.model.GradeToeicResult;
import com.hcmute.finalproject.toeicapp.services.learn.model.GradeToeicPayload;
import com.hcmute.finalproject.toeicapp.services.learn.model.GradeToeicRate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ToeicTestGradeService {
    public ToeicTestGradeService() {

    }

    private class ScoreRange {
        private Integer correctAnswerMin, correctAnswerMax;
        private Integer scoreMin, scoreMax;

        public ScoreRange() { }

        public ScoreRange(Integer correctAnswerMin, Integer correctAnswerMax, Integer scoreMin, Integer scoreMax) {
            this.correctAnswerMin = correctAnswerMin;
            this.correctAnswerMax = correctAnswerMax;
            this.scoreMin = scoreMin;
            this.scoreMax = scoreMax;
        }

        public Integer getCorrectAnswerMax() {
            return correctAnswerMax;
        }

        public void setCorrectAnswerMax(Integer correctAnswerMax) {
            this.correctAnswerMax = correctAnswerMax;
        }

        public Integer getCorrectAnswerMin() {
            return correctAnswerMin;
        }

        public void setCorrectAnswerMin(Integer correctAnswerMin) {
            this.correctAnswerMin = correctAnswerMin;
        }

        public Integer getScoreMin() {
            return scoreMin;
        }

        public void setScoreMin(Integer scoreMin) {
            this.scoreMin = scoreMin;
        }

        public Integer getScoreMax() {
            return scoreMax;
        }

        public void setScoreMax(Integer scoreMax) {
            this.scoreMax = scoreMax;
        }
    }

    private List<ScoreRange> getScoreRangesForListening() {
        List<ScoreRange> scoreRanges = new ArrayList<>();
        scoreRanges.add(new ScoreRange(0, 0, 0, 0));
        scoreRanges.add(new ScoreRange(1, 5, 10, 30));
        scoreRanges.add(new ScoreRange(6, 10, 25, 40));
        scoreRanges.add(new ScoreRange(11, 15, 35, 65));
        scoreRanges.add(new ScoreRange(16, 20, 55, 80));
        scoreRanges.add(new ScoreRange(21, 25, 75, 100));
        scoreRanges.add(new ScoreRange(26, 30, 85, 120));
        scoreRanges.add(new ScoreRange(31, 35, 100, 155));
        scoreRanges.add(new ScoreRange(36, 40, 125, 185));
        scoreRanges.add(new ScoreRange(41, 45, 150, 220));
        scoreRanges.add(new ScoreRange(46, 50, 175, 245));
        scoreRanges.add(new ScoreRange(51, 55, 205, 275));
        scoreRanges.add(new ScoreRange(56, 60, 230, 305));
        scoreRanges.add(new ScoreRange(61, 65, 255, 330));
        scoreRanges.add(new ScoreRange(66, 70, 285, 360));
        scoreRanges.add(new ScoreRange(71, 75, 315,390));
        scoreRanges.add(new ScoreRange(76, 80, 340, 420));
        scoreRanges.add(new ScoreRange(81, 85, 370, 450));
        scoreRanges.add(new ScoreRange(86, 90, 415, 475));
        scoreRanges.add(new ScoreRange(91, 95, 450, 495));
        scoreRanges.add(new ScoreRange(96, 100, 495, 495));
        return scoreRanges;
    }

    private List<ScoreRange> getScoreRangesForReading() {
        List<ScoreRange> scoreRanges = new ArrayList<>();
        scoreRanges.add(new ScoreRange(0, 0, 0, 5));
        scoreRanges.add(new ScoreRange(1, 5, 10, 20));
        scoreRanges.add(new ScoreRange(6, 10, 20, 40));
        scoreRanges.add(new ScoreRange(11, 15, 35, 55));
        scoreRanges.add(new ScoreRange(16, 20, 45, 70));
        scoreRanges.add(new ScoreRange(21, 25, 60, 90));
        scoreRanges.add(new ScoreRange(26, 30, 70, 120));
        scoreRanges.add(new ScoreRange(31, 35, 90, 145));
        scoreRanges.add(new ScoreRange(36, 40, 110, 175));
        scoreRanges.add(new ScoreRange(41, 45, 140, 205));
        scoreRanges.add(new ScoreRange(46, 50, 170, 235));
        scoreRanges.add(new ScoreRange(51, 55, 205, 270));
        scoreRanges.add(new ScoreRange(56, 60, 235, 295));
        scoreRanges.add(new ScoreRange(61, 65, 265, 325));
        scoreRanges.add(new ScoreRange(66, 70, 295, 350));
        scoreRanges.add(new ScoreRange(71, 75, 325,380));
        scoreRanges.add(new ScoreRange(76, 80, 350, 395));
        scoreRanges.add(new ScoreRange(81, 85, 375, 420));
        scoreRanges.add(new ScoreRange(86, 90, 405, 440));
        scoreRanges.add(new ScoreRange(91, 95, 430, 475));
        scoreRanges.add(new ScoreRange(96, 100, 470, 495));
        return scoreRanges;
    }

    public GradeToeicFullTestResult gradeTest(
            @NonNull List<GradeToeicPayload> payloads
    ) {
        int correctListeningQuestions = 0, correctReadingQuestions = 0;

        for (GradeToeicPayload payload : payloads) {
            final Integer partNumber = payload.getPartNumber();
            if (1 <= partNumber && partNumber <= 4) {
                if (payload.getCorrectAnswer().equals(payload.getUserAnswer())) {
                    correctListeningQuestions++;
                }
            }
            else {
                if (payload.getCorrectAnswer().equals(payload.getUserAnswer())) {
                    correctReadingQuestions++;
                }
            }
        }

        final List<ScoreRange> scoreRangeListening = this.getScoreRangesForListening();
        final List<ScoreRange> scoreRangeReading = this.getScoreRangesForReading();

        assert scoreRangeReading.size() == 21;
        assert scoreRangeListening.size() == 21;

        int lmin = 0, lmax = 0, rmin = 0, rmax = 0;

        for (int i = 0; i < 21; ++i) {
            if (scoreRangeListening.get(i).getCorrectAnswerMin() <= correctListeningQuestions
                && correctListeningQuestions <= scoreRangeListening.get(i).getCorrectAnswerMax()) {
                lmin = scoreRangeListening.get(i).scoreMin;
                lmax = scoreRangeListening.get(i).scoreMax;
            }

            if (scoreRangeReading.get(i).getCorrectAnswerMin() <= correctReadingQuestions
                && correctReadingQuestions <= scoreRangeReading.get(i).getCorrectAnswerMax()) {
                rmin = scoreRangeReading.get(i).scoreMin;
                rmax = scoreRangeReading.get(i).scoreMax;
            }
        }

        GradeToeicFullTestResult result = new GradeToeicFullTestResult();
        result.setTotalScoreMax(lmax + rmax);
        result.setTotalScoreMin(lmin + rmin);

        return result;
    }

    public GradeToeicResult gradePart(
            @NonNull List<GradeToeicPayload> payload,
            @NonNull Integer totalQuestions
    ) {
        GradeToeicResult result = new GradeToeicResult();
        int correct = 0, skip = 0;
        result.setPayloads(new ArrayList<>());

        result.setTotalQuestions(totalQuestions);

        for (GradeToeicPayload gradeToeicPayload : payload) {
            result.getPayloads().add(gradeToeicPayload);
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
        merged.setPayloads(new ArrayList<>());

        int totalQuestions = 0;
        int skipQuestions = 0;
        int correctQuestions = 0;

        for (GradeToeicResult result : results) {
            totalQuestions += result.getTotalQuestions();
            correctQuestions += result.getNumberOfCorrectQuestions();
            skipQuestions += result.getNumberOfSkipQuestions();
            merged.getPayloads().addAll(result.getPayloads());
        }

        merged.setTotalQuestions(totalQuestions);
        merged.setNumberOfCorrectQuestions(correctQuestions);
        merged.setNumberOfSkipQuestions(skipQuestions);

        if (2 * correctQuestions >= totalQuestions) merged.setRate(GradeToeicRate.GOOD);
        else merged.setRate(GradeToeicRate.BAD);

        return merged;
    }
}
