package uni.system.webapp.modules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import uni.system.webapp.tables.*;
import uni.system.webapp.tables.Module;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ModulesController {

    @Autowired
    ModulesService service;

    @RequestMapping(path = "/modules", method = RequestMethod.GET)
    public String showModulePage(ModelMap model, HttpSession session) {
        String userID = (String) session.getAttribute("ID");

        if(userID == null) {
            return "redirect:login";
        }

        String studentID = service.getStudent(userID).getID();
        List<Module> modules = service.getAllModules();
        List<ModuleRegistration> moduleRegs = service.getAllModuleRegsForStudent(userID);
        List<Topic> topics = service.getAllTopics();
        List<TopicRegistration> topicRegs = service.getAllTopicRegistrations();
        boolean hasModules = false;
        boolean registeredToAll = false;

        if(moduleRegs.size() > 0) {
            hasModules = true;
        }
        if(modules.size() == moduleRegs.size()) {
            registeredToAll = true;
        }

        model.addAttribute("ID", studentID+"STU");
        model.addAttribute("mod", modules);
        model.addAttribute("hasModules", hasModules);
        model.addAttribute("modRegs", moduleRegs);
        model.addAttribute("top", topics);
        model.addAttribute("top_reg", topicRegs);
        model.addAttribute("registeredToAll", registeredToAll);

        return "modules";
    }

    @RequestMapping(path = "/modules", method = RequestMethod.POST)
    public String registerForModule(ModelMap model, HttpSession session, @RequestParam String checkedModule) {
        String userID = (String) session.getAttribute("ID");
        System.out.println(userID);

        if(userID == null) {
            return "redirect:login";
        }

        service.addModuleRegistration(userID+"STU", checkedModule);
        String studentID = service.getStudent(userID).getID();
        List<Module> modules = service.getAllModules();
        List<ModuleRegistration> moduleRegs = service.getAllModuleRegsForStudent(userID);
        List<Topic> topics = service.getAllTopics();
        List<TopicRegistration> topicRegs = service.getAllTopicRegistrations();
        boolean hasModules = false;
        boolean registeredToAll = false;

        if(moduleRegs.size() > 0) {
            hasModules = true;
        }
        if(modules.size() == moduleRegs.size()) {
            registeredToAll = true;
        }

        model.addAttribute("ID", studentID+"STU");
        model.addAttribute("mod", modules);
        model.addAttribute("hasModules", hasModules);
        model.addAttribute("modRegs", moduleRegs);
        model.addAttribute("top", topics);
        model.addAttribute("top_reg", topicRegs);
        model.addAttribute("registeredToAll", registeredToAll);

        return "modules";
    }
}