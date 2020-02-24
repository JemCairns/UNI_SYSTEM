package uni.system.webapp.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uni.system.webapp.tables.Student;

import javax.servlet.http.HttpSession;

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
        Student student = service.getProfile(userID);

        if(userID.length() == 7) {
            model.addAttribute("account", true);
        } else {
            model.addAttribute("account", false);
        }

        model.addAttribute("ID", student.getID().substring(0, 7));
        model.addAttribute("fName", student.getFirst_name());
        model.addAttribute("lName", student.getLast_name());
        model.addAttribute("email", student.getEmail());
        model.addAttribute("number", student.getPhone_number());
        model.addAttribute("address", student.getAddress());

        return "profile";
    }
}