package com.example.acadup.Models;

public class VideoListHolderModel {
    String video_name;
    String video_duration;

    public VideoListHolderModel(String video_name, String video_duration) {
        this.video_name = video_name;
        this.video_duration = video_duration;
    }

    public String getVideo_name() {
        return video_name;
    }

    public void setVideo_name(String video_name) {
        this.video_name = video_name;
    }

    public String getVideo_duration() {
        return video_duration;
    }

    public void setVideo_duration(String video_duration) {
        this.video_duration = video_duration;
    }
}
