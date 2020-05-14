package uni.system.webapp.logout;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LogoutController {

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String logout() {
        return "login"; }

}