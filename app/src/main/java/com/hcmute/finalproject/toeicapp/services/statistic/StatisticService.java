package com.hcmute.finalproject.toeicapp.services.statistic;

import android.content.Context;

import androidx.annotation.NonNull;

import com.hcmute.finalproject.toeicapp.dao.StatisticDao;
import com.hcmute.finalproject.toeicapp.database.ToeicAppDatabase;
import com.hcmute.finalproject.toeicapp.entities.Statistic;
import com.hcmute.finalproject.toeicapp.services.base.Service;
import com.hcmute.finalproject.toeicapp.services.learn.model.GradeToeicResult;

import java.util.Calendar;

@Service
public class StatisticService {
    private final Context context;
    private StatisticDao statisticDao;

    public StatisticService(@NonNull Context context) {
        this.context = context;
        this.statisticDao = ToeicAppDatabase
                .getInstance(context)
                .getStatisticDao();
    }

    public void collectResult(
            String testMethod,
            String testName,
            GradeToeicResult result
    ) {
        // Ref: https://stackoverflow.com/questions/40167572/how-to-get-current-hour-in-android
        Calendar calendar = Calendar.getInstance();
        int hour24hrs = calendar.get(Calendar.HOUR_OF_DAY);
        int hour12hrs = calendar.get(Calendar.HOUR);
        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        Statistic statistic = new Statistic();
        statistic.setCorrect(result.getNumberOfCorrectQuestions());
        statistic.setWrong(result.getTotalQuestions() - result.getNumberOfCorrectQuestions());
        statistic.setType(testMethod);
        statistic.setName(testName);
        statistic.setNoSelected(0);
        statistic.setHours(hour24hrs + ":" + minutes);
        statistic.setDate(day + "/" + month + "/" + year);

        this.statisticDao.insert(statistic);
    }
}
