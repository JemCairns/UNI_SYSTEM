package uni.system.webapp.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class LoginController {

    @Autowired
    LoginService service;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginPage(ModelMap model) {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String showWelcomePage(ModelMap model, @RequestParam String ID, @RequestParam String password) {

        boolean isValidUser = service.validateUser(ID, password);
        System.out.println(isValidUser);

        if(!isValidUser) {
            model.addAttribute("errorMessage", "*Invalid credentials");
            return "login";
        }

        model.addAttribute("ID", ID);
        model.addAttribute("password", password);
        return "welcome";
    }
}
