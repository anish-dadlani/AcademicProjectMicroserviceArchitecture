package com.instructor.mapper;

import com.instructor.model.dto.request.InstructorRequestDto;
import com.instructor.model.dto.response.InstructorResponseDto;
import com.instructor.model.entity.Instructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class InstructorMapper {

    public Instructor toInstructor(InstructorRequestDto requestDTO) {
        Instructor instructor = new Instructor();
        instructor.setName(requestDTO.getName());
        instructor.setEmail(requestDTO.getEmail());
        instructor.setPassword(requestDTO.getPassword());
        return instructor;
    }

    public InstructorResponseDto toInstructorDto(Instructor instructor) {
        InstructorResponseDto instructorResponseDto = new InstructorResponseDto();
        BeanUtils.copyProperties(instructor, instructorResponseDto);
        return instructorResponseDto;
    }
}

