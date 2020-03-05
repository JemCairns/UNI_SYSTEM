package uni.system.webapp.welcome;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uni.system.webapp.repositories.*;
import uni.system.webapp.tables.*;
import uni.system.webapp.tables.Module;
import java.util.List;

@Service
public class WelcomeService {

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    TopicRegistrationRepository topicRegistrationRepository;

    @Autowired
    StaffRepository staffRepository;

    public List<Module> getAllModules() {
        return moduleRepository.findAll();
    }

    public String getUserName(String ID){
        if(ID.endsWith("STU")){
            return studentRepository.getOne(ID).getFirst_name();
        }
        else if(ID.endsWith("STA")){
            return staffRepository.getOne(ID).getFirst_name();
        }
        return "";
    }
    public List<Staff> getAllStaff() { return staffRepository.findAll(); }

    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }
    public List<TopicRegistration> getAllTopicRegistrations() {
        return topicRegistrationRepository.findAll();
    }
}
