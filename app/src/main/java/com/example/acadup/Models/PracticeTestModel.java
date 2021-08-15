package com.example.acadup.Models;

public class PracticeTestModel {
    private String imageSource;
    private String doc_name;
    private String name;
    public PracticeTestModel(){}

    public PracticeTestModel(String imageSource,String doc_name,String name) {
        this.imageSource = imageSource;
        this.doc_name=doc_name;
        this.name=name;
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    public String getDoc_name() {
        return doc_name;
    }

    public void setDoc_name(String doc_name) {
        this.doc_name = doc_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
