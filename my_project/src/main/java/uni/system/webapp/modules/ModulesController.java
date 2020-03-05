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

        //If no user ID in session, ask user to log back in
        if(userID == null) {
            return "redirect:login";
        }

        return getString(model, userID);
    }

    //Extracted common actions from each method
    private String getString(ModelMap model, String userID) {
        String studentID = service.getStudent(userID).getID();
        List<Module> modules = service.getAllModules();
        List<ModuleRegistration> moduleRegs = service.getAllModuleRegsForStudent(userID);
        List<Module> moduleNotRegs = service.getAllModuleNotRegsForStudent(userID);
        List<Topic> topics = service.getAllTopics();
        List<TopicRegistration> topicRegs = service.getAllTopicRegistrations();
        List<Staff> staffList = service.getAllStaff();
        boolean hasModules = service.hasModules(userID);

        model.addAttribute("user_name", service.getUserName(userID));
        model.addAttribute("st", staffList);
        model.addAttribute("ID", studentID);
        model.addAttribute("mod", modules);
        model.addAttribute("hasModules", hasModules);
        model.addAttribute("modRegs", moduleRegs);
        model.addAttribute("modNotRegs", moduleNotRegs);
        model.addAttribute("top", topics);
        model.addAttribute("top_reg", topicRegs);

        return "modules";
    }

    @RequestMapping(path = "/modules", method = RequestMethod.POST)
    public String registerForModule(ModelMap model, HttpSession session, @RequestParam String checkedModule, @RequestParam String formType) {
        String userID = (String) session.getAttribute("ID");

        if(userID == null) {
            return "redirect:login";
        }

        //Student
        if(userID.endsWith("STU")) {

            if (service.getStudent(userID).getFees_due() > 0) {
                model.addAttribute("maxStudents", false);
                model.addAttribute("feesDue", true);
            } else {
                if(formType.equals("dropout")) {
                    boolean moduleTerminated = service.removeModuleRegiatration(userID, checkedModule);

                    if(!moduleTerminated) {
                        model.addAttribute("dropOut", true);
                    }
                    model.addAttribute("maxStudents", false);
                    model.addAttribute("feesDue", false);
                } else {
                    boolean maxStudents = service.addModuleRegistration(userID, checkedModule);
                    model.addAttribute("maxStudents", maxStudents);
                    model.addAttribute("feesDue", false);
                }
            }

            return getString(model, userID);
        }
        //Staff
        else{
            getString(model, userID);
            session.setAttribute("editModuleID", checkedModule);

            if(formType.equals("edit")) {
                return "redirect:edit_module";
            } else {
                return "redirect:grades";
            }
        }

    }
}
