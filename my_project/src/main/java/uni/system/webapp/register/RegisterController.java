package uni.system.webapp.register;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class RegisterController {

    @Autowired
    RegisterService service;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showRegisterPage(ModelMap model) {
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String showWelcomePage(ModelMap model,
                                  @RequestParam String new_ID,
                                  @RequestParam String new_password,
                                  @RequestParam String first_name,
                                  @RequestParam String last_name,
                                  @RequestParam String address,
                                  @RequestParam String phone_number,
                                  @RequestParam String email,
                                  @RequestParam String gender,
                                  HttpSession session) {

        boolean alreadyRegistered = service.registerUser(new_ID+"STU", new_password, first_name, last_name, address, phone_number, email, gender);

        if(!alreadyRegistered) {
            model.addAttribute("errorMessage", "You have already registered.");
            session.setAttribute("ID", new_ID);
            return "redirect:login";
        }
        model.addAttribute("ID", new_ID);
        session.setAttribute("ID", new_ID);
        return "redirect:welcome";
    }


}
