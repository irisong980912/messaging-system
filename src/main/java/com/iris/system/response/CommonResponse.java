package com.iris.system.response;

import com.iris.system.enums.Status;
import lombok.Data;

@Data
public class CommonResponse {

    int code;
    String message;

    // service send in a status. determine what are the attributes
    public CommonResponse(Status status) {
        this.code = status.getCode();
        this.message = status.getMessage();
    }

    // send in a status that is not defined in the enum
    public CommonResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
