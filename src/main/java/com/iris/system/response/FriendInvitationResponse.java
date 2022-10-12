package com.iris.system.response;

import com.iris.system.model.FriendInvitation;
import com.iris.system.model.User;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class FriendInvitationResponse {
    private int friendInvitationId;
    private String username;
    private String nickname;
    private String message;
    private Date sendTime;

    public static FriendInvitationResponse from(FriendInvitation friendInvitation, User user) {
        return FriendInvitationResponse.builder()
                .friendInvitationId(friendInvitation.getId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .message(friendInvitation.getMessage())
                .sendTime(friendInvitation.getSendTime())
                .build();
    }
}
