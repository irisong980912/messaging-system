package com.iris.system.service;

import java.util.List;

import com.iris.system.DAO.UserDAO;
import com.iris.system.DAO.UserValidationCodeDAO;
import com.iris.system.enums.Gender;
import com.iris.system.enums.Status;
import com.iris.system.exception.MessageServiceException;
import com.iris.system.model.User;
import com.iris.system.model.UserValidationCode;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

;

/* Mockito
 * A mocking framework for JUnit.
 * can verify actions, do stubbing before the actual execution
 */

/* Mock vs InjectMock
 * @Mock creates a mock object
 * @InjectMock creates an instance of the class and injects the mocks that are created with the
 * @Mock annotations into this instance.
 */

/* MockMVC v.s. Mockito (Integration v.s. Unit)
 * Integration testing test controllers? (MockMVC)
 *      - writes to I/O or database
 * Unit testing test individual functions? (Mockito, exception verify)
 *      - not affected by web services or database (mock external services). need only the source code (stub & mock )
 *      - requirements: maven, dependency injection, POM.xml files
 */

/*
 * Stubs: pre-programmed return values
 * Mock: classes with side effects that needs to be examined.
 *       (classes that sends data to external servers or database)
 * Verify: Both tests do not contain the normal JUnit assert statements.
 *          Instead, we use the verify directive which examines the mocks
 *          after each run and passes the test if a method was called with
 *          the specified argument.
 */


// what is the difference between unit and integration testing here
// Integration Testing: mockMVC 创建接口, testing controllers

/*

 */

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserDAO userDAO;
    @Mock
    private UserValidationCodeDAO userValidationCodeDAO;
    @InjectMocks
    private UserService userService; // this.userService = new UserService(); this.userService.userDAO() = userDAP

    private static final int USER_ID = 1;
    private final static String USERNAME = "username";
    private final static String PASSWORD = "password10";
    private final static String MISMATCHED_REPEAT_PASSWORD = "mismatched-password";
    private final static String NICKNAME = "nickname";
    private final static String EMAIL = "email";
    private final  static Gender GENDER = Gender.MALE;
    private final static String ADDRESS = "address";
    private static final String VALIDATION_CODE = "123123";
    private static final int USER_VALIDATION_CODE_ID = 2;


    // test for each function
    @Test
    public void testRegister_mismatchedPasswords_messageServiceExceptionThrown() throws Exception {

        // see if it throws the exception
        var messageException = assertThrows(MessageServiceException.class,
                                        () -> this.userService.register(USERNAME,
                                                                        PASSWORD,
                                                                        MISMATCHED_REPEAT_PASSWORD,
                                                                        NICKNAME,
                                                                        EMAIL,
                                                                        ADDRESS,
                                                                        GENDER));
        assertEquals(Status.PASSWORDS_NOT_MATCHED, messageException.getStatus());
    }

    @Test
    public void testRegister_usernameAlreadyExists_messageServiceExceptionThrown() throws Exception {

        User user = User.builder()
                .username(USERNAME)
                .build();

        // we don't want to actually call the http to post a user.
        // assume that the controller has not been initiated. mock the behaviour using Mockito
        // right now, assume user has been inserted. Now we need to select the user by its username\
        when(this.userDAO.selectByUsername(USERNAME)).thenReturn(List.of(user));


        // see if it throws the exception
        var messageException = assertThrows(MessageServiceException.class,
                () -> this.userService.register(USERNAME,
                                                PASSWORD,
                                                PASSWORD,
                                                NICKNAME,
                                                EMAIL,
                                                ADDRESS,
                                                GENDER));
        assertEquals(Status.USER_ALREADY_REGISTERED, messageException.getStatus());
        // verify: check if the function stubbed was called with correct arguments
        verify(this.userDAO).selectByUsername(USERNAME);
    }

    public void testRegister_exceptionThrownWhenInsertUsers_messageServiceExceptionThrown() throws Exception {
        // when select by username or by email, return an empty list
        when(this.userDAO.selectByUsername((USERNAME))).thenReturn(List.of());
        when(this.userDAO.selectByEmail(EMAIL)).thenReturn(List.of());
        // then, when insert any users, throw exception
        doThrow(NullPointerException.class).when(this.userDAO).insert(any(User.class));

        var messageException = assertThrows(MessageServiceException.class,
                () -> this.userService.register(USERNAME,
                                                PASSWORD,
                                                PASSWORD,
                                                NICKNAME,
                                                EMAIL,
                                                ADDRESS,
                                                GENDER));
        assertEquals(Status.INTERNAL_SERVICE_ERROR, messageException.getStatus());
        verify(this.userDAO).selectByUsername(USERNAME);
        verify(this.userDAO).selectByEmail(EMAIL);
        verify(this.userDAO).insert(any(User.class));
    }

    @Test
    public void testRegister_happyCase() throws Exception {
        when(this.userDAO.selectByUsername(USERNAME)).thenReturn(List.of());
        when(this.userDAO.selectByEmail(EMAIL)).thenReturn(List.of());

        this.userService.register(USERNAME,
                PASSWORD,
                PASSWORD,
                NICKNAME,
                EMAIL,
                ADDRESS,
                GENDER);

        verify(this.userDAO).selectByUsername(USERNAME);
        verify(this.userDAO).selectByEmail(EMAIL);
        verify(this.userDAO).insert(any(User.class));
        verify(this.userValidationCodeDAO).insert(any(UserValidationCode.class));
    }

    @Test
    public void testActivate_happyCase() throws Exception {
        User user = User.builder()
                .username(USERNAME)
                .id(USER_ID)
                .build();

        UserValidationCode userValidationCode = UserValidationCode.builder()
                .id(USER_VALIDATION_CODE_ID)
                .validationCode(VALIDATION_CODE)
                .userId(USER_ID)
                .build();

        when(this.userDAO.selectByUsername(USERNAME)).thenReturn(List.of(user));
        when(this.userValidationCodeDAO.selectOneByUserId(USER_ID)).thenReturn(userValidationCode);

        this.userService.activate(USERNAME, VALIDATION_CODE);

        verify(this.userDAO).selectByUsername(USERNAME);
        verify(this.userValidationCodeDAO).selectOneByUserId(USER_ID);
        verify(this.userDAO).updateToValid(USER_ID);
        verify(this.userValidationCodeDAO).delete(USER_VALIDATION_CODE_ID);



    }






}
