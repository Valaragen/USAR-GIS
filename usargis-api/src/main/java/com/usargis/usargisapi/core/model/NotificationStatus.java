package com.usargis.usargisapi.core.model;

import com.usargis.usargisapi.util.Constant;

public enum NotificationStatus {
    PLANNED(Constant.STATUS_PLANNED_CODE, Constant.STATUS_PLANNED),
    SENT(Constant.STATUS_SENT_CODE, Constant.STATUS_SENT);

    private String code;
    private String name;

    NotificationStatus(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name;
    }

}
