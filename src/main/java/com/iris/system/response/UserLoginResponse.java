package com.iris.system.response;

import com.iris.system.enums.Status;

public class UserLoginResponse extends CommonResponse{

    private String loginToken;

    public UserLoginResponse(String loginToken) {
        super(Status.OK);
        this.loginToken = loginToken;
    }

    public String getLoginToken() {
        return loginToken;
    }
}
