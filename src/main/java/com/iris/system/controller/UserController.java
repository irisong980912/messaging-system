package com.iris.system.controller;


import com.iris.system.enums.Status;
import com.iris.system.exception.MessageServiceException;
import com.iris.system.request.ActivateUserRequest;
import com.iris.system.request.RegisterUserRequest;
import com.iris.system.request.UserLoginRequest;
import com.iris.system.response.CommonResponse;
import com.iris.system.response.UserLoginResponse;
import com.iris.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {


    // dependency injection for testability.
    // no new. Decoupling
    @Autowired
    private UserService userService;

    @PostMapping("/register") // deserialize: json/text/xml -> object in memory
    public CommonResponse register(@RequestBody RegisterUserRequest registerUserRequest) throws Exception {
        // validate the input
        // because it calls register, so must handle the exception threw by register.
        // but we don't see "throw catch" here. Why? because it is handled by "exception handlers"
        this.userService.register(registerUserRequest.getUsername(),
                registerUserRequest.getPassword(),
                registerUserRequest.getRepeatPassword(),
                registerUserRequest.getNickname(),
                registerUserRequest.getEmail(),
                registerUserRequest.getAddress(),
                registerUserRequest.getGender());

        // throw no exception
        return new CommonResponse(Status.OK);
    }

    // activate the user after clicking on the email
    @PostMapping("/activate")
    public CommonResponse activate(@RequestBody ActivateUserRequest activateUserRequest) throws Exception {
        this.userService.activate(activateUserRequest.getUsername(),
                activateUserRequest.getValidationCode());
        return new CommonResponse(Status.OK);

    }

    @PostMapping("/login")
    public UserLoginResponse login(@RequestBody UserLoginRequest userLoginRequest) throws Exception {
        var loginToken = this.userService.login(userLoginRequest.getIdentification(), userLoginRequest.getPassword());
        return new UserLoginResponse(loginToken);
    }


    public static void main(String[] args) {
        try {
            throw new NullPointerException();
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName());
        }
    }


}

/*
    Lesson 2: need to hide the implementations from the controller.
    -> put the following into user

    if (!registerUserRequest.getRepeatPassword().equals(registerUserRequest.getPassword())) {
        throw new Exception();
    }

 */


/* @RequestBody
     the request body annotation maps the HttpRequest body to a transfer or domain object
     enabling automatic deserialization of the inbound HttpRequest body onto a Java Object
     spring automatically deserializes the JSON object into a Java Type
 */

/* @Autowired
    Allow injecting the object dependency implicitly. Internally uses setter or constructor injection
    ex. userRequest
 */

/* Dependency Injection
    1. the service to use (UserService)
    2. the client that uses the service (controller)
    3. interface that provides that service and used by the client (http)
    3. injector that creates a service instance and inject to client (@Autowired)
 */

