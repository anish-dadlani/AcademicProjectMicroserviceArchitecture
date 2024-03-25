package com.student.controller;

import com.student.mapper.StudentMapper;
import com.student.model.dto.request.StudentRequestDto;
import com.student.model.dto.response.StudentResponseDto;
import com.student.model.entity.Student;
import com.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/students")
public class StudentController {
    private final StudentService studentService;
    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<StudentResponseDto>> getStudents() {
        List<StudentResponseDto> studentResponseDto = studentService.getStudents();
        return ResponseEntity.ok().body(studentResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDto> getStudentById(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        return ResponseEntity.ok(StudentMapper.toStudentDto(student));
    }

    @PostMapping
    public ResponseEntity<StudentResponseDto> addStudent(@RequestBody StudentRequestDto studentRequestDto) {
        return studentService.addStudent(studentRequestDto);
    }

    @DeleteMapping(path = "{studentId}")
    public ResponseEntity<String> deleteStudent(@PathVariable("studentId") Long studentId){
        return studentService.deleteStudent(studentId);
    }
    @PutMapping(path = "{studentId}")
    public ResponseEntity<String> updateStudent(@PathVariable("studentId") Long studentId,
                                                @RequestBody StudentRequestDto studentRequestDto) {
        return studentService.updateStudent(studentId, studentRequestDto);
    }
}
