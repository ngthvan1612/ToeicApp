package com.hcmute.finalproject.toeicapp.model.vocabulary;

public class VocabularyTopicStatistic {
    private String topicName;
    private Integer success;
    private Integer total;

    public VocabularyTopicStatistic() {

    }

    public VocabularyTopicStatistic(VocabularyTopic topic) {
        this.setTopicName(topic.getTopicName());
        this.setTotal(topic.getNumberOfVocabularies());
        this.setSuccess(0);
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
