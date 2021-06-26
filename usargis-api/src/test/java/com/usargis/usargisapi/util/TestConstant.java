package com.usargis.usargisapi.util;

import java.time.LocalDateTime;

public abstract class TestConstant {
    public static final LocalDateTime SAMPLE_LOCAL_DATE_TIME = LocalDateTime.of(2020, 8, 20, 18, 30, 15, 254);
    public static final LocalDateTime SAMPLE_LOCAL_DATE_TIME_2 = LocalDateTime.of(2020, 8, 22, 18, 30, 15, 254);

    private TestConstant() {
    }
}
