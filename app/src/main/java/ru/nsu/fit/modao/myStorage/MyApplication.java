package ru.nsu.fit.modao.myStorage;

import android.app.Application;

import okhttp3.OkHttpClient;

public class MyApplication extends Application {
    private long userID;
    private OkHttpClient client;
    private String ipServer = "192.168.137.1";

    public String getIpServer() {
        return ipServer;
    }

    public OkHttpClient getClient() {
        return client;
    }

    public void setClient(OkHttpClient client) {
        this.client = client;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }
}
