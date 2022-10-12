package com.iris.system.model;

import com.iris.system.enums.FriendInvitationStatus;
import lombok.Builder;
import lombok.Value;

import java.util.Date;

@Value
@Builder
public class FriendInvitation {
    int id;
    int senderUserId;
    int receiverUserId;
    Date sendTime;
    Date processTime;
    FriendInvitationStatus status;
    String message;
}
