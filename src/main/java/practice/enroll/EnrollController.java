package practice.enroll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/enroll")
public class EnrollController {
    private final EnrollService enrollService;
    @Autowired
    public EnrollController(EnrollService enrollService) {
        this.enrollService = enrollService;
    }

    @GetMapping
    public ResponseEntity<List<Enroll>> getCoursesWithStudents() {
        List<Enroll> courseStudentList = enrollService.getCoursesWithStudents();
        return ResponseEntity.ok(courseStudentList);
    }

    @PostMapping
    public ResponseEntity<String> registerForCourse(@RequestParam Long studentId, @RequestParam Long courseId) {
        enrollService.enrollStudentIntoCourse(studentId, courseId);
        return ResponseEntity.ok("Registration successful.");
    }
}