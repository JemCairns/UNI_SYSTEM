package uni.system.webapp.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
    public String showWelcomePage(ModelMap model, @RequestParam String ID, @RequestParam String password, HttpSession session) {

        String userType = service.validateUser(ID, password);
        boolean invalidUser = userType.equals("");
        System.out.println(invalidUser);

        if(invalidUser) {
            model.addAttribute("errorMessage", "*Invalid credentials");
            return "login";
        }

//        model.addAttribute("ID", ID);
//        model.addAttribute("password", password);

        session.setAttribute("ID", ID+userType);
//        session.setAttribute("editModuleID", "COMP00001");
        return "redirect:welcome";
    }
}