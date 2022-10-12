package com.iris.system.controller;

import com.iris.system.DAO.FriendInvitationDAO;
import com.iris.system.constant.Constant;
import com.iris.system.enums.FriendInvitationStatus;
import com.iris.system.enums.Status;
import com.iris.system.model.FriendInvitation;
import com.iris.system.model.User;
import com.iris.system.response.CommonResponse;
import com.iris.system.response.FriendInvitationResponse;
import com.iris.system.response.ListFriendInvitationsResponse;
import com.iris.system.service.FriendService;
import com.iris.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/friends")
public class FriendController {


    @Autowired
    private UserService userService;

    @Autowired
    private FriendInvitationDAO friendInvitationDAO;

    @Autowired
    private FriendService friendService;

    @PostMapping("/add")
    public CommonResponse addFriend(@RequestBody String username, @RequestHeader String loginToken) throws Exception {
        User user = this.userService.authenticate(loginToken);
        return null;
    }

    @GetMapping("/listInvitations")
    public ListFriendInvitationsResponse listFriendInvitations(@RequestHeader String loginToken,
                                                               @RequestParam(required = false, defaultValue = "1") int page) throws Exception {
        User user = this.userService.authenticate(loginToken);
        List<FriendInvitationResponse> friendInvitationResponses = new ArrayList<>();
        List<FriendInvitation> friendInvitations = this.friendService.listFriendInvitations(user, page);
        for (FriendInvitation friendInvitation : friendInvitations) {
            User sender = this.userService.getUserById(friendInvitation.getSenderUserId());
            FriendInvitationResponse friendInvitationResponse = FriendInvitationResponse.from(friendInvitation, sender);
            friendInvitationResponses.add(friendInvitationResponse);
        }
        return new ListFriendInvitationsResponse(friendInvitationResponses.subList(0, Constant.PAGE_SIZE),
                page,
                friendInvitations.size() > Constant.PAGE_SIZE);
    }

    @PostMapping("/accept")
    public CommonResponse acceptFriendInvitation(@RequestHeader String loginToken,
                                                 @RequestParam int friendInvitationId) throws Exception {

        // check if login
        userService.authenticate(loginToken);

        // update the invitation status to accept
        this.friendInvitationDAO.updateFriendInvitationStatus(friendInvitationId, FriendInvitationStatus.ACCEPTED);


        return new CommonResponse(Status.OK);
    }

    @PostMapping("/reject")
    public CommonResponse acceptFriendInvitation(@RequestHeader String loginToken,
                                                 @RequestParam int friendInvitationId,
                                                 @RequestParam String message) throws Exception {
        return new CommonResponse(Status.OK);
    }
}
