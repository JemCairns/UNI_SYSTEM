package uni.system.webapp.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uni.system.webapp.repositories.*;
import uni.system.webapp.security.PasswordUtils;
import uni.system.webapp.tables.Staff;
import uni.system.webapp.tables.Student;

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

    public String validateUser(String ID, String pass, String user) {

        if(user.equals("student")) {
            if(studentRepository.existsById(ID+"STU")) {
                Student student = studentRepository.getOne(ID+"STU");
                if(PasswordUtils.verifyUserPassword(pass, student.getPassword(), student.getSalt())){
                    return "STU";
                }
            }
        }
        else {
            if(staffRepository.existsById(ID+"STA")){
                Staff staff = staffRepository.getOne(ID+"STA");
                if(PasswordUtils.verifyUserPassword(pass, staff.getPassword(), staff.getSalt())){
                    return "STA";
                }
            }
        }
        return "";
    }
}
