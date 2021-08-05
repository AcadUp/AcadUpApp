package com.example.acadup.Models;

public class HotCourseModel {
    private String name,image,link;

    public HotCourseModel(){}
    public HotCourseModel(String name,String image,String link){
        this.name=name;
        this.image=image;
        this.link=link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
