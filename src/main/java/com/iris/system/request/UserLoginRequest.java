package com.iris.system.request;

import lombok.Data;

@Data
public class UserLoginRequest {
    private String identification; // username or email
    private String password;
}
