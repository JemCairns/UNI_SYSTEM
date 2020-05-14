package uni.system.webapp.welcome;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uni.system.webapp.repositories.*;
import uni.system.webapp.tables.*;
import uni.system.webapp.tables.Module;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static uni.system.webapp.filter.SecurityConstraints.*;

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

    @Autowired
    UserRepository userRepository;

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
