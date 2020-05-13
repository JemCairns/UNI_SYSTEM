package uni.system.webapp.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/login")
public class LoginController {

    @Autowired
    LoginService service;

    @GetMapping()
    public String showLoginPage() {
        return "login";
    }

    @PostMapping()
    public String showWelcomePage(ModelMap model, @RequestParam String username, @RequestParam String password, @RequestParam String user) {

        String userType = service.validateUser(username, password, user);
        boolean invalidUser = userType.equals("");

        if(invalidUser) {
            model.addAttribute("errorMessage", "*Invalid credentials");
            return "login";
        }

        return "redirect:welcome";
    }
}