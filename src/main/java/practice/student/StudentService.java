package practice.student;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

//    @Autowired
    public List<Student> getStudents(){
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) { //addStudent
        Optional<Student> studentByEmail = studentRepository.findStudentByEmail(student.getEmail());
        if(studentByEmail.isPresent()){
            throw new IllegalStateException("Email Taken");
        }
        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        boolean studentExists = studentRepository.existsById(studentId);
        if(!studentExists){
            throw new IllegalStateException("Student with Id: " + studentId + " does not exists");
        }
        studentRepository.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(Long studentId, String name, String email) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException("Student with Id: " + studentId + " does not exists"));
        if(name != null && !name.isEmpty() && !Objects.equals(student.getName(), name)){
            student.setName(name);
        }
        if(email != null && !email.isEmpty() && !Objects.equals(student.getEmail(), email)){
            Optional<Student> studentByEmail = studentRepository.findStudentByEmail(student.getEmail());
            if(studentByEmail.isPresent()){
                throw new IllegalStateException("Email Taken");
            }
            student.setEmail(email);
        }
    }
}
