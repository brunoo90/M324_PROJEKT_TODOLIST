package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class Tasktest {

    @Test
    void testDescription() {

        final String taskDescription = "DEMO";

        Task testee = new Task();

        testee.setTaskDescription(taskDescription); // camelCase Setter

        assertEquals(taskDescription, testee.getTaskDescription(), "should be the End"); // camelCase Getter
    }

}
