package uni.system.webapp.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uni.system.webapp.tables.Staff;
import uni.system.webapp.tables.Student;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ProfileController {

    @Autowired
    ProfileService service;


    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String showProfilePage(ModelMap model, HttpSession session) {
        String userID = (String) session.getAttribute("ID");

        if(userID == null) {
            return "redirect:login";
        }

        if(userID.endsWith("STU")) {
            Student student = service.getStudent(userID);
            model.addAttribute("ID", userID);
            model.addAttribute("user_name", student.getFirst_name());
            model.addAttribute("fName", student.getFirst_name());
            model.addAttribute("lName", student.getLast_name());
            model.addAttribute("email", student.getEmail());
            model.addAttribute("number", student.getPhone_number());
            model.addAttribute("address", student.getAddress());
        }
        else{
            Staff staff = service.getStaff(userID);
            model.addAttribute("ID", userID);
            model.addAttribute("user_name", staff.getFirst_name());
            model.addAttribute("fName", staff.getFirst_name());
            model.addAttribute("lName", staff.getLast_name());
//            model.addAttribute("email", staff.getEmail());
//            model.addAttribute("number", staff.getPhone_number());
//            model.addAttribute("address", staff.getAddress());
        }

        return "profile";
    }
}