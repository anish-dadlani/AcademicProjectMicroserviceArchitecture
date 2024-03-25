package com.enroll.external.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;
import java.util.Optional;

@FeignClient(name = "COURSE-SERVICE")
public interface CourseService {
    @GetMapping("/courses/{id}")
    Optional<Map<String, Object>> getCourseById(Long id);
}
