package com.practice.academics.controller;

import com.practice.academics.service.CourseService;
import com.practice.academics.model.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Course> getCourses(){
        return courseService.getCourses();
    }

    @PostMapping
    public void addCourse(@RequestBody Course course){ courseService.addCourse(course); }

    @DeleteMapping(path = "{courseId}")
    public void deleteCourse(@PathVariable("courseId") Long courseId){
        courseService.deleteCourse(courseId);
    }

    @PutMapping(path = "{courseId}")
    public void updateCourse(@PathVariable("courseId") Long courseId,
                                @RequestParam(required = false) String courseName,
                                @RequestParam(required = false) String courseCode){
        courseService.updateCourse(courseId, courseName, courseCode);
    }
}
