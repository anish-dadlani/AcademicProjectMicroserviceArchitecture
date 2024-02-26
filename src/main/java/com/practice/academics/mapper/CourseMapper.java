package com.practice.academics.mapper;

import com.practice.academics.model.dto.request.CourseRequestDto;
import com.practice.academics.model.dto.response.CourseResponseDto;
import com.practice.academics.model.entity.Course;
import org.springframework.beans.BeanUtils;

public class CourseMapper
{
    public CourseMapper() {
    }

    public static Course toCourse(CourseRequestDto courseRequestDto){
        Course course = new Course();
        course.setCourseName(courseRequestDto.getCourseName());
        course.setCourseCode(courseRequestDto.getCourseCode());
        return course;
    }

    public static CourseResponseDto toCourseDto(Course course){
        CourseResponseDto courseResponseDto = new CourseResponseDto();
        BeanUtils.copyProperties(course, courseResponseDto);
        return courseResponseDto;
    }
}
