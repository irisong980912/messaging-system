package com.iris.system.service;

import com.iris.system.DAO.UserDAO;
import com.iris.system.DAO.UserValidationCodeDAO;
import com.iris.system.enums.Gender;
import com.iris.system.enums.Status;
import com.iris.system.exception.MessageServiceException;
import com.iris.system.model.User;
import com.iris.system.model.UserValidationCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Date;


/*
 * handles the user request
 */
@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private UserValidationCodeDAO userValidationCodeDAO;

    @Autowired
    private EmailService emailService;

    // this method throws messageServiceException. Whoever calls it, must habdle the exception
    public void register(String username,
                         String password,
                         String repeatPassword,
                         String nickname,
                         String email,
                         String address,
                         Gender gender) throws Exception {
        //validations: switch
        if (!repeatPassword.equals(password)) {
            throw new MessageServiceException(Status.PASSWORDS_NOT_MATCHED);
        }
        if (password.length() < 10) {
            throw new MessageServiceException(Status.PASSWORD_TOO_SHORT);
        }

        // check if username already exists in the database
        if (this.userDAO.selectByUsername(username).size() >= 1) {
            throw new MessageServiceException(Status.USER_ALREADY_REGISTERED);
        }

//        // check email
        if (this.userDAO.selectByEmail(email).size() >= 1) {
            throw new MessageServiceException(Status.EMAIL_ALREADY_REGISTERED);
        }


        // var detects automatically the datatype of a variable based on the surrounding context
        // can be used as a local instance within a method, but cannot be used as a global variable
        var user = User.builder()
                .username(username)
                .password(password)
                .nickname(nickname)
                .email(email)
                .address(address)
                .gender(gender)
                .registerTime(new Date())
                .isValid(false)
                .build();
        // the User (model / value data) class has included the annotation @Builder
        // builder class set everything to private final, does not allow change after initialization (use multiple constructors to realize this)
        // object immutability

        this.userDAO.insert(user);

        //1. generate a validation code and store
        // need to get the user ID
        String validationCode = RandomStringUtils.randomNumeric(6);
        var userValidationCode = UserValidationCode.builder()
                .userId(user.getId())
                .validationCode(validationCode)
                .build();


        this.userValidationCodeDAO.insert(userValidationCode);

        //2. send an email with the validation code to `email`
//        this.emailService.sendEmail(user.getEmail(), validationCode);

        // and then activate the user
    }

    //3. send a request to another API /users/activate {username: "", validationCode: ""}
    public void activate(String username, String validationCode) throws MessageServiceException{

        // get the userID by username
        var users = this.userDAO.selectByUsername(username); // return a list
        if (users == null || users.size() == 0) {
            throw new MessageServiceException(Status.USER_NOT_EXIST);
        }

        var user = users.get(0);

        var userValidationCode = this.userValidationCodeDAO.selectOneByUserId(user.getId());
        if (userValidationCode == null) {
            throw new MessageServiceException(Status.UNKNOWN);
        }

        if (!validationCode.equals(userValidationCode.getValidationCode())) {
            throw new MessageServiceException(Status.VALIDATION_FAILED);
        }

        this.userValidationCodeDAO.delete(userValidationCode.getId());
        this.userDAO.updateToValid(user.getId());

    }

}














