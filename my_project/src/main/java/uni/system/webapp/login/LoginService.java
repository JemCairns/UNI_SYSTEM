package uni.system.webapp.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uni.system.webapp.repositories.*;

@Service
public class LoginService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    StaffRepository staffRepository;

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    TopicRegistrationRepository topicRegistrationRepository;

    public String validateUser(String ID, String pass) {
        if(studentRepository.existsById(ID+"STU")) {
            if(studentRepository.getOne(ID+"STU").getPassword().equals(pass)) {
                return "STU";
            }
        }
        else if(staffRepository.existsById(ID+"STA")){
            if(staffRepository.getOne(ID+"STA").getPassword().equals(pass)){
                return "STA";
            }
        }
        return "";
    }
}
