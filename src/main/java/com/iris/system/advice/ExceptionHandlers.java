package com.iris.system.advice;

import com.iris.system.exception.MessageServiceException;
import com.iris.system.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/*
 * throws: signature of the method
 * throw: throws the exception explicitly from a method or a block of code
 *
 * controller advice: an interceptor of exceptions thrown by methods annotated with @RequestMapping
 *                   集中处理exception handler (beautiful way of implementing try and catch)
 *
 * a centralized point for exceptions. (different controller might throw the same exception. So cannot write
 * exception handler in the same method.)
 */

/* Response Entity
 * responseEntity represents the whole HTTP response: status code, headers, and body
 */

// any exceptions that require controller to handle
@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(MessageServiceException.class)
    @ResponseBody
    public ResponseEntity<CommonResponse> handleMessageServiceException(MessageServiceException e) {
        var commonResponse = new CommonResponse(e.getStatus());
        return new ResponseEntity<>(commonResponse, HttpStatus.BAD_REQUEST);


    }
//
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<CommonResponse> handleException(Exception e) {
        var commonResponse = new CommonResponse(9999, e.getMessage());
        return new ResponseEntity<>(commonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
