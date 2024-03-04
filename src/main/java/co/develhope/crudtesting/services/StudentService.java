package co.develhope.crudtesting.services;

import co.develhope.crudtesting.entities.Student;
import co.develhope.crudtesting.repositories.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepo repo;

    public Student create(Student student) {
        return repo.save(student);
    }

    public List<Student> findAll() {
        return repo.findAll();
    }

    public Optional<Student> searchById(Long id) {
        return repo.findById(id);
    }

    public Optional<Student> modify(Long id, Student student) {
        Optional<Student> studentOptional = repo.findById(id);
        if (studentOptional.isPresent()) {
            studentOptional.get().setName(student.getName());
            studentOptional.get().setSurname(student.getSurname());
            repo.save(studentOptional.get());
        } else {
            return Optional.empty();
        }
        return studentOptional;
    }

    public Optional<Student> modifyStatus(Long id, boolean isWorking) {
        Optional<Student> studentOptional = repo.findById(id);
        if (studentOptional.isPresent()) {
            studentOptional.get().setWorking(isWorking);
            repo.save(studentOptional.get());
        } else {
            return Optional.empty();
        }
        return studentOptional;
    }
}
