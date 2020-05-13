package uni.system.webapp.modules;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uni.system.webapp.repositories.*;
import uni.system.webapp.tables.*;
import uni.system.webapp.tables.Module;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

import static uni.system.webapp.filter.SecurityConstraints.COOKIE_NAME;
import static uni.system.webapp.filter.SecurityConstraints.SECRET;

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

    @Autowired
    StaffRepository staffRepository;

    public Student getStudent(String id) {
        return studentRepository.getOne(id);
    }
    public List<Staff> getAllStaff() { return staffRepository.findAll(); }
    public List<Module> getAllModules() {
        return moduleRepository.findAll();
    }

    public List<ModuleRegistration> getAllModuleRegsForStudent(String id) {
        List<ModuleRegistration> moduleRegs = moduleRegistrationRepository.findAll();
        List<ModuleRegistration> studentModules = new LinkedList<>();

        for(ModuleRegistration m : moduleRegs) {
            if(m.getStudent_ID().equals(id)) {
                studentModules.add(m);
            }
        }
        return studentModules;
    }

    public boolean hasModules(String ID){
        if(ID.endsWith("STU")){
            for(ModuleRegistration registration : moduleRegistrationRepository.findAll()){
                if(registration.getStudent_ID().equals(ID)){
                    return true;
                }
            }
        }
        else if(ID.endsWith("STA")){
            for(Module module : moduleRepository.findAll()){
                if(module.getCoordinator_ID().equals(ID)){
                    return true;
                }
            }
        }
        return false;
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

    public boolean addModuleRegistration(String studentID, String moduleID) {
        ModuleRegistration moduleRegistration = new ModuleRegistration(studentID, moduleID, "NA", 0.0);
        Module module = moduleRepository.getOne(moduleID);
        List<ModuleRegistration> moduleRegistrationList = new LinkedList<>();

        for(ModuleRegistration reg : moduleRegistrationRepository.findAll()) {
            if(reg.getModule_ID().equals(moduleID)) {
                moduleRegistrationList.add(reg);
            }
        }

        if(moduleRegistrationList.size() + 1 > module.getMax_num_students()) {
            return true;
        } else {
            module.setCurr_num_students(module.getCurr_num_students() + 1);
            moduleRepository.save(module);
            moduleRegistrationRepository.save(moduleRegistration);
            return false;
        }
    }

    public boolean removeModuleRegiatration(String id, String module) {
        List<ModuleRegistration> moduleRegistrations = moduleRegistrationRepository.findAll();

        for(ModuleRegistration m : moduleRegistrations) {
            if(m.getModule_ID().equals(module) && m.getStudent_ID().equals(id)) {
                Module mod = moduleRepository.getOne(module);
                if(mod.getStatus().equals("terminated")) {
                    return false;
                } else {
                    moduleRegistrationRepository.delete(m);
                    break;
                }
            }
        }

        return true;
    }

    public String getID(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String token = null;


        if(cookies!=null){

            for(Cookie cookie: cookies){
                if(cookie.getName().equals(COOKIE_NAME))
                    token = cookie.getValue();
            }}

        if (token != null) {
            String user = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .build()
                    .verify(token)
                    .getSubject();
            return user;
        }
        else
            return "";
    }
}
