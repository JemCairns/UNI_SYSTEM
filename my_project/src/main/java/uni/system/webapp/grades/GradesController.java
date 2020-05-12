package uni.system.webapp.grades;

import org.springframework.beans.factory.annotation.Autowired;
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

        //If no user ID in session, ask user to log back in
        if(userID == null) {
            return "redirect:login";
        }

        return getString(model, userID, moduleID);
    }

    // Extracting common actions from each method
    private String getString(ModelMap model, String userID, String moduleID) {
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

        //If no user ID in session, ask user to log back in
        if(userID == null) {
            return "redirect:login";
        }

        boolean validInput = true;
        for(int i=0; i<grades.length; i++){
            if(grades[i].length()==0 || percents[i].length()==0){
                model.addAttribute("error", true);
                validInput = false;
                break;
            }
        }
        if(validInput) {
            validInput = service.updateGrades(moduleID, grades, percents);
        }

        //Check if the grade & percentage is valid
        if(!validInput && grades[0] != null) {
            model.addAttribute("error", true);
        }

        return getString(model, userID, moduleID);
    }
}
