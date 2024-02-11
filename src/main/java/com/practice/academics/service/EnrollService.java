package com.practice.academics.service;

import com.practice.academics.model.entity.Course;
import com.practice.academics.model.entity.Enroll;
import com.practice.academics.repository.CourseRepository;
import com.practice.academics.repository.EnrollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.practice.academics.model.entity.Student;
import com.practice.academics.repository.StudentRepository;
import java.util.List;

@Service
public class EnrollService {
    private final EnrollRepository enrollRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public EnrollService(EnrollRepository enrollRepository, StudentRepository studentRepository,
                         CourseRepository courseRepository) {
        this.enrollRepository = enrollRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public List<Enroll> getCoursesWithStudents() {
        return enrollRepository.findByCourseIsNotNullAndStudentIsNotNull();
    }

    public void enrollStudentIntoCourse(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + studentId));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found with ID: " + courseId));

        if (enrollRepository.findByStudentAndCourse(student, course).isPresent()) {
            throw new IllegalStateException("Student is already registered for the course.");
        }

        Enroll enroll = new Enroll(student, course);
        enrollRepository.save(enroll);
    }
}