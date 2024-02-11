package com.practice.academics.model.entity;

import com.practice.academics.model.entity.Course;
import jakarta.persistence.*;
import com.practice.academics.model.entity.Student;

import java.time.LocalDateTime;

@Entity
@Table
public class Enroll {
    @Id
    @SequenceGenerator(name = "enroll_sequence", sequenceName = "enroll_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "enroll_sequence")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;

    @Column(name = "registration_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime registrationDate;

    public Enroll() {
    }

    public Enroll(Long id, Student student, Course course) {
        this.id = id;
        this.student = student;
        this.course = course;
    }

    public Enroll(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.registrationDate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public String toString() {
        return "Enroll{" +
                "id=" + id +
                ", student=" + student +
                ", course=" + course +
                '}';
    }
}
