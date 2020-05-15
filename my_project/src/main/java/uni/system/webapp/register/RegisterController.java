package uni.system.webapp.register;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import uni.system.webapp.captcha.ReCaptchaResponse;
import uni.system.webapp.logger.Logging;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class RegisterController {

    @Autowired
    RegisterService service;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showRegisterPage() {
        return "register";
    }

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String showWelcomePage(ModelMap model,
                                  @RequestParam String new_ID,
                                  @RequestParam String new_password,
                                  @RequestParam String confirm_password,
                                  @RequestParam String first_name,
                                  @RequestParam String last_name,
                                  @RequestParam String address1,
                                  @RequestParam String address2,
                                  @RequestParam String phone_number,
                                  @RequestParam String email,
                                  @RequestParam String gender,
                                  @RequestParam String date_of_birth,
                                  @RequestParam String stage,
                                  @RequestParam Integer password_strength,
                                  @RequestParam(name="g-recaptcha-response") String captchaResponse) {

        String url = "https://www.google.com/recaptcha/api/siteverify";
        String params = "?secret=6LctyPYUAAAAAM45kposcNT1V-vYiUYhpt98aTsu&response="+captchaResponse;

        ReCaptchaResponse reCaptchaResponse = restTemplate.exchange(url+params, HttpMethod.POST, null, ReCaptchaResponse.class).getBody();

        boolean invalidDetails = false;
        if(     new_ID.equals("") ||
                new_password.equals("") ||
                confirm_password.equals("") ||
                first_name.equals("") ||
                last_name.equals("") ||
                address1.equals("") ||
                address2.equals("") ||
                phone_number.equals("") ||
                email.equals("") ||
                date_of_birth.equals("") ||
                reCaptchaResponse == null ||
                !reCaptchaResponse.isSuccess()){
            model.addAttribute("errorMessage", "*Please fill out all fields");
            invalidDetails = true;
        }
        else if(!new_password.equals(confirm_password)){
            model.addAttribute("errorMessage", "*Passwords do not match.");
            invalidDetails = true;
        }
        else if(password_strength < 3){
            model.addAttribute("errorMessage", "*Password is not strong enough.");
            invalidDetails = true;
        }
        else if(!email.endsWith("@springfield.com") || new_ID.length()!=7 || phone_number.length()!=10){
            model.addAttribute("errorMessage", "*Invalid details.");
            invalidDetails = true;
        }
        if(invalidDetails){

            // user failed to register
            if(reCaptchaResponse == null){
                Logging.getInstance().warning("Student with id=" + new_ID + " failed to register.");
            }
            else {
                Logging.getInstance().warning("Student with id=" + new_ID + " failed to register from IP=" + reCaptchaResponse.getHostname() + ".");
            }

            model.addAttribute("new_ID", new_ID);
            model.addAttribute("new_password", new_password);
            model.addAttribute("confirm_password", confirm_password);
            model.addAttribute("first_name", first_name);
            model.addAttribute("last_name", last_name);
            model.addAttribute("address1", address1);
            model.addAttribute("address2", address2);
            model.addAttribute("phone_number", phone_number);
            model.addAttribute("email", email);
            model.addAttribute("gender", gender);
            model.addAttribute("date_of_birth", date_of_birth);
            model.addAttribute("stage", stage);
            model.addAttribute("password_strength", password_strength);
            return "register";
        }

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date user_dob = null;
        try {
            user_dob = df.parse(date_of_birth);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        boolean alreadyRegistered = service.registerUser(new_ID+"STU", new_password, first_name, last_name, address1+", "+address2, phone_number, email, gender, user_dob, stage);

        //Check if the student is already registered
        if(!alreadyRegistered) {

            // user already registered
            Logging.getInstance().warning("Student with id=" + new_ID + " attempted to register from IP="
                    + reCaptchaResponse.getHostname() + " but was already registered.");

            model.addAttribute("errorMessage", "*You have already registered.");
            model.addAttribute("new_ID", new_ID);
            model.addAttribute("new_password", new_password);
            model.addAttribute("confirm_password", confirm_password);
            model.addAttribute("first_name", first_name);
            model.addAttribute("last_name", last_name);
            model.addAttribute("address1", address1);
            model.addAttribute("address2", address2);
            model.addAttribute("phone_number", phone_number);
            model.addAttribute("email", email);
            model.addAttribute("gender", gender);
            model.addAttribute("date_of_birth", date_of_birth);
            model.addAttribute("stage", stage);
            model.addAttribute("password_strength", password_strength);
            return "register";
        }

        // user successfully registered
        Logging.getInstance().info("Student with id=" + new_ID + " successfully registered from IP=" + reCaptchaResponse.getHostname() + ".");

        return "redirect:welcome";
    }
}
