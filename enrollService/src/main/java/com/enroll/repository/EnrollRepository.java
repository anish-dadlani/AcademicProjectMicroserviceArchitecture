package com.enroll.repository;

import com.enroll.model.entity.Enroll;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollRepository extends JpaRepository<Enroll, Long> {
//    Optional<Enroll> findByStudentAndCourse(Student student, Course course);
//    List<Enroll> findByCourse_IdIsNotNullAndStudent_IdIsNotNull();
    @Query("SELECT e FROM Enroll e WHERE e.course_id = :courseId")
    List<Enroll> findByCourse_id(@Param("courseId") Long courseId);

    @Query("SELECT e FROM Enroll e WHERE e.student_id = :student_id AND e.course_id = :course_id")
    Optional<Enroll> findByStudent_idAndCourse_id(@Param("student_id") Long student_id, @Param("course_id") Long course_id);
}
