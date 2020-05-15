package uni.system.webapp.fees;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import uni.system.webapp.tables.Student;

import javax.servlet.http.HttpServletRequest;

@Controller
public class FeesController {

    @Autowired
    FeesService service;

    @RequestMapping(path = "/fees", method = RequestMethod.GET)
    public String showModulePage(ModelMap model, HttpServletRequest request) {
        String userID = service.getID(request);

        //If no user ID in session, ask user to log back in
        if(userID == null) {
            return "redirect:login";
        }

        Student student = service.getStudent(userID);
        double feesPaid = student.getFees_paid();
        double feesDue = student.getFees_due();
        double feesTotal = feesDue + feesPaid;

        model.addAttribute("user_name", student.getFirst_name());
        model.addAttribute("feesTotal", feesTotal);
        model.addAttribute("feesDue", feesDue);
        model.addAttribute("feesPaid", feesPaid);
        model.addAttribute("ID", userID);
        return "fees";
    }

    @RequestMapping(path = "/fees", method = RequestMethod.POST)
    public String updateFees(ModelMap model, HttpServletRequest request, @RequestParam String amount) {
        String userID = service.getID(request);

        double fees_amount=0;
        if(amount.length()!=0){
            fees_amount = Double.parseDouble(amount);
        }

        //If no user ID in session, ask user to log back in
        if(userID == null) {
            return "redirect:login";
        }

        Student student = service.getStudent(userID);
        String error = service.updateStudentFees(student, fees_amount);
        model.addAttribute("error", error);
        model.addAttribute("ID", userID);

        double feesPaid = student.getFees_paid();
        double feesDue = student.getFees_due();
        double feesTotal = feesDue + feesPaid;
        model.addAttribute("feesTotal", feesTotal);
        model.addAttribute("user_name", student.getFirst_name());
        model.addAttribute("feesDue", feesDue);
        model.addAttribute("feesPaid", feesPaid);
        return "fees";
    }
}
