package com.iris.system.response;

import java.util.List;

public class ListFriendResponse extends PaginatedCommonResponse{
     List<UserResponse> userResponses;

    public ListFriendResponse(List<UserResponse> userResponses, int page, boolean hasNext) {
        super(page, hasNext);
        this.userResponses = userResponses;
    }
}
