package uni.system.webapp.edit_modules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uni.system.webapp.tables.Module;

import javax.servlet.http.HttpSession;

@Controller
public class EditModulesController {

    private boolean newModule=false;

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

        if(editModuleID.equals("new_module")){
            newModule=true;
            model.addAttribute("new_mod", true);
            Module module = new Module("", "", userID, 0, 120, "available", "");

            model.addAttribute("module", module);
        }
        else{
            newModule=false;
            model.addAttribute("new_mod", false);

//            model.addAttribute("ID", userID);
//            System.out.println("module max: "+service.getModule(editModuleID).getMax_num_students());
            model.addAttribute("module", service.getModule(editModuleID));
        }

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

        if(newModule){
            if(module.getID().length()!=9 || module.getName().equals("") || module.getDescription().equals("")
                    || module.getMax_num_students()<10){
                model.addAttribute("errorMessage", "*Invalid module details");
                return "edit_module";
            }
            boolean added = service.saveModule(module);
            if(!service.saveModule(module)){
                model.addAttribute("errorMessage", "*Course code is not unique");
                return "edit_module";
            }

            model.addAttribute("ID", userID);
            return "redirect:modules";

        }
        else {
            if(module.getMax_num_students()<10 || module.getDescription().equals("")){
                model.addAttribute("errorMessage", "*Invalid module details");
                return "edit_module";
            }

            service.updateModule(module);
            model.addAttribute("ID", userID);

            return "redirect:modules";
        }

    }



}
