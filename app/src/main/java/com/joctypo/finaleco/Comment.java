package com.joctypo.finaleco;

public class Comment {

   private  String id,comment,projectId,designerId;

   public Comment(){

   }
    public Comment(String id, String comment, String projectId, String designerId) {
        this.id = id;
        this.comment = comment;
        this.projectId = projectId;
        this.designerId = designerId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
