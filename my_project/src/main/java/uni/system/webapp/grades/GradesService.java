package uni.system.webapp.grades;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uni.system.webapp.repositories.ModuleRegistrationRepository;
import uni.system.webapp.repositories.StaffRepository;
import uni.system.webapp.repositories.StudentRepository;
import uni.system.webapp.tables.ModuleRegistration;
import uni.system.webapp.tables.Student;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static uni.system.webapp.filter.SecurityConstraints.COOKIE_NAME;
import static uni.system.webapp.filter.SecurityConstraints.SECRET;

@Service
public class GradesService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    StaffRepository staffRepository;

    @Autowired
    ModuleRegistrationRepository moduleRegistrationRepository;

    private String[] validGrades = {"NG", "F", "E", "D-", "D", "D+", "C-", "C", "C+", "B-", "B", "B+", "A-", "A", "A+"};
    private Integer[] validPercents = {10, 25,  40,   45,  50,   55,   60,  65,   70,   75,  80,  85 ,   90,  95,  101};

    public String getUserName(String ID){
        if(ID.endsWith("STU")){
            return studentRepository.getOne(ID).getFirst_name();
        }
        else if(ID.endsWith("STA")){
            return staffRepository.getOne(ID).getFirst_name();
        }
        return "";
    }

    public List<ModuleRegistration> getAllModuleRegistrations() {
        return moduleRegistrationRepository.findAll();
    }

    public List<Student> getAllStudentsRegisteredToModule(String moduleID) {
        List<ModuleRegistration> moduleRegistrations = moduleRegistrationRepository.findAll();
        List<Student> students = new LinkedList<>();

        for(ModuleRegistration m : moduleRegistrations) {
            if(m.getModule_ID().equals(moduleID)) {
                students.add(studentRepository.getOne(m.getStudent_ID()));
            }
        }

        return students;
    }

    public boolean updateGrades(String moduleID, String[] grades, String[] percents) {
        List<ModuleRegistration> moduleRegistrations = getAllModuleRegistrations();

        List<String> validGradesList = Arrays.asList(validGrades);
        List<Integer> validPercentsList = Arrays.asList(validPercents);
        int i = 0;
        for(ModuleRegistration m : moduleRegistrations) {
            if(m.getModule_ID().equals(moduleID)) {

                if(validGradesList.contains(grades[i]) && grades[i].equals("NG") && Double.parseDouble(percents[i]) < validPercentsList.get(validGradesList.indexOf(grades[i]))) {
                    m.setLetterGrade(grades[i]);
                    m.setPercentage(Double.parseDouble(percents[i]));
                    moduleRegistrationRepository.save(m);
                    i++;
                } else if(validGradesList.contains(grades[i]) && (Double.parseDouble(percents[i]) < validPercentsList.get(validGradesList.indexOf(grades[i])) && Double.parseDouble(percents[i]) >= validPercentsList.get(validGradesList.indexOf(grades[i])-1))) {
                    m.setLetterGrade(grades[i]);
                    m.setPercentage(Double.parseDouble(percents[i]));
                    moduleRegistrationRepository.save(m);
                    i++;
                } else {
                    return false;
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
