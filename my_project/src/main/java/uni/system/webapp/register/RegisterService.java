package uni.system.webapp.register;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uni.system.webapp.tables.Student;
import uni.system.webapp.repositories.StudentRepository;

import java.sql.Date;

@Service
public class RegisterService {

    @Autowired
    StudentRepository studentRepository;

    public boolean registerUser(String ID, String password, String first_name, String last_name, String address, String phone_number, String email, String gender, Date date_of_birth, String stage) {
        if(studentRepository.existsById(ID)) {
            return false;
        }
        else{
            Student student = new Student(ID, password, first_name, last_name, address, phone_number, email, gender, 3000, 3000, date_of_birth, stage);
            studentRepository.save(student);
        }
        return true;
    }
}
