package com.usargis.usargisapi.core.search;

import com.usargis.usargisapi.util.Constant;

public enum AvailabilityCriteriaSortBy {
    DEFAULT(Constant.ID, Constant.CR_ROOT_AVAILABILITY),
    ID(Constant.ID, Constant.CR_ROOT_AVAILABILITY),
    START_DATE(Constant.START_DATE, Constant.CR_ROOT_AVAILABILITY),
    END_DATE(Constant.END_DATE, Constant.CR_ROOT_AVAILABILITY),
    USER_USERNAME(Constant.USERNAME, Constant.CR_ROOT_USER),
    MISSION_NAME(Constant.NAME, Constant.CR_ROOT_MISSION),
    MISSION_STATUS(Constant.STATUS, Constant.CR_ROOT_MISSION);

    private String dbFieldName;
    private String tableName;

    AvailabilityCriteriaSortBy(String dbFieldName, String tableName) {
        this.dbFieldName = dbFieldName;
        this.tableName = tableName;
    }

    public String getName() {
        return dbFieldName;
    }

    public String getTableName() {
        return tableName;
    }

    public String toString() {
        return dbFieldName;
    }
}
