package com.iris.system.response;

import com.iris.system.enums.Status;

public class PaginatedCommonResponse extends CommonResponse{

    private int page;
    private boolean hasNext;

    public PaginatedCommonResponse(int page, boolean hasNext) {
        super(Status.OK);
        this.page = page;
        this.hasNext = hasNext;
    }
}
