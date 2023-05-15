package com.hcmute.finalproject.toeicapp.model.toeic;

import java.io.Serializable;
import java.util.List;

public class TestToeicFullTest implements Serializable {
    private String slug;
    private String fullName;
    private List<TestToeicPart> parts;

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<TestToeicPart> getParts() {
        return parts;
    }

    public void setParts(List<TestToeicPart> parts) {
        this.parts = parts;
    }

    public TestToeicFullTest() {

    }
}
