package practice.enroll;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import practice.course.Course;
import practice.student.Student;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollRepository extends JpaRepository<Enroll, Long> {
    Optional<Enroll> findByStudentAndCourse(Student student, Course course);

    List<Enroll> findByCourseIsNotNullAndStudentIsNotNull();
//    List<Enroll> findCourseCodeCourseNameAndStudentName();

//    List<Enroll> findAll();
}
