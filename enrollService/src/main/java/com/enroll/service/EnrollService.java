package com.enroll.service;

import com.enroll.external.services.CourseService;
import com.enroll.external.services.StudentService;
import com.enroll.mapper.EnrollMapper;
import com.enroll.model.dto.request.EnrollRequestDto;
import com.enroll.model.entity.Enroll;
import com.enroll.repository.EnrollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EnrollService {
    private final EnrollRepository enrollRepository;
    private final CourseService courseService;
    private final StudentService studentService;

    @Autowired
    public EnrollService(EnrollRepository enrollRepository, CourseService courseService, StudentService studentService) {
        this.enrollRepository = enrollRepository;
        this.courseService = courseService;
        this.studentService = studentService;
    }

    public void enrollStudentIntoCourse(EnrollRequestDto enrollRequestDto) {
        Enroll enroll = EnrollMapper.toEnroll(enrollRequestDto);

        Map<String, Object> studentInfo = studentService.getStudentById(enrollRequestDto.getStudent_id())
            .orElseThrow(() -> new IllegalStateException("Student not found with id: " + enrollRequestDto.getStudent_id()));

        Map<String, Object> courseInfo = courseService.getCourseById(enrollRequestDto.getCourse_id())
            .orElseThrow(() -> new IllegalStateException("Course not found with id: " + enrollRequestDto.getCourse_id()));

        if (enrollRepository.findByStudent_idAndCourse_id(
                enrollRequestDto.getStudent_id(),
                enrollRequestDto.getCourse_id()).isPresent()) {
            throw new IllegalStateException("Student is already registered for the course.");
        }

        enrollRepository.save(enroll);
    }

    public List<Map<String, Object>> getStudentEnrollmentsByCourseId(Long courseId) {
        List<Map<String, Object>> studentEnrollments = new ArrayList<>();
        List<Map<String, Object>> students = new ArrayList<>();

        try {
            if (courseId == null || courseId <= 0) {
                throw new IllegalArgumentException("Invalid course ID. Please provide a valid course Id.");
            }

            Map<String, Object> courseInfo = courseService.getCourseById(courseId)
                    .orElseThrow(() -> new IllegalStateException("Course not found with id: " + courseId));

            List<Enroll> enrolls = enrollRepository.findByCourse_id(courseId);

            for (Enroll enroll : enrolls) {
                // Fetch information of the enrolled student
                Map<String, Object> studentInfo = studentService.getStudentById(enroll.getStudent_id())
                        .orElseThrow(() -> new IllegalStateException("Student not found with id: " + enroll.getStudent_id()));

                // Structure student enrollment information
                studentInfo.put("registrationDate", enroll.getRegistrationDate());
                students.add(studentInfo);
            }

            // Structure final response with course info once and list of students
            Map<String, Object> finalResponse = new HashMap<>();
            finalResponse.put("course_info", courseInfo);
            finalResponse.put("students", students);

            studentEnrollments.add(finalResponse);
        } catch (ClassCastException e) {
            System.out.println("Unexpected response format: " + e.getMessage());
        }
        return studentEnrollments;
    }
}