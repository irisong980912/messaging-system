package com.iris.system.enums;

// unfortunately, Lombook @Value only supports class.
// customized response status
public enum Status {

    // define the enum constants
    OK(1000, "successful"),
    PASSWORDS_NOT_MATCHED(1001, "Passwords are not matched."),
    PASSWORD_TOO_SHORT(1002, "Passwords is too short."),
    USER_ALREADY_REGISTERED(1003, "User is already registered."),
    USER_NO_EMAIL(1004, "User has no email."),
    USER_NOT_EXIST(1005, "User doesn't exist."),

    UNKNOWN(1006, "Unknown error."),

    VALIDATION_FAILED(1007, "Validation code does not match.");



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
