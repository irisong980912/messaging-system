package com.iris.system.service;

import com.iris.system.DAO.FriendInvitationDAO;
import com.iris.system.constant.Constant;
import com.iris.system.enums.FriendInvitationStatus;
import com.iris.system.model.FriendInvitation;
import com.iris.system.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendService {

    @Autowired
    private FriendInvitationDAO friendInvitationDAO;

    // pagination
    public List<FriendInvitation> listFriendInvitations(User user, int page) {
        int start = (page - 1) * Constant.PAGE_SIZE;
        return this.friendInvitationDAO.selectByReceiverUserId(user.getId(), start, Constant.PAGE_SIZE + 1);
    }

    public boolean isFriend(int id, int friendUserId) {
        // check if there's a accepted invitation for them
        FriendInvitation friendInvitation = friendInvitationDAO.selectOneInvitation(id, friendUserId);
        return friendInvitation.getStatus() == FriendInvitationStatus.ACCEPTED;

    }
}