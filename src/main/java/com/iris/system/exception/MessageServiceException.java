package com.iris.system.exception;

import com.iris.system.enums.Status;

// handles system exceptions?
// throws exception and halt the process
// commonResponse is just telling a message
// a customized exception that must be handled -> try and catch

//Custom exceptions provide you the flexibility to add attributes and methods
//        that are not part of a standard Java exception.
//        These can store additional information,
//        like an application-specific error code, or provide utility methods
//        that can be used to handle or present the exception to a user.



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
