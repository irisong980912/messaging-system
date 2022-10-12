package com.iris.system.enums;

// unfortunately, Lombook @Value only supports class.
// customized response status
public enum Status {

    // define the enum constants
    OK(1000, "Successful."),
    PASSWORDS_NOT_MATCHED(1001, "Passwords are not matched."),
    PASSWORD_TOO_SHORT(1002, "Passwords is too short."),
    USER_ALREADY_REGISTERED(1003, "User is already registered."),
    EMAIL_ALREADY_REGISTERED(1004, "This email is already registered."),
    USER_NOT_EXIST(1005, "User doesn't exist."),

    INTERNAL_SERVICE_ERROR(1006, "Internal service error."),

    VALIDATION_FAILED(1007, "Validation code does not match."),

    WRONG_PASSWORD(1008, "Wrong password"),
    LOGIN_REQUIRED(1009, "You need to log in"),
    INACTIVATED_USER(1010, "Inactivated user");


    private int code;
    private String message;

    Status(int code, String message) {
        this.code = code;
        this.message = message;
    }

    // define getter

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
