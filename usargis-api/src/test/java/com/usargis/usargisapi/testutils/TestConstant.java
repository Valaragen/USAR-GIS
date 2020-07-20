package com.usargis.usargisapi.testutils;

import java.time.LocalDateTime;

public abstract class TestConstant {
    private TestConstant() {
    }
     public static final LocalDateTime SAMPLE_LOCAL_DATE_TIME = LocalDateTime.of(2020, 8, 20, 18,30, 15, 254);
}
