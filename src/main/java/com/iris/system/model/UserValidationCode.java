package com.iris.system.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserValidationCode {
    int id;
    int userId;
    String validationCode;
}
