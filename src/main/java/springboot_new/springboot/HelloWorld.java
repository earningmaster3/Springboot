package springboot_new.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloWorld {
    @GetMapping("/hello")
    public String hello(){
        return "Hello world";
    }
    @GetMapping("/hello/{name}")
    public String greet(@PathVariable String name){
        return "Hello " + name;
    }

}
