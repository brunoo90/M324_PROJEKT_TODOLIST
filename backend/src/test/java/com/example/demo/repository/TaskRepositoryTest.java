package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.Task;
import com.example.demo.Repository.TaskRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = com.example.demo.DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
public class TaskRepositoryTest {

    @Autowired 
    TaskRepository taskRepository;

    @Test
    public void testSaveAndRetrieve() {
        Task t = new Task();
        t.setTaskDescription("test"); // ✅ Korrigierte Methode
        taskRepository.save(t);

        assertNotNull(t.getId()); // Prüft, ob die ID nach dem Speichern generiert wurde

        Task retrievedTask = taskRepository.findById(t.getId()).get();
        assertNotNull(retrievedTask); // Prüft, ob ein Task gefunden wurde

        assertEquals(t.getId(), retrievedTask.getId()); // Vergleicht die IDs
    }
}
