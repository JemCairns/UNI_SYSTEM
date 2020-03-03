package uni.system.webapp.grades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import uni.system.webapp.tables.ModuleRegistration;
import uni.system.webapp.tables.Student;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class GradesController {

    @Autowired
    GradesService service;

    @RequestMapping(value = "/grades", method = RequestMethod.GET)
    public String showGradesPage(ModelMap model, HttpSession session) {
        String userID = (String) session.getAttribute("ID");
        String moduleID = (String) session.getAttribute("editModuleID");
        if(userID == null) {
            return "redirect:login";
        }

        List<ModuleRegistration> moduleRegistrations = service.getAllModuleRegistrations();
        List<Student> students = service.getAllStudentsRegisteredToModule(moduleID);
        model.addAttribute("mod_regs", moduleRegistrations);
        model.addAttribute("moduleID", moduleID);
        model.addAttribute("user_name", service.getUserName(userID));
        model.addAttribute("students", students);
        model.addAttribute("ID", userID);
        return "grades";
    }

    @RequestMapping(value = "/grades", method = RequestMethod.POST)
    public String updateGrades(ModelMap model, HttpSession session, @RequestParam("grades") String[] grades, @RequestParam("percents") String[] percents) {
        String userID = (String) session.getAttribute("ID");
        String moduleID = (String) session.getAttribute("editModuleID");
        if(userID == null) {
            return "redirect:login";
        }

        for(String g : grades) {
            System.out.println(g);
        }
        System.out.println("STOP");
        boolean validInput = service.updateGrades(moduleID, grades, percents);

        if(!validInput) {
            model.addAttribute("error", true);
        }

        List<ModuleRegistration> moduleRegistrations = service.getAllModuleRegistrations();
        List<Student> students = service.getAllStudentsRegisteredToModule(moduleID);

        model.addAttribute("mod_regs", moduleRegistrations);
        model.addAttribute("moduleID", moduleID);
        model.addAttribute("user_name", service.getUserName(userID));
        model.addAttribute("students", students);
        model.addAttribute("ID", userID);
        return "grades";
    }
}
