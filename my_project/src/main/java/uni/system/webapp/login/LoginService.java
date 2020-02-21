package uni.system.webapp.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uni.system.webapp.repositories.ModuleRepository;
import uni.system.webapp.repositories.StudentRepository;
import uni.system.webapp.repositories.TopicRegistrationRepository;
import uni.system.webapp.repositories.TopicRepository;
import uni.system.webapp.tables.Module;
import uni.system.webapp.tables.Topic;
import uni.system.webapp.tables.TopicRegistration;

import java.util.List;

@Service
public class LoginService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    TopicRegistrationRepository topicRegistrationRepository;

    public boolean validateUser(String ID, String pass) {
        if(studentRepository.existsById(ID+"STU")) {
            return studentRepository.getOne(ID+"STU").getPassword().equals(pass);
        }
        return false;
    }
}
