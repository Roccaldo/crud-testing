package co.develhope.crudtesting;

import co.develhope.crudtesting.controllers.StudentController;
import co.develhope.crudtesting.entities.Student;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CrudTestingApplicationTests {
    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void contextLoads() {
        assertThat(studentController).isNotNull();
    }


    @Test
    void addStudent() throws Exception {
        Student student = new Student(1L, "Roxana", "Jackson", true);
        String userJSON = objectMapper.writeValueAsString(student);

        MvcResult result = this.mockMvc.perform(post("/student/add")
                        .contentType("application/json")
                        .content(userJSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    void getAllStudents() throws Exception {
        Student student = new Student(1L, "John", "Doe", true);
        Student student2 = new Student(2L, "Mario", "Rossi", true);
        Student student3 = new Student(3L, "Luca", "Gialli", false);
        Student student4 = new Student(3L, "Abdul", "Ghali", false);
        Student student5 = new Student(3L, "Giorgia", "Franchi", true);
        ArrayList<Student> studentsList = new ArrayList<>(Arrays.asList(student, student2, student3, student4));

        String userJSON = objectMapper.writeValueAsString(studentsList);

        MvcResult result = this.mockMvc.perform(get("/student/all")
                        .contentType("application/json")
                        .content(userJSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        List usersResponse = objectMapper.readValue(result.getResponse().getContentAsString(), List.class);
        assertThat(usersResponse.size()).isNotZero();
    }

    @Test
    void modifyStudent() throws Exception {
        Student student = new Student(1L, "Fabio", "Lanterna", false);
        student.setName("Manuel");
        String userJSON = objectMapper.writeValueAsString(student);

        MvcResult result = this.mockMvc.perform(put("/student/modify/" + student.getId())
                        .contentType("application/json")
                        .content(userJSON))
                .andDo(print())
                .andReturn();

        assertThat(student.getName()).isEqualTo("Manuel");
    }

    @Test
    void modifyStatus() throws Exception {
        Student student = new Student(1L, "Fabio", "Lanterna", false);
        student.setWorking(true);
        String userJSON = objectMapper.writeValueAsString(student);

        MvcResult result = this.mockMvc.perform(patch("/modify/status/" + student.getId())
                        .contentType("application/json")
                        .content(userJSON))
                .andDo(print())
                .andReturn();

        assertThat(student.getWorking()).isEqualTo(true);
    }

    @Test
    void deleteStudent() throws Exception {
        Student student = new Student(1L, "Manuel", "Rollini", false);
        Student student2 = new Student(2L, "Gina", "Ferramenti", false);
        ArrayList<Student> studentsList = new ArrayList<>(Arrays.asList(student, student2));
        studentsList.remove(student);

        String userJSON = objectMapper.writeValueAsString(studentsList);

        MvcResult result = this.mockMvc.perform(delete("/del/" + student.getId()))
                .andDo(print())
                .andReturn();

        assertThat(studentsList.size()).isEqualTo(1);
    }
}
