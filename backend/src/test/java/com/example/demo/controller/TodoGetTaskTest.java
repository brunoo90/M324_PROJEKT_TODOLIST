package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.fail;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import jakarta.transaction.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
public class TodoGetTaskTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Transactional
    public void testGetTasks() {
        try {
            // Erst Task hinzufügen
            JSONObject taskJson = new JSONObject();
            taskJson.put("taskdescription", "Get test task");

            mockMvc.perform(MockMvcRequestBuilders.post("/task")
                    .content(taskJson.toString())
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

            // Dann prüfen, ob die Task in der Liste erscheint
            mockMvc.perform(MockMvcRequestBuilders.get("/task"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(org.hamcrest.Matchers.containsString("Get test task")));

        } catch (Exception e) {
            fail("Test fehlgeschlagen wegen: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
