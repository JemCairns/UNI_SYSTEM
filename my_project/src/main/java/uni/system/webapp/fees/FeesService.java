package uni.system.webapp.fees;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uni.system.webapp.logger.Logging;
import uni.system.webapp.repositories.StudentRepository;
import uni.system.webapp.tables.Student;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import static uni.system.webapp.filter.SecurityConstraints.COOKIE_NAME;
import static uni.system.webapp.filter.SecurityConstraints.SECRET;

@Service
public class FeesService {

    @Autowired
    private StudentRepository studentRepository;

    public Student getStudent(String id) {
        return studentRepository.getOne(id);
    }

    public String updateStudentFees(Student s, double amount) {
        if(amount < 0) {
            return "Please enter a valid number!";
        } else if(amount > s.getFees_due()) {
            return "Please enter a valid amount!";
        } else if(s.getFees_due() == 0) {
            return "You have paid all of your fess!";
        } else {
            // student paid fees
            Logging.getInstance().info("Student with id=" + s.getID() + " paid fees=€" + amount + ".");

            s.setFees_due(s.getFees_due() - amount);
            s.setFees_paid(s.getFees_paid() + amount);
            studentRepository.save(s);
            return "ok";
        }
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
