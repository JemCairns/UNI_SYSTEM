package uni.system.webapp.welcome;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import uni.system.webapp.tables.Module;
import uni.system.webapp.tables.Student;
import uni.system.webapp.tables.Topic;
import uni.system.webapp.tables.TopicRegistration;

import java.util.List;

@Controller
public class WelcomeController {

    @Autowired
    WelcomeService service;

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String showWelcomePage(ModelMap model, @RequestParam String ID) {

        model.addAttribute("ID", ID);

        List<Module> modules = service.getAllModules();
        model.addAttribute("mod", modules);

        List<Topic> topics = service.getAllTopics();
        model.addAttribute("top", topics);

        List<TopicRegistration> topicRegistrations = service.getAllTopicRegistrations();
        model.addAttribute("top_reg", topicRegistrations);
        return "welcome";
    }

    @RequestMapping(value = "/profile/{id}", method = RequestMethod.GET)
    public String showProfilePage(ModelMap model, @PathVariable(value = "id") String ID) {

        model.addAttribute("id", ID);
        return "profile";
    }
}
