package com.practice.academics.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequestDto {
    @NonNull
    private String name;
    @NonNull
    private String email;
    @NonNull
    private LocalDate dob;
}
