package com.example.system4;

public class userTable {
    String id;
    String user_type;
    String name;
    String email;
    String contact_number;
    String password;
    String create;
    String update;

    public userTable(String id, String user_type, String name, String email, String contact_number, String password, String create, String update) {
        this.id = id;
        this.user_type = user_type;
        this.name = name;
        this.email = email;
        this.contact_number = contact_number;
        this.password = password;
        this.create = create;
        this.update = update;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public String getCreate() {
        return create;
    }

    public void setCreate(String create) {
        this.create = create;
    }
}
