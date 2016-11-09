package com.johncole.cn.internethw;

import java.io.Serializable;

/**
 * Created by johncole on 2016/6/18.
 */
public class User implements Serializable {
    String uid;
    String type;

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    String isActive;
    public User(String uid, String type, String isActive) {
        this.uid = uid;
        this.type = type;
        this.isActive = isActive;
    }



}
