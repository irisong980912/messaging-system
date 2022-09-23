package com.iris.system.controller;


import com.iris.system.request.RegisterUserRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class UserController {

    // the request body annotation maps the HttpRequest body to a transfer or domain object
    // enabling automatic deserialization of the inbound HttpRequest body onto a Java Object

    // srping automatically deserializes the JSON object into a Java Type
    @PostMapping("/register") // deserialize: json/text/xml -> object in memory
    public void register(@RequestBody RegisterUserRequest registerUserRequest) throws Exception{
        // validate the input
        if (!registerUserRequest.getRepeatPassword().equals(registerUserRequest.getPassword())) {
            throw new Exception();
        }

        // todo:store a user into the user table
    }
}
