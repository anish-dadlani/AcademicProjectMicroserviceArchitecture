package com.enroll.external.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;
import java.util.Optional;

@FeignClient(name = "STUDENT-SERVICE")
public interface StudentService {
    @GetMapping("/students/{id}")
    Optional<Map<String, Object>> getStudentById(Long id);
}
