package com.example.cpisfun;

public class Admin {
    private String name;
    private String text;
    private String avatarUrl;

    public Admin() {
        // Default constructor required for calls to DataSnapshot.getValue(Admin.class)
    }

    public Admin(String name, String text, String avatarUrl) {
        this.name = name;
        this.text = text;
        this.avatarUrl = avatarUrl;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }
}
