package com.joctypo.finaleco.model;

public class Project {

    private String id,userId,projectDescription,category,designerId;
    private int day,month;
    private boolean taken;


    public Project(String id, String userId, String projectDescription, String category, int day, int month) {
        this.id = id;
        this.userId = userId;
        this.projectDescription = projectDescription;
        this.category = category;
        this.day = day;
        this.month = month;
        this.taken =false;
    }


    public Project() {

    }

    public String getDesignerId() {
        return designerId;
    }

    public void setDesignerId(String designerId) {
        this.designerId = designerId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }
}
