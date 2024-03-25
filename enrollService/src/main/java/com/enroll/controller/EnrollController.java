package com.enroll.controller;

import com.enroll.model.dto.request.EnrollRequestDto;
import com.enroll.service.EnrollService;
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
    public ResponseEntity<List<Map<String, Object>>> getStudentEnrollmentsByCourseId(@RequestParam(required = false) Long courseId) {
        List<Map<String, Object>> coursesWithStudents = enrollService.getStudentEnrollmentsByCourseId(courseId);
        return ResponseEntity.ok(coursesWithStudents);
    }

    @PostMapping
    public ResponseEntity<String> registerForCourse(@RequestBody EnrollRequestDto enrollRequestDto) {
//        enrollService.enrollStudentIntoCourse(enrollRequestDto);
        return ResponseEntity.ok("Student registered for the course successfully.");
    }
}