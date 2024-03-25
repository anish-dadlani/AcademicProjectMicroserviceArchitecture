package com.enroll.service;

import com.enroll.model.entity.Enroll;
import com.enroll.repository.EnrollRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    public EnrollService(EnrollRepository enrollRepository) {
        this.enrollRepository = enrollRepository;
    }

//    public void enrollStudentIntoCourse(EnrollRequestDto enrollRequestDto) {
//        Enroll enroll = EnrollMapper.toEnroll(enrollRequestDto);
//
//        Student student = enroll.getStudent();
//        if (studentRepository.findById(student.getId()).isEmpty()) {
//            throw new IllegalArgumentException("Student not found with ID: " + student.getId());
//        }
//
//        Course course = enroll.getCourse();
//        if (courseRepository.findById(course.getId()).isEmpty()) {
//            throw new IllegalArgumentException("Course not found with ID: " + course.getId());
//        }
//
//        if (enrollRepository.findByStudentAndCourse(student, course).isPresent()) {
//            throw new IllegalStateException("Student is already registered for the course.");
//        }
//
//        enrollRepository.save(enroll);
//    }

//    public List<Map<String, Object>> getCoursesWithStudents(Long courseId) {
//        List<Map<String, Object>> coursesWithStudents = new ArrayList<>();
//
//        List<Map<String, Object>> courses;
//        if (courseId != null) {
//            return restTemplate.getForObject("http://localhost:8084/api/v1/courses/"+courseId, ArrayList.class);
////            courses = Collections.singletonList(courseClient.getCourseById(courseId)
////                    .orElseThrow(() -> new IllegalArgumentException("Course not found with ID: " + courseId)));
//        } else throw new UnsupportedOperationException("Fetching all courses without ID is not supported");

//        for (Map<String, Object> courseMap : courses) {
//            List<Enroll> enrollments = enrollRepository.findByCourse_id((Long) courseMap.get("id"));
//            List<Map<String, Object>> studentsList = new ArrayList<>();
//
//            for (Enroll enroll : enrollments) {
//                Map<String, Object> studentInfo = new HashMap<>();
////                studentInfo.put("id", enroll.getStudent().getId());
////                studentInfo.put("name", enroll.getStudent().getName());
//                studentInfo.put("registrationDate", enroll.getRegistrationDate());
//                studentsList.add(studentInfo);
//            }
//
//            Map<String, Object> courseWithStudents = new HashMap<>();
////            courseWithStudents.put("courseName", course.getCourseName());
//            courseWithStudents.put("studentsRegistered", studentsList);
//
//            coursesWithStudents.add(courseWithStudents);
//        }

//        return forObject;
//    }

    public List<Map<String, Object>> getCoursesWithStudents(Long courseId) {
        List<Map<String, Object>> coursesWithStudents = new ArrayList<>();

        if (courseId != null) {
            ResponseEntity<List<Map<String, Object>>> responseEntity = restTemplate.exchange(
                    "http://localhost:8084/api/v1/courses/" + courseId,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Map<String, Object>>>() {});

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                coursesWithStudents = responseEntity.getBody();
            } else {
                throw new IllegalStateException("Failed to fetch courses with students. Status code: " + responseEntity.getStatusCodeValue());
            }
        } else {
            throw new UnsupportedOperationException("Fetching all courses without ID is not supported");
        }

        return coursesWithStudents;
    }
}