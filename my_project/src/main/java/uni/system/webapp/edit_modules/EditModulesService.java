package uni.system.webapp.edit_modules;

import org.springframework.beans.factory.annotation.Autowired;
import uni.system.webapp.repositories.ModuleRegistrationRepository;
import uni.system.webapp.repositories.ModuleRepository;
import uni.system.webapp.repositories.StaffRepository;
import uni.system.webapp.repositories.StudentRepository;
import uni.system.webapp.tables.Module;

public class EditModulesService {

    @Autowired
    StaffRepository staffRepository;

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    ModuleRegistrationRepository registrationRepository;

    public String getUserName(String ID){
        return staffRepository.getOne(ID).getFirst_name();
    }

    public Module getModule(String ID){
        return moduleRepository.getOne(ID);
    }


}
