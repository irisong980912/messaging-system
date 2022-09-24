package com.iris.system.request;

import com.iris.system.enums.Gender;
import lombok.Data;

@Data
public class ActivateUserRequest {
    private String username; // alphanumeric
    private String validationCode; // check if the code are the same
}
