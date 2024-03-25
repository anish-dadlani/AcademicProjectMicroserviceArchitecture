package com.instructor.controller;

import com.instructor.model.dto.request.InstructorRequestDto;
import com.instructor.model.dto.response.InstructorResponseDto;
import com.instructor.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/instructors")
public class InstructorController {
    @Autowired
    private InstructorService instructorService;

    @PostMapping
    public ResponseEntity<InstructorResponseDto> createInstructor(@RequestBody InstructorRequestDto requestDTO) {
        InstructorResponseDto responseDTO = instructorService.createInstructor(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<InstructorResponseDto>> getAllInstructors() {
        List<InstructorResponseDto> instructors = instructorService.getAllInstructors();
        return ResponseEntity.ok(instructors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstructorResponseDto> getInstructorById(@PathVariable Long id) {
        InstructorResponseDto instructor = instructorService.getInstructorById(id);
        return ResponseEntity.ok(instructor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InstructorResponseDto> updateInstructor(@PathVariable Long id,
                                                                  @RequestBody InstructorRequestDto requestDTO) {
        InstructorResponseDto updatedInstructor = instructorService.updateInstructor(id, requestDTO);
        return ResponseEntity.ok(updatedInstructor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstructor(@PathVariable Long id) {
        instructorService.deleteInstructor(id);
        return ResponseEntity.noContent().build();
    }
}

