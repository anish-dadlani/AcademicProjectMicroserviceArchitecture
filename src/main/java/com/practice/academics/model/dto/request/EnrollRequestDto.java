package com.practice.academics.model.dto.request;

import com.practice.academics.model.dto.response.CourseResponseDto;
import com.practice.academics.model.dto.response.StudentResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollRequestDto {
    @NonNull
    private String name;
    @NonNull
    private StudentResponseDto student;
    @NonNull
    private CourseResponseDto course;
}
