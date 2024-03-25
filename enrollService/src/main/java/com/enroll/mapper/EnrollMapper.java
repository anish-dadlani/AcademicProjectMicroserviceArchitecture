package com.enroll.mapper;

import com.enroll.model.dto.request.EnrollRequestDto;
import com.enroll.model.dto.response.EnrollResponseDto;
import com.enroll.model.entity.Enroll;
import org.springframework.beans.BeanUtils;

public class EnrollMapper {
    public EnrollMapper() {
    }

    public static Enroll toEnroll(EnrollRequestDto enrollRequestDto) {
        Enroll enroll = new Enroll();
        enroll.setCourse_id(enrollRequestDto.getCourse_id());
        enroll.setStudent_id(enrollRequestDto.getStudent_id());
//        enroll.setRegistrationDate(enrollRequestDto.getRegistrationDate());
        return enroll;
    }

    public static EnrollResponseDto toEnrollDto(Enroll enroll){
        EnrollResponseDto enrollResponseDto = new EnrollResponseDto();
        BeanUtils.copyProperties(enroll, enrollResponseDto);
        return enrollResponseDto;
    }
}
