package com.usargis.usargisapi.core.model;

import com.usargis.usargisapi.util.Constant;

public enum MissionStatus {
    ONFOCUS(Constant.STATUS_ONFOCUS_CODE, Constant.STATUS_ONFOCUS),
    PRE_ALERT(Constant.STATUS_TEAMENGAGEMENT_CODE, Constant.STATUS_TEAMENGAGEMENT),
    TEAM_ENGAGEMENT(Constant.STATUS_TEAMENGAGEMENT_CODE, Constant.STATUS_TEAMENGAGEMENT),
    ONGOING(Constant.STATUS_ONGOING_CODE, Constant.STATUS_ONGOING),
    FINISHED(Constant.STATUS_FINISHED_CODE, Constant.STATUS_FINISHED),
    CANCELLED(Constant.STATUS_CANCELLED_CODE, Constant.STATUS_CANCELLED);

    private String code;
    private String name;

    MissionStatus(String code, String name) {
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
