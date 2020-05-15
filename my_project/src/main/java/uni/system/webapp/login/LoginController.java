package uni.system.webapp.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/login")
public class LoginController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping()
    public String showLoginPage(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("INVALID")) {
                    model.addAttribute("errorMessage", "*Invalid credentials");
                    Cookie cookie = new Cookie("INVALID", null);
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    return "login";
                } else if (c.getName().equals("NOTFOUND")) {
                    model.addAttribute("errorMessage", "*Invalid credentials");
                    Cookie cookie = new Cookie("NOTFOUND", null);
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    return "login";
                }
            }
        }

        return "login";
    }
}