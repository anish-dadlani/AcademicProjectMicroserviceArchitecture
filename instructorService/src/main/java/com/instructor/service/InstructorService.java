package com.instructor.service;

import com.instructor.mapper.InstructorMapper;
import com.instructor.model.dto.request.InstructorRequestDto;
import com.instructor.model.dto.response.InstructorResponseDto;
import com.instructor.model.entity.Instructor;
import com.instructor.repository.InstructorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InstructorService {
    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private InstructorMapper instructorMapper;

    public InstructorResponseDto createInstructor(InstructorRequestDto requestDTO) {
        Instructor instructor = instructorMapper.toInstructor(requestDTO);
        Instructor savedInstructor = instructorRepository.save(instructor);
        return instructorMapper.toInstructorDto(savedInstructor);
    }

    public List<InstructorResponseDto> getAllInstructors() {
        List<Instructor> instructors = instructorRepository.findAll();
        return instructors.stream()
                .map(instructorMapper::toInstructorDto)
                .collect(Collectors.toList());
    }

    public InstructorResponseDto getInstructorById(Long id) {
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Instructor not found with id: " + id));
        return instructorMapper.toInstructorDto(instructor);
    }

    public InstructorResponseDto updateInstructor(Long id, InstructorRequestDto requestDTO) {
        Instructor existingInstructor = instructorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Instructor not found with id: " + id));
        Instructor updatedInstructor = instructorMapper.toInstructor(requestDTO);
        updatedInstructor.setId(existingInstructor.getId());
        Instructor savedInstructor = instructorRepository.save(updatedInstructor);
        return instructorMapper.toInstructorDto(savedInstructor);
    }

    public void deleteInstructor(Long id) {
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Instructor not found with id: " + id));
        instructorRepository.delete(instructor);
    }
}
