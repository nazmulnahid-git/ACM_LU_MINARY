package com.example.cpisfun;

public class Comment {
    private String commentId;
    private String userId;
    private String postId;
    private String text;
    private String userName; // Add userName field for commenter's name

    public Comment() {
        // Default constructor required for Firebase
    }

    public Comment(String commentId, String userId, String postId, String text) {
        this.commentId = commentId;
        this.userId = userId;
        this.postId = postId;
        this.text = text;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
