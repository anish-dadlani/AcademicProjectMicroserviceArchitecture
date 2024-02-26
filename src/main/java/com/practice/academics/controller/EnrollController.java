package com.practice.academics.controller;

import com.practice.academics.model.dto.request.EnrollRequestDto;
import com.practice.academics.service.EnrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/v1/enroll")
public class EnrollController {
    private final EnrollService enrollService;
    @Autowired
    public EnrollController(EnrollService enrollService) {
        this.enrollService = enrollService;
    }

    @GetMapping("/courses")
    public ResponseEntity<List<Map<String, Object>>> getCoursesWithStudents(@RequestParam(required = false) Long courseId) {
        List<Map<String, Object>> coursesWithStudents = enrollService.getCoursesWithStudents(courseId);
        return ResponseEntity.ok(coursesWithStudents);
    }

    @PostMapping
    public ResponseEntity<String> registerForCourse(@RequestBody EnrollRequestDto enrollRequestDto) {
        enrollService.enrollStudentIntoCourse(enrollRequestDto);
        return ResponseEntity.ok("Student registered for the course successfully.");
    }
}