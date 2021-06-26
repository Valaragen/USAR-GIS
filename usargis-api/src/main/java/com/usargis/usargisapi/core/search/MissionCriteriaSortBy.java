package com.usargis.usargisapi.core.search;

import com.usargis.usargisapi.util.Constant;

public enum MissionCriteriaSortBy {
    DEFAULT(Constant.ID, Constant.CR_ROOT_MISSION),
    ID(Constant.ID, Constant.CR_ROOT_MISSION),
    NAME(Constant.NAME, Constant.CR_ROOT_MISSION),
    STATUS(Constant.STATUS, Constant.CR_ROOT_MISSION),
    START_DATE(Constant.START_DATE, Constant.CR_ROOT_MISSION),
    END_DATE(Constant.END_DATE, Constant.CR_ROOT_MISSION),
    PLANNED_START_DATE(Constant.PLANNED_START_DATE, Constant.CR_ROOT_MISSION),
    EXPECTED_DURATION_IN_DAYS(Constant.PLANNED_START_DATE, Constant.CR_ROOT_MISSION),
    ADDRESS(Constant.ADDRESS, Constant.CR_ROOT_MISSION),
    CREATIONDATE(Constant.CREATION_DATE, Constant.CR_ROOT_MISSION),
    LAST_EDITION_DATE(Constant.LAST_EDITION_DATE, Constant.CR_ROOT_MISSION),
    AUTHOR_USERNAME(Constant.USERNAME, Constant.CR_ROOT_USER);

    private String dbFieldName;
    private String tableName;

    MissionCriteriaSortBy(String dbFieldName, String tableName) {
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
