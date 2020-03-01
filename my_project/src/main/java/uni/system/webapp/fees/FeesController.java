package uni.system.webapp.fees;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import uni.system.webapp.tables.Student;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class FeesController {

    @Autowired
    FeesService service;

    @RequestMapping(path = "/fees", method = RequestMethod.GET)
    public String showModulePage(ModelMap model, HttpSession session) {
        String userID = (String) session.getAttribute("ID");

        if(userID == null) {
            return "redirect:login";
        }

        Student student = service.getStudent(userID);
//        List<Student> students = service.getAllStudents();
        double feesPaid = student.getFees_paid();
        double feesDue = student.getFees_due();
        double feesTotal = feesDue + feesPaid;

//        model.addAttribute("studs", students);
        model.addAttribute("user_name", student.getFirst_name());
        model.addAttribute("feesTotal", feesTotal);
        model.addAttribute("feesDue", feesDue);
        model.addAttribute("feesPaid", feesPaid);
        model.addAttribute("ID", userID);
        return "fees";
    }

    @RequestMapping(path = "/fees", method = RequestMethod.POST)
    public String updateFees(ModelMap model, HttpSession session, @RequestParam double amount) {
        String userID = (String) session.getAttribute("ID");

        if(userID == null) {
            return "redirect:login";
        }

        Student student = service.getStudent(userID);
        String error = service.updateStudentFees(student, amount);
//        List<Student> students = service.getAllStudents();
        model.addAttribute("error", error);
        model.addAttribute("ID", userID);

        double feesPaid = student.getFees_paid();
        double feesDue = student.getFees_due();
        double feesTotal = feesDue + feesPaid;
//        model.addAttribute("studs", students);
        model.addAttribute("feesTotal", feesTotal);
        model.addAttribute("user_name", student.getFirst_name());
        model.addAttribute("feesDue", feesDue);
        model.addAttribute("feesPaid", feesPaid);
        return "fees";
    }
}
