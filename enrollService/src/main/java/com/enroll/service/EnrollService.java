package com.enroll.service;

import com.enroll.mapper.EnrollMapper;
import com.enroll.model.dto.request.EnrollRequestDto;
import com.enroll.model.entity.Enroll;
import com.enroll.repository.EnrollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class EnrollService {
    private final EnrollRepository enrollRepository;

    @Value("${apigateway.service.url}")
    private String apigatewayServiceUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    public EnrollService(EnrollRepository enrollRepository) {
        this.enrollRepository = enrollRepository;
    }

    public void enrollStudentIntoCourse(EnrollRequestDto enrollRequestDto) {
        Enroll enroll = EnrollMapper.toEnroll(enrollRequestDto);
        System.out.println("enroll"+enroll);
        // API Gateway URLs
        String studentApiUrl = apigatewayServiceUrl + "/students/";
        String courseApiUrl = apigatewayServiceUrl + "/courses/";

        Map<String, Object> studentInfo = fetchInfo(studentApiUrl, enrollRequestDto.getStudent_id());
        if (studentInfo == null) throw new IllegalArgumentException("Student not found with ID: " + enrollRequestDto.getStudent_id());

        Map<String, Object> courseInfo = fetchInfo(courseApiUrl, enrollRequestDto.getCourse_id());
        if (courseInfo == null) throw new IllegalArgumentException("Course not found with ID: " + enrollRequestDto.getCourse_id());

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

        // API Gateway URLs
        String studentApiUrl = apigatewayServiceUrl + "/students/";
        String courseApiUrl = apigatewayServiceUrl + "/courses/";

        try {
            // Validate course ID
            if (courseId == null || courseId <= 0) {
                throw new IllegalArgumentException("Invalid course ID. Please provide a valid course Id.");
            }

            // Fetch course information first
            Map<String, Object> courseInfo = fetchInfo(courseApiUrl, courseId);

            List<Enroll> enrolls = enrollRepository.findByCourse_id(courseId);

            for (Enroll enroll : enrolls) {
                // Fetch information of the enrolled student
                Map<String, Object> studentInfo = fetchInfo(studentApiUrl, enroll.getStudent_id());

                // Structure student enrollment information
                Map<String, Object> studentEnrollment = new HashMap<>();
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

    private Map<String, Object> fetchInfo(String apiUrl, Long Id) {
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                apiUrl + Id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<String, Object>>() {});

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new IllegalStateException("Failed to fetch response. Status code: " + response.getStatusCode());
        }
    }
}