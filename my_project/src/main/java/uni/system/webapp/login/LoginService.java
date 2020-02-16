package uni.system.webapp.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uni.system.webapp.repositories.StudentRepository;

@Service
public class LoginService {

    @Autowired
    StudentRepository studentRepository;

    public boolean validateUser(String ID, String pass) {
        if(studentRepository.existsById(ID)) {
            return studentRepository.getOne(ID).getPassword().equals(pass);
        }
        return false;
    }
}