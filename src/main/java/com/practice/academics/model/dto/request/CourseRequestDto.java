package com.practice.academics.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseRequestDto {
    @NonNull
    private String courseName;
    @NonNull
    private String courseCode;
}
