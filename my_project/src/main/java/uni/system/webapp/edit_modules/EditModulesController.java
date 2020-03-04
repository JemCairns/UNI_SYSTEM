package uni.system.webapp.edit_modules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import uni.system.webapp.tables.Module;
import uni.system.webapp.tables.Topic;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

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
            model.addAttribute("registered_topics", new ArrayList<>());
            model.addAttribute("not_registered_topics", service.getAllTopics());
        }
        else{
            newModule=false;
            model.addAttribute("new_mod", false);

//            model.addAttribute("ID", userID);
//            System.out.println("module max: "+service.getModule(editModuleID).getMax_num_students());
            model.addAttribute("module", service.getModule(editModuleID));
            model.addAttribute("registered_topics", service.getRegisteredTopics(editModuleID));
            model.addAttribute("not_registered_topics", service.getNotRegisteredTopics(editModuleID));
        }

        return "edit_module";
    }





    @RequestMapping(path = "/edit_module", method = RequestMethod.POST)
    public String updateModule(ModelMap model, Module module, HttpSession session,
                               @RequestParam(value = "prev_topics", required = false) int[] prevIDs,
                               @RequestParam(value = "new_topics", required = false) int[] newIDs,
                               @RequestParam("modID") String modID) {
        String userID = (String) session.getAttribute("ID");
        String editModuleID = (String) session.getAttribute("editModuleID");
        System.out.println(userID);
        System.out.println("in post EM");
        System.out.println("max: " + module.getMax_num_students());

        if(userID == null) {
            return "redirect:login";
        }


        if(newModule){
            model.addAttribute("user_name", service.getUserName(userID));
            System.out.println(modID);
            module.setID(modID);
            if(module.getID().length()!=9 || module.getName().equals("") || module.getDescription().equals("")
                    || module.getMax_num_students()<10){
                model.addAttribute("errorMessage", "*Invalid module details");
                model.addAttribute("new_mod", true);
                Module moduleNew = new Module("", "", userID, 0, 120, "available", "");

                model.addAttribute("module", moduleNew);
                model.addAttribute("registered_topics", new ArrayList<>());
                model.addAttribute("not_registered_topics", service.getAllTopics());
                return "edit_module";
            }
            boolean added = service.saveModule(module);
            if(!service.saveModule(module)){
                model.addAttribute("errorMessage", "*Course code is not unique");
                return "edit_module";
            }

            if(newIDs != null) {
                for (int newID : newIDs) {
                    service.registerModuleForTopic(modID, newID);
                }
            }

            return "redirect:modules";

        }
        else {
            if(module.getMax_num_students()<10 || module.getDescription().equals("")){
                model.addAttribute("errorMessage", "*Invalid module details");
                return "edit_module";
            }

            for(Topic prevRegTopic : service.getRegisteredTopics(editModuleID)){
                boolean unregistered=true;
                if(prevIDs != null) {
                    for (int prevID : prevIDs) {
                        if (prevRegTopic.getTopic_ID() == prevID) {
                            unregistered = false;
                            break;
                        }
                    }
                }
                if(unregistered){
                    service.unregisterModuleFromTopic(editModuleID, prevRegTopic.getTopic_ID());
                }
            }
            if(newIDs != null) {
                for (int newID : newIDs) {
                    service.registerModuleForTopic(editModuleID, newID);
                }
            }

            service.updateModule(module);
            model.addAttribute("ID", userID);

            return "redirect:modules";
        }

    }



}
