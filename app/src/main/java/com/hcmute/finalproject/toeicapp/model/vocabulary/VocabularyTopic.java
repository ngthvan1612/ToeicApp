package com.hcmute.finalproject.toeicapp.model.vocabulary;

@Deprecated
public class VocabularyTopic {
    private String topicName;
    private Integer numberOfVocabularies;

    public VocabularyTopic() {

    }

    public VocabularyTopic(String topicName, Integer numberOfVocabularies) {
        this.topicName = topicName;
        this.numberOfVocabularies = numberOfVocabularies;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public Integer getNumberOfVocabularies() {
        return numberOfVocabularies;
    }

    public void setNumberOfVocabularies(Integer numberOfVocabularies) {
        this.numberOfVocabularies = numberOfVocabularies;
    }
}
