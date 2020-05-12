package uni.system.webapp.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import uni.system.webapp.tables.Student;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class LoginController {

    @Autowired
    LoginService service;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginPage() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String showWelcomePage(ModelMap model, @RequestParam String ID, @RequestParam String password, @RequestParam String user,  HttpSession session) {

        String userType = service.validateUser(ID, password, user);
        boolean invalidUser = userType.equals("");

        if(invalidUser) {
            // log login failure
            // id, password, ip address, time
            model.addAttribute("errorMessage", "*Invalid credentials");
            return "login";
        }

        // log login success
        // id, password, ip address, time
        session.setAttribute("ID", ID+userType);
        return "redirect:welcome";
    }
}