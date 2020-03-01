package uni.system.webapp.edit_modules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import uni.system.webapp.modules.ModulesService;
import uni.system.webapp.tables.*;

import javax.servlet.http.HttpSession;
import java.util.List;

public class EditModulesController {

    @Autowired
    EditModulesService service;

    @RequestMapping(path = "/edit_module", method = RequestMethod.GET)
    public String showEditModulePage(ModelMap model, HttpSession session) {
        String userID = (String) session.getAttribute("ID");
        String editModuleID = (String) session.getAttribute("editModuleID");

        System.out.println("module id: "+editModuleID);

        if(userID == null) {
            return "redirect:login";
        }


        model.addAttribute("user_name", service.getUserName(userID));

        model.addAttribute("ID", userID);
        System.out.println("module name: "+service.getModule(editModuleID).getName());
        model.addAttribute("module", service.getModule(editModuleID));

        return "edit_module";
    }





    @RequestMapping(path = "/edit_module", method = RequestMethod.POST)
    public String updateModule(ModelMap model, HttpSession session) {
        String userID = (String) session.getAttribute("ID");
        System.out.println(userID);
        System.out.println("in post EM");

        if(userID == null) {
            return "redirect:login";
        }

        return "edit_module";
    }



}
