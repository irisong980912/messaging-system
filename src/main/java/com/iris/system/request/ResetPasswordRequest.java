package com.iris.system.request;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String username; // alphanumeric
    private String validationCode; // check if the code are the same
    private String newPassword;
}
