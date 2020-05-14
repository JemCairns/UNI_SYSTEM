package uni.system.webapp.register;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uni.system.webapp.repositories.StudentRepository;
import uni.system.webapp.repositories.UserRepository;
import uni.system.webapp.security.PasswordUtils;
import uni.system.webapp.tables.Student;
import uni.system.webapp.tables.User;
import java.util.Date;

@Service
public class RegisterService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    UserRepository userRepository;

    public boolean registerUser(String ID, String password, String first_name, String last_name, String address, String phone_number, String email, String gender, Date date_of_birth, String stage) {
        for(Student student : studentRepository.findAll()){
            if(student.getID().equals(ID) || student.getEmail().equals(email)){
                return false;
            }
        }

        String salt = PasswordUtils.getSalt(30);
        String securePassword = PasswordUtils.generateSecurePassword(password, salt);
        Student student = new Student(ID, securePassword, first_name, last_name, address, phone_number, email, gender, 3000, 0, date_of_birth, stage, salt);
        User user = new User(ID, password, "STUDENT");
        studentRepository.save(student);
        userRepository.save(user);
        return true;
    }
}
