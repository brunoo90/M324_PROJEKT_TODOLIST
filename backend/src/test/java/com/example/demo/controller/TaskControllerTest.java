package com.example.demo.controller;

import org.hamcrest.Matchers;
import org.json.JSONException;
import org.json.JSONObject;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = com.example.demo.DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Transactional
    public void testEmptyTaskListViaREST() {
        try {
            // Auf "/task" anpassen, nicht "/"
            mockMvc.perform(MockMvcRequestBuilders.get("/task"))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("[]")));
        } catch (Exception e) {
            fail("test failed because " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    @Transactional
    public void testAddTaskListViaREST() {
        final String task = "test task";
        JSONObject t = new JSONObject();
        try {
            // Korrigierter Feldname: taskDescription (Groß-/Kleinschreibung beachten!)
            t.put("taskDescription", task);
        } catch (JSONException e) {
            fail("crashed on creating JSON param");
        }

        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/task")
                    .content(t.toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("utf-8"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());

            mockMvc.perform(MockMvcRequestBuilders.get("/task"))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(task)));
        } catch (Exception e) {
            fail("test failed because " + e.getMessage());
            e.printStackTrace();
        }
    }
}
