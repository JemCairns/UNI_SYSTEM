package uni.system.webapp.welcome;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import uni.system.webapp.tables.Module;
import uni.system.webapp.tables.Topic;
import uni.system.webapp.tables.TopicRegistration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
public class WelcomeController {

    @Autowired
    WelcomeService service;

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String showWelcomePage(ModelMap model, HttpSession session) {
        model.addAttribute("ID", session.getAttribute("ID"));
        List<Module> modules = service.getAllModules();
        model.addAttribute("mod", modules);

        List<Topic> topics = service.getAllTopics();
        model.addAttribute("top", topics);

        List<TopicRegistration> topicRegistrations = service.getAllTopicRegistrations();
        model.addAttribute("top_reg", topicRegistrations);

        return "welcome";
    }

    @RequestMapping(name = "/welcome", method = RequestMethod.GET)
    public String showProfilePage(ModelMap model) {
        return "profile";
    }
}
