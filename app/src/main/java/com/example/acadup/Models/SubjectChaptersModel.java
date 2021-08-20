package com.example.acadup.Models;

import java.util.Date;

public class SubjectChaptersModel {

    String name;
    String duration;
    String lectures;
    String image;
    String pdf;
    String answerPdf,course_name,amount;
    String video,score;
    Date date;



    public SubjectChaptersModel() {

    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAnswerPdf() {
        return answerPdf;
    }

    public void setAnswerPdf(String answerPdf) {
        this.answerPdf = answerPdf;
    }

    public SubjectChaptersModel(String name, String duration, String lectures, String image, String pdf, String video,String answerPdf,String score,Date date,String course_name,String amount) {
        this.name = name;
        this.duration = duration;
        this.lectures = lectures;
        this.image = image;
        this.pdf=pdf;
        this.video=video;
        this.answerPdf=answerPdf;
        this.score=score;
        this.date=date;
        this.course_name=course_name;
        this.amount=amount;
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
