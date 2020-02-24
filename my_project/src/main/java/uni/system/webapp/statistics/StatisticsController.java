package uni.system.webapp.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uni.system.webapp.tables.Student;

import javax.servlet.http.HttpSession;
import java.util.Optional;

import java.applet.*;

@Controller
public class StatisticsController {

    @Autowired
    StatisticsService service;


    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    public String showProfilePage(ModelMap model, HttpSession session) {

        model.addAttribute("ID", session.getAttribute("ID"));

        int[] numGenders = new int[3];
        numGenders[0] = service.getNumGender("Male");
        numGenders[1] = service.getNumGender("Female");
        numGenders[2] = service.getNumGender("Other");
        model.addAttribute("num_genders", numGenders);

        model.addAttribute("num_students", service.getNumStudents());
        model.addAttribute("num_staff", service.getNumStaff());

        return "statistics";
    }

    public int[] getGenders(){
        int[] numGenders = new int[3];
        numGenders[0] = service.getNumGender("Male");
        numGenders[1] = service.getNumGender("Female");
        numGenders[2] = service.getNumGender("Other");
        return numGenders;
    }



}