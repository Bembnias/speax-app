package com.example.speax;

public class User {
    public String name, email, id, search;

    public User() {};

    public User(String name, String email, String id, String search) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.search = search;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getUserId() {
        return id;
    }
    public void setUserId(String id) {
        this.id = id;
    }
    public String getSearch() {
        return search;
    }
    public void setSearch(String search) {
        this.search = search;
    }
}
