package uni.system.webapp.security;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/block")
public class BlockController {

    @GetMapping()
    public String showBlockPage() {
        return "block";
    }
}