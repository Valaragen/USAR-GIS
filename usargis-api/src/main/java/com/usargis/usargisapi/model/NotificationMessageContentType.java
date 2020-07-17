package com.usargis.usargisapi.model;

import com.usargis.usargisapi.util.Constant;

public enum NotificationMessageContentType {
    HTML(Constant.NMCT_HTML_CODE, Constant.NMCT_HTML),
    TEXT(Constant.NMCT_TEXT_CODE, Constant.NMCT_TEXT);

    private String code;
    private String name;

    NotificationMessageContentType(String code, String name) {
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
