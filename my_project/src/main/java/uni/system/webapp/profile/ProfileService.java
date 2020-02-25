package uni.system.webapp.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uni.system.webapp.repositories.StudentRepository;
import uni.system.webapp.tables.Student;

import java.util.List;

@Service
public class ProfileService {

    @Autowired
    StudentRepository studentRepository;

    public Student getProfile(String ID) {
        Student student = studentRepository.getOne(ID+"STU");
        return student;
    }

    public List<Student> getAllStudents() { return studentRepository.findAll(); }

}
