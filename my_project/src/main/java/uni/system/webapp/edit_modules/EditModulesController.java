package uni.system.webapp.edit_modules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import uni.system.webapp.modules.ModulesService;
import uni.system.webapp.tables.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class EditModulesController {

    @Autowired
    EditModulesService service;

    @RequestMapping(value = "/edit_module", method = RequestMethod.GET)
    public String showEditModulePage(ModelMap model, HttpSession session) {
        String userID = (String) session.getAttribute("ID");
        String editModuleID = (String) session.getAttribute("editModuleID");

        System.out.println("module id: "+editModuleID);

        if(userID == null) {
            return "redirect:login";
        }


        model.addAttribute("user_name", service.getUserName(userID));

        model.addAttribute("ID", userID);
        System.out.println("module max: "+service.getModule(editModuleID).getMax_num_students());
        model.addAttribute("module", service.getModule(editModuleID));

        return "edit_module";
    }





    @RequestMapping(path = "/edit_module", method = RequestMethod.POST)
    public String updateModule(ModelMap model, Module module, HttpSession session) {
        String userID = (String) session.getAttribute("ID");
        System.out.println(userID);
        System.out.println("in post EM");
        System.out.println("max: " + module.getMax_num_students());

        if(userID == null) {
            return "redirect:login";
        }

        if(module.getMax_num_students()<10 || module.getDescription().equals("")){
            model.addAttribute("errorMessage", "*Invalid module details");
            return "edit_module";
        }

        service.updateModule(module);

        return "redirect:modules";
    }



}
