package uni.system.my_project;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;
@RestController
@EnableAutoConfiguration
public class Example {
    @RequestMapping("/home")
    String home() {
        return "Hello World!";
    }
    public static void main(String[] args) {
        SpringApplication.run(Example.class, args);
    }
}
