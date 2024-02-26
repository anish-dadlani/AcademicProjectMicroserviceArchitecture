package com.practice.academics.mapper;

import com.practice.academics.model.dto.request.EnrollRequestDto;
import com.practice.academics.model.dto.response.CourseResponseDto;
import com.practice.academics.model.dto.response.EnrollResponseDto;
import com.practice.academics.model.dto.response.StudentResponseDto;
import com.practice.academics.model.entity.Course;
import com.practice.academics.model.entity.Enroll;
import com.practice.academics.model.entity.Student;
import org.springframework.beans.BeanUtils;

public class EnrollMapper {
    public EnrollMapper() {
    }

    public static Enroll toEnroll(EnrollRequestDto enrollRequestDto) {
        Enroll enroll = new Enroll();
        StudentResponseDto studentResponseDto = enrollRequestDto.getStudent();
        CourseResponseDto courseResponseDto = enrollRequestDto.getCourse();

        // Assuming StudentResponseDto contains id field
        Student student = new Student();
        student.setId(studentResponseDto.getId());
        enroll.setStudent(student);

        // Assuming CourseResponseDto contains id field
        Course course = new Course();
        course.setId(courseResponseDto.getId());
        enroll.setCourse(course);

//        enroll.setRegistrationDate(enrollRequestDto.getRegistrationDate());
        return enroll;
    }

    public static EnrollResponseDto toEnrollDto(Enroll enroll){
        EnrollResponseDto enrollResponseDto = new EnrollResponseDto();
        BeanUtils.copyProperties(enroll, enrollResponseDto);
        return enrollResponseDto;
    }
}
