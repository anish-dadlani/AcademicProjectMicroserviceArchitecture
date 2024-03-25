package com.student.service;

import com.student.mapper.StudentMapper;
import com.student.model.dto.request.StudentRequestDto;
import com.student.model.dto.response.StudentResponseDto;
import com.student.model.entity.Student;
import com.student.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<StudentResponseDto> getStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream()
                .map(StudentMapper::toStudentDto)
                .collect(Collectors.toList());
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Student not found with id: " + id));
    }

    public ResponseEntity<StudentResponseDto> addStudent(StudentRequestDto studentRequestDto) {
        Optional<Student> studentByEmail = studentRepository.findStudentByEmail(studentRequestDto.getEmail());
        if (studentByEmail.isPresent()) {
            throw new IllegalStateException("Email Taken");
        }
        Student student = StudentMapper.toStudent(studentRequestDto);
        Student savedStudent = studentRepository.save(student);
        StudentResponseDto studentResponseDto = StudentMapper.toStudentDto(savedStudent);
        return ResponseEntity.ok(studentResponseDto);
    }

    public ResponseEntity<String> deleteStudent(Long studentId) {
        boolean studentExists = studentRepository.existsById(studentId);
        if (!studentExists) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Student with Id: " + studentId + " does not exist");
        }
        studentRepository.deleteById(studentId);
        return ResponseEntity.ok("Student with Id: " + studentId + " deleted successfully");
    }

    @Transactional
    public ResponseEntity<String> updateStudent(Long studentId, StudentRequestDto studentRequestDto) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException("Student with Id: " + studentId + " does not exist"));

        if (!studentRequestDto.getName().isEmpty() && !Objects.equals(student.getName(), studentRequestDto.getName())) {
            student.setName(studentRequestDto.getName());
        }

        if (!studentRequestDto.getEmail().isEmpty() && !Objects.equals(student.getEmail(), studentRequestDto.getEmail())) {
            Optional<Student> studentByEmail = studentRepository.findStudentByEmail(studentRequestDto.getEmail());
            if (studentByEmail.isPresent() && !Objects.equals(studentByEmail.get().getId(), studentId)) {
                throw new IllegalStateException("Email Already Taken");
            }
            student.setEmail(studentRequestDto.getEmail());
        }

        studentRepository.save(student);
        return ResponseEntity.ok("Student with Id: " + studentId + " updated successfully");
    }
}
