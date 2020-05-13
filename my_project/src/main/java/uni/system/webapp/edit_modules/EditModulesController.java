package uni.system.webapp.edit_modules;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import uni.system.webapp.logger.Logging;
import uni.system.webapp.tables.Module;
import uni.system.webapp.tables.Topic;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class EditModulesController {

    private boolean newModule=false;

    @Autowired
    EditModulesService service;

    @RequestMapping(value = "/edit_module", method = RequestMethod.GET)
    public String showEditModulePage(ModelMap model, HttpSession session) {
        String userID = (String) session.getAttribute("ID");
        String editModuleID = (String) session.getAttribute("editModuleID");

        //If no user ID in session, ask user to log back in
        if(userID == null) {
            return "redirect:login";
        }

        model.addAttribute("user_name", service.getUserName(userID));

        // Create a new module
        if(editModuleID.equals("new_module")){
            newModule=true;
            model.addAttribute("new_mod", true);
            Module module = new Module("", "", userID, 0, 120, "available", "");

            model.addAttribute("module", module);
            model.addAttribute("registered_topics", new ArrayList<>());
            model.addAttribute("not_registered_topics", service.getAllTopics());
        }
        // Edit an existing module
        else{
            newModule=false;
            model.addAttribute("new_mod", false);
            model.addAttribute("module", service.getModule(editModuleID));
            model.addAttribute("registered_topics", service.getRegisteredTopics(editModuleID));
            model.addAttribute("not_registered_topics", service.getNotRegisteredTopics(editModuleID));
        }

        return "edit_module";
    }

    @RequestMapping(path = "/edit_module", method = RequestMethod.POST)
    public String updateModule(ModelMap model, Module module, HttpSession session,
                               @RequestParam(value = "prev_topics", required = false) int[] prevIDs,
                               @RequestParam(value = "new_topics", required = false) int[] newIDs) {
        String userID = (String) session.getAttribute("ID");
        String editModuleID = (String) session.getAttribute("editModuleID");

        //If no user ID in session, ask user to log back in
        if(userID == null) {
            return "redirect:login";
        }


        if(newModule){
            model.addAttribute("user_name", service.getUserName(userID));

            //Check all values are correct
            if(module.getID().length()!=9 || module.getName().equals("") || module.getDescription().equals("") || module.getMax_num_students()<10){
                model.addAttribute("errorMessage", "*Invalid module details");
                model.addAttribute("new_mod", true);
                model.addAttribute("registered_topics", new ArrayList<>());
                model.addAttribute("not_registered_topics", service.getAllTopics());
                return "edit_module";
            }

            if(service.saveModule(module)){
                model.addAttribute("errorMessage", "*Course code is not unique");
                return "edit_module";
            }
            else{
                // staff created module
                Logging.getInstance().info("Staff with id=" + userID + " created module with id=" + module.getID() + ".");
            }

            if(newIDs != null) {
                for (int newID : newIDs) {
                    service.registerModuleForTopic(module.getID(), newID);
                }
            }

        }
        else {
            if(module.getMax_num_students()<10 || module.getDescription().equals("")){
                model.addAttribute("errorMessage", "*Invalid module details");
                return "edit_module";
            }

            Module oldModule = service.getModule(module.getID());

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
                    // staff removed topic from module
                    Logging.getInstance().info("Staff with id=" + userID + " removed topic with id=" + prevRegTopic.getTopic_ID()
                            + " from module with id=" + module.getID() + ".");
                }
            }
            if(newIDs != null) {
                for (int newID : newIDs) {
                    service.registerModuleForTopic(editModuleID, newID);
                    // staff added topic to module
                    Logging.getInstance().info("Staff with id=" + userID + " added topic with id=" + newID
                            + " to module with id=" + editModuleID + ".");
                }
            }


            service.updateModule(module);
            model.addAttribute("ID", userID);
            // staff edited module details
            try {
                Logging.getInstance().info("Staff with id=" + userID + " edited details of module with id=" + module.getID()
                        + " from " + new ObjectMapper().writeValueAsString(oldModule) + " to " + new ObjectMapper().writeValueAsString(module) + ".");
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

        }
        return "redirect:modules";
    }
}
