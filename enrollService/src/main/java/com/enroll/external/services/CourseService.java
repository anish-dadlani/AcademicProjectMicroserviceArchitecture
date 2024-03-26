package com.enroll.external.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;
import java.util.Optional;

@FeignClient( name = "COURSE-SERVICE")
public interface CourseService {
    @GetMapping("/api/v1/courses/{id}")
    Optional<Map<String, Object>> getCourseById(@PathVariable Long id);
}
