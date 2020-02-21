package uni.system.webapp.welcome;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uni.system.webapp.repositories.ModuleRepository;
import uni.system.webapp.repositories.TopicRegistrationRepository;
import uni.system.webapp.repositories.TopicRepository;
import uni.system.webapp.tables.Module;
import uni.system.webapp.tables.Topic;
import uni.system.webapp.tables.TopicRegistration;

import java.util.List;

@Service
public class WelcomeService {

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    TopicRegistrationRepository topicRegistrationRepository;

    public List<Module> getAllModules() {
//        for(Module m : moduleRepository.findAll()){
//            System.out.println(m.getID());
//        }
        return moduleRepository.findAll();
    }
    public List<Topic> getAllTopics() {
//        for(Topic t : topicRepository.findAll()){
//            System.out.println(t.getTopic_ID());
//        }
        return topicRepository.findAll();
    }
    public List<TopicRegistration> getAllTopicRegistrations() {
//        for(TopicRegistration tr : topicRegistrationRepository.findAll()){
//            System.out.println(tr.getReg_ID());
//            System.out.println(tr.getModule_ID());
//            System.out.println(tr.getTopic_ID());
//        }
        return topicRegistrationRepository.findAll();
    }
}
