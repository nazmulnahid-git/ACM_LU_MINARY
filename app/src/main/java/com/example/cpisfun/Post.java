package com.example.cpisfun;

import java.util.HashMap;
import java.util.Map;

public class Post {
    private String postId;
    private String userId;
    private String userEmail;
    private String text;
    private int likes;
    private Map<String, Boolean> likedUsers;
    private Map<String, Comment> comments;

    public Post() {
        // Default constructor required for Firebase
    }

    public Post(String postId, String userId, String userEmail, String text) {
        this.postId = postId;
        this.userId = userId;
        this.userEmail = userEmail;
        this.text = text;
        this.likes = 0;
        this.likedUsers = new HashMap<>();
        this.comments = new HashMap<>();
    }

    // Getters and setters
    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public Map<String, Boolean> getLikedUsers() {
        return likedUsers;
    }

    public void setLikedUsers(Map<String, Boolean> likedUsers) {
        this.likedUsers = likedUsers;
    }

    public Map<String, Comment> getComments() {
        return comments;
    }

    public void setComments(Map<String, Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment) {
        if (this.comments == null) {
            this.comments = new HashMap<>();
        }
        this.comments.put(comment.getCommentId(), comment);
    }
}