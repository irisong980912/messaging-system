package com.iris.system.exception;

import com.iris.system.enums.Status;

// handles system exceptions?
// throws exception and halt the process
// commonResponse is just telling a message
public class MessageServiceException extends Exception{

    private Status status;

    public MessageServiceException(Status status) {
        super(status.getMessage());
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }
}
