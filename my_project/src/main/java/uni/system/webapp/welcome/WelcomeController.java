package uni.system.webapp.welcome;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import uni.system.webapp.tables.*;
import uni.system.webapp.tables.Module;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class WelcomeController {

    @Autowired
    WelcomeService service;

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String showWelcomePage(ModelMap model, HttpSession session) {
        String userID = (String) session.getAttribute("ID");

        //If no user ID in session, ask user to log back in
        if(userID == null) {
            return "redirect:login";
        }

        model.addAttribute("user_name", service.getUserName(userID));

        List<Staff> staffList = service.getAllStaff();
        model.addAttribute("st", staffList);

        model.addAttribute("ID", userID);
        List<Module> modules = service.getAllModules();
        model.addAttribute("mod", modules);

        List<Topic> topics = service.getAllTopics();
        model.addAttribute("top", topics);

        List<TopicRegistration> topicRegistrations = service.getAllTopicRegistrations();
        model.addAttribute("top_reg", topicRegistrations);

        return "welcome";
    }
}
