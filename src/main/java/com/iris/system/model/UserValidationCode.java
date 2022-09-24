package com.iris.system.model;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class UserValidationCode {
    private int id;
    private int userID;
    private String validationCode;
}
