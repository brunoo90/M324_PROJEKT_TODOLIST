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
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test") /* make sure u have that line! */
public class TaskRepositoryTest {

    @Autowired 
    TaskRepository taskRepository;

    @Test
    public void testSaveAndRetrieve() {
        Task t = new Task();
        t.setTaskdescription("test");
        taskRepository.save(t);
        assertNotNull(t.getId());
        Task retrievedTask = taskRepository.findById(t.getId()).get();
        assertNotNull(retrievedTask);
        assertEquals(t.getId(), retrievedTask.getId());
    }
}
