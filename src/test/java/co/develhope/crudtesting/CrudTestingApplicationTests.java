package co.develhope.crudtesting;

import co.develhope.crudtesting.controllers.StudentController;
import co.develhope.crudtesting.entities.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CrudTestingApplicationTests {
    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void contextLoads() {
        assertThat(studentController).isNotNull();
    }

    @Test
    void addStudent() {

        Student student = new Student(1L, "John", "Doe", true);


        ResponseEntity<Student> response = testRestTemplate.postForEntity("http://localhost:" + port + "/student/add", student, Student.class);


        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();

    }

    @Test
    void getAllStudents() {
        Student student = new Student(1L, "John", "Doe", true);
        Student student2 = new Student(2L, "Mario", "Rossi", true);
        Student student3 = new Student(3L, "Luca", "Gialli", false);
        Student student4 = new Student(3L, "Abdul", "Ghali", false);
        Student student5 = new Student(3L, "Giorgia", "Franchi", true);
        ArrayList<Student> studentsList = new ArrayList<>(Arrays.asList(student, student2, student3, student4));

        ResponseEntity<List> response = testRestTemplate.getForEntity("http://localhost:" + port + "/student/all", List.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody().isEmpty()).isFalse();
    }

    @Test
    void modifyStudent() {
        Student student = new Student(1L, "Fabio", "Lanterna", false);
        ResponseEntity<Student> response = studentController.modify(1L, new Student(5L, "Roccaldo", "Digrisolo", true));

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody().getName()).isEqualTo("Roccaldo");
        assertThat(response.getBody().getSurname()).isEqualTo("Digrisolo");
    }

    @Test
    void modifyStatus() {
        Student student = new Student(1L, "Fabio", "Lanterna", false);
        ResponseEntity<Student> response = studentController.modifyStatus(1L, true);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody().getWorking()).isEqualTo(true);
    }
}
