package uni.system.login_stuff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
public class LoginController {

    @Autowired
    LoginService service;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginPage(ModelMap model) {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String showWelcomePage(ModelMap model, @RequestParam String name, @RequestParam String pass) {

        boolean isValidUser = service.validateUser(name, pass);

        if(!isValidUser) {
            model.addAttribute("errorMessage", "Incorrect Credentials");
            return "login";
        }

        model.addAttribute("name", name);
        model.addAttribute("pass", pass);

        return "welcome";
    }
}
