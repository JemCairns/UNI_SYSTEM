package uni.system.webapp.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uni.system.webapp.repositories.ModuleRepository;
import uni.system.webapp.repositories.StudentRepository;
import uni.system.webapp.repositories.TopicRepository;
import uni.system.webapp.tables.Module;
import uni.system.webapp.tables.Topic;

import java.util.List;

@Service
public class LoginService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    TopicRepository topicRepository;

    public boolean validateUser(String ID, String pass) {
        if(studentRepository.existsById(ID)) {
            return studentRepository.getOne(ID).getPassword().equals(pass);
        }
        return false;
    }

    public List<Module> getAllModules() {
        for(Module m : moduleRepository.findAll()){
            System.out.println(m.getID());
        }
        return moduleRepository.findAll();
    }
    public List<Topic> getAllTopics() {
        for(Topic t : topicRepository.findAll()){
            System.out.println(t.getTitle());
        }
        return topicRepository.findAll();
    }
}
