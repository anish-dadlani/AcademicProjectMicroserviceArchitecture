package com.practice.academics.service;

import com.practice.academics.mapper.CourseMapper;
import com.practice.academics.model.dto.request.CourseRequestDto;
import com.practice.academics.model.dto.response.CourseResponseDto;
import com.practice.academics.model.entity.Course;
import com.practice.academics.repository.CourseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<CourseResponseDto> getCourses(){
        List<Course> courses = courseRepository.findAll();
        return courses.stream()
                .map(CourseMapper::toCourseDto)
                .collect(Collectors.toList());
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Course not found with id: " + id));
    }

    public ResponseEntity<CourseResponseDto> addCourse(CourseRequestDto courseRequestDto) {
        Optional<Course> course = courseRepository.findCourseByCourseCode(courseRequestDto.getCourseCode());
        if (course.isPresent()) {
            throw new IllegalStateException("Course Code is Already Used");
        }
        Course courseDto = CourseMapper.toCourse(courseRequestDto);
        Course savedCourse = courseRepository.save(courseDto);
        CourseResponseDto courseResponseDto = CourseMapper.toCourseDto(savedCourse);
        return ResponseEntity.ok(courseResponseDto);
    }

    public ResponseEntity<String> deleteCourse(Long courseId) {
        boolean courseExists = courseRepository.existsById(courseId);
        if (!courseExists) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Course with Id: " + courseId + " does not exist");
        }
        courseRepository.deleteById(courseId);
        return ResponseEntity.ok("Course with Id: " + courseId + " deleted successfully");
    }

    @Transactional
    public ResponseEntity<String> updateCourse(Long courseId, CourseRequestDto courseRequestDto) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalStateException("Course with Id: " + courseId + " does not exist"));

        if (courseRequestDto.getCourseName() != null && !courseRequestDto.getCourseName().isEmpty() &&
                !Objects.equals(course.getCourseName(), courseRequestDto.getCourseName())) {
            course.setCourseName(courseRequestDto.getCourseName());
        }

        if (courseRequestDto.getCourseCode() != null && !courseRequestDto.getCourseCode().isEmpty() &&
                !Objects.equals(course.getCourseCode(), courseRequestDto.getCourseCode())) {
            Optional<Course> courseCodeExists = courseRepository.findCourseByCourseCode(courseRequestDto.getCourseCode());
            if (courseCodeExists.isPresent() && Objects.equals(courseCodeExists.get().getId(), courseId)) {
                throw new IllegalStateException("Course is already being used for another code");
            }
            course.setCourseCode(courseRequestDto.getCourseCode());
        }

        courseRepository.save(course);
        return ResponseEntity.ok("Student with Id: " + courseId + " updated successfully");
    }
}
