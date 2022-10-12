package com.iris.system.service;

import com.iris.system.enums.Status;
import com.iris.system.exception.MessageServiceException;
import com.iris.system.model.GroupChat;
import com.iris.system.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class GroupChatService {

    @Autowired
    private FriendService friendService;

    public void addGroupChat(User creator, String name, String description, List<Integer> friendUserIds) throws Exception {
        // validation
        for (Integer friendUserId: friendUserIds) {
            if (!this.friendService.isFriend(creator.getId(), friendUserId)) {
                throw new MessageServiceException(Status.USER_NOT_EXIST);
            }
        }

        var groupChat = GroupChat.builder()
                .name(name)
                .description(description)
                .createTime(new Date())
                .build();
    }

}
