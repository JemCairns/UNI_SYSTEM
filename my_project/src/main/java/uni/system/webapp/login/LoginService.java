package uni.system.webapp.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uni.system.webapp.repositories.ModuleRepository;
import uni.system.webapp.repositories.StudentRepository;
import uni.system.webapp.tables.Module;

import java.util.List;

@Service
public class LoginService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    ModuleRepository moduleRepository;

    public boolean validateUser(String ID, String pass) {
        if(studentRepository.existsById(ID)) {
            return studentRepository.getOne(ID).getPassword().equals(pass);
        }
        return false;
    }

    public List<Module> getAllModules() {
        return moduleRepository.findAll();
    }
}
