package com.usargis.usargisapi.core.model;

import com.usargis.usargisapi.util.Constant;

public enum NotificationMessageSendingMode {
    APP(Constant.NMSM_APP_CODE, Constant.NMSM_APP),
    MAIL(Constant.NMSM_MAIL_CODE, Constant.NMSM_MAIL),
    PHONE(Constant.NMSM_PHONE_CODE, Constant.NMSM_PHONE);

    private String code;
    private String name;

    NotificationMessageSendingMode(String code, String name) {
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
