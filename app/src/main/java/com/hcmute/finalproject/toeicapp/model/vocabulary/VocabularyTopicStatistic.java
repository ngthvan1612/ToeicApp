package com.hcmute.finalproject.toeicapp.model.vocabulary;

import com.hcmute.finalproject.toeicapp.services.backend.vocabs.model.AndroidToeicVocabTopic;

public class VocabularyTopicStatistic {
    private String topicName;
    private Integer success;
    private Integer total;

    private String imageFileName;

    public VocabularyTopicStatistic() {

    }

    public VocabularyTopicStatistic(VocabularyTopic topic) {
        this.setTopicName(topic.getTopicName());
        this.setTotal(topic.getNumberOfVocabularies());
        this.setSuccess(0);
    }

    public VocabularyTopicStatistic(AndroidToeicVocabTopic androidToeicVocabTopic) {
        this.setTopicName(androidToeicVocabTopic.getTopicName());
        this.setTotal(androidToeicVocabTopic.getWords().size());
        this.setSuccess(0);
        this.setImageFileName(androidToeicVocabTopic.getImageFileName());
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
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
