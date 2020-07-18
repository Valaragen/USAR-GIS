package com.usargis.usargisapi.core.model;

import com.usargis.usargisapi.util.Constant;

public enum InscriptionStatus {
    WAITING_FOR_VALIDATION(Constant.STATUS_WAITING_FOR_VALIDATION_CODE, Constant.STATUS_WAITING_FOR_VALIDATION),
    VALIDATED(Constant.STATUS_VALIDATED_CODE, Constant.STATUS_VALIDATED);

    private String code;
    private String name;

    InscriptionStatus(String code, String name) {
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

