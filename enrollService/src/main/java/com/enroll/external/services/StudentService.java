package com.enroll.external.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;
import java.util.Optional;

@FeignClient(name = "STUDENT-SERVICE")
public interface StudentService {
    @GetMapping("/api/v1/students/{id}")
    Optional<Map<String, Object>> getStudentById(@PathVariable Long id);
}
