package com.usargis.usargisapi.model;

import com.usargis.usargisapi.util.Constant;

public enum EventStatus {
    PENDING(Constant.STATUS_PENDING_CODE, Constant.STATUS_PENDING),
    FINISHED(Constant.STATUS_FINISHED_CODE, Constant.STATUS_FINISHED),
    CANCELLED(Constant.STATUS_CANCELLED_CODE, Constant.STATUS_CANCELLED);

    private String code;
    private String name;

    EventStatus(String code, String name) {
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
