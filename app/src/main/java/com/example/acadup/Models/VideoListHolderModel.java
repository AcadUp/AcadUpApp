package com.example.acadup.Models;

public class VideoListHolderModel {
    String name;
    String duration;
    String video;
    String notes_pdf;
    public VideoListHolderModel(){}
    public VideoListHolderModel(String name, String duration,String video,String notes_pdf) {
        this.name=name;
        this.duration=duration;
        this.video=video;
        this.notes_pdf=notes_pdf;
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

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getNotes_pdf() {
        return notes_pdf;
    }

    public void setNotes_pdf(String notes_pdf) {
        this.notes_pdf = notes_pdf;
    }
}
