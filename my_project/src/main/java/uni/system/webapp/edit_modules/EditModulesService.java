package uni.system.webapp.edit_modules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uni.system.webapp.repositories.*;
import uni.system.webapp.tables.Module;
import uni.system.webapp.tables.ModuleRegistration;
import uni.system.webapp.tables.Topic;
import uni.system.webapp.tables.TopicRegistration;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class EditModulesService {

    @Autowired
    StaffRepository staffRepository;

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    ModuleRegistrationRepository registrationRepository;

    @Autowired
    TopicRegistrationRepository topicRegistrationRepository;

    @Autowired
    TopicRepository topicRepository;

    public String getUserName(String ID){
        return staffRepository.getOne(ID).getFirst_name();
    }

    public Module getModule(String ID){
        return moduleRepository.getOne(ID);
    }

    public void updateModule(Module module){
        moduleRepository.save(module);
    }

    public boolean saveModule(Module newModule){
        boolean alreadyExists=false;
        for(Module module : moduleRepository.findAll()) {
            if(module.getID().equals(newModule.getID())){
                alreadyExists=true;
            }
        }
        if(!alreadyExists){
            moduleRepository.save(newModule);
        }

        return alreadyExists;
    }

    public List<Topic> getAllTopics(){
        return topicRepository.findAll();
    }
    public Topic getTopic(int ID){
        return topicRepository.getOne(ID);
    }

    public ArrayList<Topic> getRegisteredTopics(String moduleID){
        ArrayList<Topic> topics = new ArrayList<>();
        for(TopicRegistration registration : topicRegistrationRepository.findAll()){
            if(registration.getModule_ID().equals(moduleID)){
                topics.add(getTopic(registration.getTopic_ID()));
            }
        }
        return topics;
    }
    public List<Topic> getNotRegisteredTopics(String moduleID){
        List<Topic> registeredTopics = getRegisteredTopics(moduleID);
        List<Topic> allTopics = getAllTopics();
        List<Topic> topicsNotRegs = new LinkedList<>();

        for(Topic topic: allTopics){
            boolean included=false;
            for(Topic topicRegistration : registeredTopics){
                if(topic.getTopic_ID() == topicRegistration.getTopic_ID()){
                    included=true;
                    break;
                }
            }
            if(!included){
                topicsNotRegs.add(topic);
            }
        }
        return topicsNotRegs;
    }


    public void registerModuleForTopic(String moduleID, int topicID){
//        TopicRegistration topicRegistration = new TopicRegistration();
//        topicRegistration.setModule_ID(moduleID);
//        topicRegistration.setTopic_ID(topicID);
        topicRegistrationRepository.save(new TopicRegistration(moduleID, topicID));
    }
    public void unregisterModuleFromTopic(String moduleID, int topicID){
        for(TopicRegistration topicRegistration : topicRegistrationRepository.findAll()){
            if(topicRegistration.getModule_ID().equals(moduleID) && topicRegistration.getTopic_ID()==topicID){
                topicRegistrationRepository.delete(topicRegistration);
                break;
            }
        }
    }


}
