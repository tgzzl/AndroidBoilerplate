package com.optilink.android.boilerplate.pojo;

/**
 * Created by tg on 2015/10/17.
 */
public class Contributor {

    public String login;
    public String type;
    public String avatar_url;
    public boolean site_admin;
    public int contributions;

    @Override
    public String toString() {
        return "Contributor{" +
                "login='" + login + '\'' +
                ", type='" + type + '\'' +
                ", avatar_url='" + avatar_url + '\'' +
                ", site_admin=" + site_admin +
                ", contributions=" + contributions +
                '}';
    }
}
