package com.practice.academics.service;

import com.practice.academics.mapper.EnrollMapper;
import com.practice.academics.model.dto.request.EnrollRequestDto;
import com.practice.academics.model.entity.Course;
import com.practice.academics.model.entity.Enroll;
import com.practice.academics.repository.CourseRepository;
import com.practice.academics.repository.EnrollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.practice.academics.model.entity.Student;
import com.practice.academics.repository.StudentRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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

    public void enrollStudentIntoCourse(EnrollRequestDto enrollRequestDto) {
        Enroll enroll = EnrollMapper.toEnroll(enrollRequestDto);

        Student student = enroll.getStudent();
        if (studentRepository.findById(student.getId()).isEmpty()) {
            throw new IllegalArgumentException("Student not found with ID: " + student.getId());
        }

        Course course = enroll.getCourse();
        if (courseRepository.findById(course.getId()).isEmpty()) {
            throw new IllegalArgumentException("Course not found with ID: " + course.getId());
        }

        if (enrollRepository.findByStudentAndCourse(student, course).isPresent()) {
            throw new IllegalStateException("Student is already registered for the course.");
        }

        enrollRepository.save(enroll);
    }

    public List<Map<String, Object>> getCoursesWithStudents(Long courseId) {
        List<Map<String, Object>> coursesWithStudents = new ArrayList<>();

        List<Course> courses;
        if (courseId != null) {
            courses = Collections.singletonList(courseRepository.findById(courseId)
                    .orElseThrow(() -> new IllegalArgumentException("Course not found with ID: " + courseId)));
        } else {
            courses = courseRepository.findAll();
        }

        for (Course course : courses) {
            List<Enroll> enrollments = enrollRepository.findByCourseId(course.getId());
            List<Map<String, Object>> studentsList = new ArrayList<>();

            for (Enroll enroll : enrollments) {
                Map<String, Object> studentInfo = new HashMap<>();
                studentInfo.put("id", enroll.getStudent().getId());
                studentInfo.put("name", enroll.getStudent().getName());
                studentInfo.put("registrationDate", enroll.getRegistrationDate());
                studentsList.add(studentInfo);
            }

            Map<String, Object> courseWithStudents = new HashMap<>();
            courseWithStudents.put("courseName", course.getCourseName());
            courseWithStudents.put("studentsRegistered", studentsList);

            coursesWithStudents.add(courseWithStudents);
        }

        return coursesWithStudents;
    }
}