package uni.system.webapp.profile;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uni.system.webapp.repositories.ModuleRegistrationRepository;
import uni.system.webapp.repositories.StaffRepository;
import uni.system.webapp.repositories.StudentRepository;
import uni.system.webapp.tables.ModuleRegistration;
import uni.system.webapp.tables.Staff;
import uni.system.webapp.tables.Student;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static uni.system.webapp.filter.SecurityConstraints.COOKIE_NAME;
import static uni.system.webapp.filter.SecurityConstraints.SECRET;

@Service
public class ProfileService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    StaffRepository staffRepository;

    @Autowired
    ModuleRegistrationRepository moduleRegistrationRepository;

    public Student getStudent(String ID) {
        return studentRepository.getOne(ID);
    }
    public Staff getStaff(String ID) {
        return staffRepository.getOne(ID);
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

    public void deleteStudent(String id){
        for(ModuleRegistration moduleRegistration : moduleRegistrationRepository.findAll()){
            if(moduleRegistration.getStudent_ID().equals(id)){
                moduleRegistrationRepository.delete(moduleRegistration);
            }
        }
        Student student = getStudent(id);
        studentRepository.delete(student);
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
