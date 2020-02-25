package uni.system.webapp.modules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uni.system.webapp.repositories.*;
import uni.system.webapp.tables.*;
import uni.system.webapp.tables.Module;

import javax.persistence.GeneratedValue;
import java.util.LinkedList;
import java.util.List;

@Service
public class ModulesService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    ModuleRegistrationRepository moduleRegistrationRepository;

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    TopicRegistrationRepository topicRegistrationRepository;

    public Student getStudent(String id) {
        return studentRepository.getOne(id);
    }

    public List<Module> getAllModules() {
        return moduleRepository.findAll();
    }

    public List<ModuleRegistration> getAllModuleRegsForStudent(String id) {
        List<ModuleRegistration> moduleRegs = moduleRegistrationRepository.findAll();
        List<ModuleRegistration> studentModules = new LinkedList<>();

        for(ModuleRegistration m : moduleRegs) {
//            System.out.println(m.getStudent_ID());
            if(m.getStudent_ID().equals(id+"STU")) {
                studentModules.add(m);
            }
        }
        return studentModules;
    }

    public List<Module> getAllModuleNotRegsForStudent(String id){
        List<ModuleRegistration> studentRegs = getAllModuleRegsForStudent(id);
        List<Module> allModules = getAllModules();
        List<Module> studentNotRegs = new LinkedList<>();

        for(Module module : allModules){
            boolean included=false;
            for(ModuleRegistration moduleRegistration : studentRegs){
                if(module.getID().equals(moduleRegistration.getModule_ID())){
                    included=true;
                    break;
                }
            }
            if(!included){
                studentNotRegs.add(module);
            }
        }
        return studentNotRegs;
    }

    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }
    public List<TopicRegistration> getAllTopicRegistrations() {
        return topicRegistrationRepository.findAll();
    }

    public void addModuleRegistration(String studentID, String moduleID) {
        ModuleRegistration moduleRegistration = new ModuleRegistration(studentID, moduleID, "NA", 0.0);
        moduleRegistrationRepository.save(moduleRegistration);
    }
}
