package com.iris.system.response;

import com.iris.system.enums.Status;

import java.util.List;

public class ListFriendInvitationsResponse extends PaginatedCommonResponse {

    private List<FriendInvitationResponse> friendInvitations;
    private int page;
    private boolean hasNext;

    public ListFriendInvitationsResponse(List<FriendInvitationResponse> friendInvitations, int page, boolean hasNext) {
        super(page, hasNext);
        this.friendInvitations = friendInvitations;
    }
}