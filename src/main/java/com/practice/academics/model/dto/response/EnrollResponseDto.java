package com.practice.academics.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollResponseDto {
    private Long id;
    private StudentResponseDto student;
    private CourseResponseDto course;
    private LocalDateTime registrationDate;
}
