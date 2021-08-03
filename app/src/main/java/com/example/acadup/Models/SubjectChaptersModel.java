package com.example.acadup.Models;

public class SubjectChaptersModel {

    String name;
    String duration;
    String lectures;
    String image;
    String pdf;
    String video;



    public SubjectChaptersModel() {

    }

    public SubjectChaptersModel(String name, String duration, String lectures, String image,String pdf,String video) {
        this.name = name;
        this.duration = duration;
        this.lectures = lectures;
        this.image = image;
        this.pdf=pdf;
        this.video=video;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getLectures() {
        return lectures;
    }

    public void setLectures(String lectures) {
        this.lectures = lectures;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
