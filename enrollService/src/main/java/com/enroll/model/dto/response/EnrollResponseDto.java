package com.enroll.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollResponseDto {
    private Long id;
    private Long student_id;
    private Long course_id;
    private LocalDateTime registrationDate;
}
