package com.iris.system.controller;

import com.iris.system.Person;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

    @PostMapping("/hi")
    public String hello(@RequestParam(defaultValue = "Alice", required = false) String name,
                            @RequestHeader("User-Agent") String userAgent,
                            @RequestBody String body) {
        return "hello " + name + " from " + userAgent + " body: " + body;
    }

    @PostMapping("/greeting")
    public Person greeting(@RequestBody Person person) {

        return person;
    }

    @GetMapping("/superHello/{name}")
    @ResponseBody
    public String getName(@PathVariable("name") String personName) {

        return "Hello " + personName + "!";
    }


}
