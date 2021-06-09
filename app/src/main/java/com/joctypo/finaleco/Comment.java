package com.joctypo.finaleco;

public class Comment {

   private  String id,projectId,designerId;

    public Comment(String id, String projectId, String designerId) {
        this.id = id;

        this.projectId = projectId;
        this.designerId = designerId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getDesignerId() {
        return designerId;
    }

    public void setDesignerId(String designerId) {
        this.designerId = designerId;
    }
}
