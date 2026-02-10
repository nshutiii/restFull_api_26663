package com.example.Student.Registration.service;

import com.example.Student.Registration.model.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private List<Student> students = new ArrayList<>();

    public StudentService() {
        // Initializing with test data
        students.add(new Student(1L, "Alice", "Johnson", "alice@example.com", "Computer Science", 3.8));
        students.add(new Student(2L, "Bob", "Smith", "bob@example.com", "Mathematics", 3.2));
        students.add(new Student(3L, "Charlie", "Brown", "charlie@example.com", "Computer Science", 3.4));
        students.add(new Student(4L, "David", "Wilson", "david@example.com", "Physics", 3.9));
        students.add(new Student(5L, "Eve", "Davis", "eve@example.com", "Computer Science", 3.6));
    }

    public List<Student> getAllStudents() {
        return students;
    }

    public Optional<Student> getStudentById(Long studentId) {
        return students.stream().filter(s -> s.getStudentId().equals(studentId)).findFirst();
    }

    public List<Student> getStudentsByMajor(String major) {
        return students.stream()
                .filter(s -> s.getMajor().equalsIgnoreCase(major))
                .collect(Collectors.toList());
    }

    public List<Student> filterByMinGpa(Double minGpa) {
        return students.stream()
                .filter(s -> s.getGpa() >= minGpa)
                .collect(Collectors.toList());
    }

    public Student addStudent(Student student) {
        students.add(student);
        return student;
    }

    public Optional<Student> updateStudent(Long studentId, Student updatedStudent) {
        return getStudentById(studentId).map(student -> {
            student.setFirstName(updatedStudent.getFirstName());
            student.setLastName(updatedStudent.getLastName());
            student.setEmail(updatedStudent.getEmail());
            student.setMajor(updatedStudent.getMajor());
            student.setGpa(updatedStudent.getGpa());
            return student;
        });
    }
}
