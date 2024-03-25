package com.enroll.model.dto.request;

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
    private Long student_id;
    @NonNull
    private Long course_id;
}