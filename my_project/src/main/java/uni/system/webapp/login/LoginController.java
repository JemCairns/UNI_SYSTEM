package uni.system.webapp.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import uni.system.webapp.captcha.ReCaptchaResponse;
import uni.system.webapp.logger.Logging;
import uni.system.webapp.tables.Student;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class LoginController {

    @Autowired
    LoginService service;

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginPage() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String showWelcomePage(ModelMap model,
                                  @RequestParam String ID,
                                  @RequestParam String password,
                                  @RequestParam String user,
                                  @RequestParam(name="g-recaptcha-response") String captchaResponse,
                                  HttpSession session
    ) {

        String url = "https://www.google.com/recaptcha/api/siteverify";
        String params = "?secret=6LctyPYUAAAAAM45kposcNT1V-vYiUYhpt98aTsu&response="+captchaResponse;

        ReCaptchaResponse reCaptchaResponse = restTemplate.exchange(url+params, HttpMethod.POST, null, ReCaptchaResponse.class).getBody();

        String userType="";
        boolean invalidUser = true;
        if(reCaptchaResponse.isSuccess()) {
            userType = service.validateUser(ID, password, user);
            invalidUser = userType.equals("");
        }

        if(invalidUser) {
            // login failure
            Logging.getInstance().warning("User with id=" + ID+userType + " failed to login from IP=" /*+IP*/ + ".");
            model.addAttribute("errorMessage", "*Invalid credentials");
            return "login";
        }

        // login success
        Logging.getInstance().info("User with id=" + ID+userType + " successfully logged in from IP=" /*+IP*/ + ".");
        session.setAttribute("ID", ID+userType);
        return "redirect:welcome";
    }
}