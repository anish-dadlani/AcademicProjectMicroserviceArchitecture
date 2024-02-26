package com.practice.academics.mapper;

import com.practice.academics.model.dto.request.StudentRequestDto;
import com.practice.academics.model.dto.response.StudentResponseDto;
import com.practice.academics.model.entity.Student;
import org.springframework.beans.BeanUtils;

public class StudentMapper
{
    public StudentMapper() {
    }

    public static Student toStudent(StudentRequestDto studentRequestDto){
        Student student = new Student();
        student.setName(studentRequestDto.getName());
        student.setEmail(studentRequestDto.getEmail());
        student.setDob(studentRequestDto.getDob());
        return student;
    }

    public static StudentResponseDto toStudentDto(Student student){
        StudentResponseDto studentResponseDto = new StudentResponseDto();
        BeanUtils.copyProperties(student, studentResponseDto);
        return studentResponseDto;
    }
}
