package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class Tasktest {

    @Test
    void testDescription(){

    final String taskdescription = "DEMO";

    Task testee = new Task();

    testee.setTaskdescription(taskdescription);

    assertEquals(taskdescription, testee.getTaskdescription(),"should be the End");
    }

    
}
