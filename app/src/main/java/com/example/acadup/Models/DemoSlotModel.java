package com.example.acadup.Models;

public class DemoSlotModel {
    String available,classes,date,email,formatted_date,month,year,name,phone,time,subject;
    public DemoSlotModel(){

    }
    public DemoSlotModel(String available, String classes, String date, String email,
                         String formatted_date, String month,String year, String name, String phone, String time, String subject) {
        this.available = available;
        this.classes = classes;
        this.date = date;
        this.email = email;
        this.formatted_date = formatted_date;
        this.month = month;
        this.year=year;
        this.name = name;
        this.phone = phone;
        this.time = time;
        this.subject=subject;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFormatted_date() {
        return formatted_date;
    }

    public void setFormatted_date(String formatted_date) {
        this.formatted_date = formatted_date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
