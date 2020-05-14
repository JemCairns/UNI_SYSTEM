package uni.system.webapp.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/login")
public class LoginController {

    @Autowired
    LoginService service;

    @GetMapping()
    public String showLoginPage(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();

        for(Cookie c : cookies) {
            if(c.getName().equals("INVALID")) {
                model.addAttribute("errorMessage", "*Invalid credentials");
                response.addCookie(new Cookie("INVALID", null));
                return "login";
            }
        }

        return "login";
    }
}