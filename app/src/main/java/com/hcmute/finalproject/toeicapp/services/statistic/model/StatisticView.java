package com.hcmute.finalproject.toeicapp.services.statistic.model;

import androidx.room.PrimaryKey;

import com.hcmute.finalproject.toeicapp.entities.Statistic;

public class StatisticView {
    private Integer id;

    private String hours;

    private String date;

    private String type;

    private String name;

    private Integer correct;

    private Integer wrong;

    private Integer noSelected;

    public StatisticView() {

    }

    public StatisticView(Statistic entity) {
        this.setId(entity.getId());
        this.setCorrect(entity.getCorrect());
        this.setDate(entity.getDate());
        this.setHours(entity.getHours());
        this.setType(entity.getType());
        this.setName(entity.getName());
        this.setNoSelected(entity.getNoSelected());
        this.setWrong(entity.getWrong());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCorrect() {
        return correct;
    }

    public void setCorrect(Integer correct) {
        this.correct = correct;
    }

    public Integer getWrong() {
        return wrong;
    }

    public void setWrong(Integer wrong) {
        this.wrong = wrong;
    }

    public Integer getNoSelected() {
        return noSelected;
    }

    public void setNoSelected(Integer noSelected) {
        this.noSelected = noSelected;
    }
}
