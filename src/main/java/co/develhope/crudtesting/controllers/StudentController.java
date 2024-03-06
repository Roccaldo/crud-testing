package co.develhope.crudtesting.controllers;

import co.develhope.crudtesting.entities.Student;
import co.develhope.crudtesting.services.StudentService;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/add")
    public ResponseEntity<Student> create(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.create(student));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Student>> findAll() {
        return ResponseEntity.ok(studentService.findAll());
    }

    @PutMapping("/modify/{id}")
    public ResponseEntity<Student> modify(@PathVariable Long id, @RequestBody Student student) {
        Optional<Student> optionalStudent = studentService.modify(id, student);
        if (optionalStudent.isPresent()) {
            return ResponseEntity.ok(optionalStudent.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/modify/status/{id}")
    public ResponseEntity<Student> modifyStatus(@PathVariable Long id, @RequestParam boolean working) {
        Optional<Student> optionalStudent = studentService.modifyStatus(id, working);
        if (optionalStudent.isPresent()) {
            return ResponseEntity.ok(optionalStudent.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/del/{id}")
    public ResponseEntity<Student> delete(@PathVariable Long id) {
        Student optionalStudent = studentService.delete(id);
        if (optionalStudent != null) {
            return ResponseEntity.ok(optionalStudent);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
