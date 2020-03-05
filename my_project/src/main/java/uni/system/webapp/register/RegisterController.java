package uni.system.webapp.register;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpSession;
import java.sql.Date;

@Controller
public class RegisterController {

    @Autowired
    RegisterService service;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showRegisterPage() {
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
                                  @RequestParam Date date_of_birth,
                                  @RequestParam String stage,
                                  HttpSession session) {

        if(     new_ID.length()<7 ||
                new_password.equals("") ||
                first_name.equals("") ||
                last_name.equals("") ||
                address.equals("") ||
                phone_number.equals("") ||
                email.equals("") ||
                gender.equals("") ||
                date_of_birth == null ||
                stage.equals("")){
            model.addAttribute("errorMessage", "*Invalid registration details.");
            System.out.println("added");
            session.setAttribute("ID", new_ID);
            return "register";
        }

        boolean alreadyRegistered = service.registerUser(new_ID+"STU", new_password, first_name, last_name, address, phone_number, email, gender, date_of_birth, stage);

        //Check if the student is already registered
        if(!alreadyRegistered) {
            model.addAttribute("errorMessage", "*You have already registered.");
            session.setAttribute("ID", new_ID);
            return "register";
        }

        session.setAttribute("ID", new_ID+"STU");
        return "redirect:welcome";
    }
}
