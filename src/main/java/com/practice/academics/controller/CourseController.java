package com.practice.academics.controller;

import com.practice.academics.mapper.CourseMapper;
import com.practice.academics.model.dto.request.CourseRequestDto;
import com.practice.academics.model.dto.response.CourseResponseDto;
import com.practice.academics.service.CourseService;
import com.practice.academics.model.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/courses")
public class CourseController {
    private final CourseService courseService;
    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<CourseResponseDto>> getCourses(){
        List<CourseResponseDto> courses = courseService.getCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDto> getCourseById(@PathVariable Long id) {
        Course course = courseService.getCourseById(id);
        return ResponseEntity.ok(CourseMapper.toCourseDto(course));
    }

    @PostMapping
    public ResponseEntity<CourseResponseDto> addCourse(@RequestBody CourseRequestDto courseRequestDto) {
        return courseService.addCourse(courseRequestDto);
    }

    @DeleteMapping(path = "{courseId}")
    public ResponseEntity<String> deleteCourse(@PathVariable("courseId") Long courseId){
        return courseService.deleteCourse(courseId);
    }

    @PutMapping(path = "{courseId}")
    public ResponseEntity<String> updateCourse(@PathVariable("courseId") Long courseId,
                                             @RequestBody CourseRequestDto courseRequestDto) {
        return courseService.updateCourse(courseId, courseRequestDto);
    }
}
