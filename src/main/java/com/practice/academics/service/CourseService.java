package com.practice.academics.service;

import com.practice.academics.model.entity.Course;
import com.practice.academics.repository.CourseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getCourses(){
        return courseRepository.findAll();
    }

    public void addCourse(Course course) {
        Optional<Course> CourseByCourseCode = courseRepository.findCourseByCourseCode(course.getCourseCode());
        if(CourseByCourseCode.isPresent()){
            throw new IllegalStateException("Course Code Taken");
        }
        courseRepository.save(course);
    }

    public void deleteCourse(Long courseId) {
        boolean courseExists = courseRepository.existsById(courseId);
        if(!courseExists){
            throw new IllegalStateException("Course with Id: " + courseId + " does not exists");
        }
        courseRepository.deleteById(courseId);
    }

    @Transactional
    public void updateCourse(Long courseId, String courseName, String courseCode) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalStateException("Course with Id: " + courseId + " does not exists"));
        if(courseName != null && !courseName.isEmpty() && !Objects.equals(course.getCourseName(), courseName)){
            course.setCourseName(courseName);
        }
        if(courseCode != null && !courseCode.isEmpty() && !Objects.equals(course.getCourseCode(), courseCode)){
            Optional<Course> courseByCourseCode = courseRepository.findCourseByCourseCode(course.getCourseCode());
            if(courseByCourseCode.isPresent()){
                throw new IllegalStateException("Course Code Taken");
            }
            course.setCourseCode(courseCode);
        }
    }
}
