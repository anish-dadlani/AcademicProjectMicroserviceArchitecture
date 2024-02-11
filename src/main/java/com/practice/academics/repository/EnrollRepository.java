package com.practice.academics.repository;

import com.practice.academics.model.entity.Course;
import com.practice.academics.model.entity.Enroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.practice.academics.model.entity.Student;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollRepository extends JpaRepository<Enroll, Long> {
    Optional<Enroll> findByStudentAndCourse(Student student, Course course);

    List<Enroll> findByCourseIsNotNullAndStudentIsNotNull();
//    List<Enroll> findCourseCodeCourseNameAndStudentName();

//    List<Enroll> findAll();
}
