package ru.academits.hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/hello/api/v1")
public class HelloWorldController {
    @RequestMapping(value = "hello", method = RequestMethod.GET)
    @ResponseBody
    public String sayHello(@RequestParam(required = false) String name) {
        return "hello, " + name;
    }
}


