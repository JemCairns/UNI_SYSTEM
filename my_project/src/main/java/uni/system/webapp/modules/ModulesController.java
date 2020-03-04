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
        System.out.println("in get");

        if(userID == null) {
            return "redirect:login";
        }

        String studentID = service.getStudent(userID).getID();
        List<Module> modules = service.getAllModules();
        List<ModuleRegistration> moduleRegs = service.getAllModuleRegsForStudent(userID);
        List<Module> moduleNotRegs = service.getAllModuleNotRegsForStudent(userID);
        List<Topic> topics = service.getAllTopics();
        List<TopicRegistration> topicRegs = service.getAllTopicRegistrations();
        boolean hasModules = service.hasModules(userID);

//        List<Student> students = service.getAllStudents();
//        model.addAttribute("studs", students);
        model.addAttribute("user_name", service.getUserName(userID));

        List<Staff> staffList = service.getAllStaff();
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
        System.out.println(userID);

        System.out.println("inside post");

        if(userID == null) {
            return "redirect:login";
        }

        if(userID.endsWith("STU")) {

            if (service.getStudent(userID).getFees_due() > 0) {
                model.addAttribute("maxStudents", false);
                model.addAttribute("feesDue", true);
            } else {

                if(formType.equals("dropout")) {
                    boolean moduleTerminated = service.removeModuleRegiatration(userID, checkedModule);

                    System.out.println("HELLO " + moduleTerminated);
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

            String studentID = service.getStudent(userID).getID();
            List<Module> modules = service.getAllModules();
            List<ModuleRegistration> moduleRegs = service.getAllModuleRegsForStudent(userID);
            List<Module> moduleNotRegs = service.getAllModuleNotRegsForStudent(userID);
            List<Topic> topics = service.getAllTopics();
            List<TopicRegistration> topicRegs = service.getAllTopicRegistrations();
            boolean hasModules = service.hasModules(userID);
            model.addAttribute("user_name", service.getUserName(userID));
            List<Staff> staffList = service.getAllStaff();
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
        else{
            String studentID = service.getStudent(userID).getID();
            List<Module> modules = service.getAllModules();
            List<ModuleRegistration> moduleRegs = service.getAllModuleRegsForStudent(userID);
            List<Module> moduleNotRegs = service.getAllModuleNotRegsForStudent(userID);
            List<Topic> topics = service.getAllTopics();
            List<TopicRegistration> topicRegs = service.getAllTopicRegistrations();
            boolean hasModules = service.hasModules(userID);
            model.addAttribute("user_name", service.getUserName(userID));
            List<Staff> staffList = service.getAllStaff();
            model.addAttribute("st", staffList);
            model.addAttribute("ID", studentID);
            model.addAttribute("mod", modules);
            model.addAttribute("hasModules", hasModules);
            model.addAttribute("modRegs", moduleRegs);
            model.addAttribute("modNotRegs", moduleNotRegs);
            model.addAttribute("top", topics);
            model.addAttribute("top_reg", topicRegs);
            session.setAttribute("editModuleID", checkedModule);
            System.out.println("MC: "+checkedModule);

            if(formType.equals("edit")) {
                return "redirect:edit_module";
            } else {
                return "redirect:grades";
            }
        }

    }
}
