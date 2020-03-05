package uni.system.webapp.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpSession;
import java.util.TreeMap;

@Controller
public class StatisticsController {

    @Autowired
    StatisticsService service;


    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    public String showProfilePage(ModelMap model, HttpSession session) {
        String userID = (String) session.getAttribute("ID");

        //If no user ID in session, ask user to log back in
        if(userID == null) {
            return "redirect:login";
        }

        model.addAttribute("ID", userID);
        model.addAttribute("user_name", service.getUserName(userID));

        TreeMap<String, Integer> studentGenderMap = service.getStudentGendersAndCounts();
        TreeMap<String, Integer> staffGenderMap = service.getStaffGendersAndCounts();
        model.addAttribute("student_gender_categories", studentGenderMap.keySet());
        model.addAttribute("student_gender_counts", studentGenderMap.values());
        model.addAttribute("staff_gender_categories", staffGenderMap.keySet());
        model.addAttribute("staff_gender_counts", staffGenderMap.values());

        model.addAttribute("num_students", service.getNumStudents());
        model.addAttribute("num_staff", service.getNumStaff());

        TreeMap<Integer, Integer> studentIndexedAges = service.getStudentIndexedAges();
        TreeMap<Integer, Integer> staffIndexedAges = service.getStaffIndexedAges();
        model.addAttribute("student_age_categories", studentIndexedAges.keySet());
        model.addAttribute("student_age_counts", studentIndexedAges.values());
        model.addAttribute("staff_age_categories", staffIndexedAges.keySet());
        model.addAttribute("staff_age_counts", staffIndexedAges.values());

        TreeMap<String, Integer> studentStagesAndCounts = service.getStudentStagesAndCounts();
        model.addAttribute("student_stage_categories", studentStagesAndCounts.keySet());
        model.addAttribute("student_stage_counts", studentStagesAndCounts.values());

        return "statistics";
    }
}