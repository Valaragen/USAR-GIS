package com.usargis.usargisapi.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

class GroupTest {
    private Group objectToTest;

    @BeforeEach
    void setup() {
        objectToTest = new Group();
    }

    @Test
    void newInstanceTest() {
        String name = "test";
        objectToTest = new Group(name, new HashSet<>());

        Assertions.assertEquals(objectToTest.getName(), name);
        Assertions.assertTrue(objectToTest.getUsers().isEmpty());
    }


}
