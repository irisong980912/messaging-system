package com.iris.system.controller;

import com.iris.system.service.GroupChatService;
import com.iris.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GroupChatController {

    @Autowired
    private GroupChatService groupChatService;
    @Autowired
    private UserService userService;

}
