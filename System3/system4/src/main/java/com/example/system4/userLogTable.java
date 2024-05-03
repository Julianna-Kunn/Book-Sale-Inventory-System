package com.example.system4;

public class userLogTable {
    String id;
    String user_type;
    String date_time;

    public userLogTable(String user_type, String id, String date_time) {
        this.user_type = user_type;
        this.id = id;
        this.date_time = date_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }
}
