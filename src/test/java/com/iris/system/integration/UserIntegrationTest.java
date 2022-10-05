package com.iris.system.integration;


// software testing
/* integration test 集成测试
 *  Individual software components are tested. Typically performed by the developer
 *
 */

/* unit testing 单元测试
 * The process of testing the interface between two software units or modules
 * Focuses on the correctness of the interface (all modules)
 */



/* MockMVC

 */

import com.iris.system.dao.TestUserDAO;
import com.iris.system.dao.TestUserValidationCodeDAO;
import com.iris.system.enums.Gender;
import com.iris.system.model.User;
import com.iris.system.model.UserValidationCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class UserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestUserDAO testUserDAO;

    @Autowired
    private TestUserValidationCodeDAO testUserValidationCodeDAO;

    // before each test, we need to clean up the old data
    @BeforeEach
    public void cleanUpOldData() {
        this.testUserDAO.deleteAll();
        this.testUserValidationCodeDAO.deleteAll();
    }


    // does not know the internal design
    @Test // begin the integration test. Test the methods in the controller
    public void testRegister_happyCase() throws Exception {

        var requestBody = "{\n" +
                "    \"username\": \"Stephen12\",\n" +
                "    \"nickname\": \"George\",\n" +
                "    \"address\": \"Canada\",\n" +
                "    \"email\": \"fanjianjin@gmail.com\",\n" +
                "    \"gender\": \"MALE\",\n" +
                "   \"password\": \"11111111111111111111111111111111111\",\n" +
                "   \"repeatPassword\": \"11111111111111111111111111111111111\"\n" +
                "}";
        this.mockMvc.perform(post("/users/register")
                    .content(requestBody)
                    .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-TYpe", "application/json"))
                .andExpect(jsonPath("$.code").value(1000))
                .andExpect(jsonPath("$.message").value("Successful."));

        // select from the DAO class
        List<User> users = this.testUserDAO.selectByUsername("Stephen12");
        assertNotNull(users); // users != null
        assertEquals(1, users.size());

        User user = users.get(0);
        assertEquals("Stephen12", user.getUsername());
        assertEquals(Gender.MALE, user.getGender());

        UserValidationCode userValidationCode = this.testUserValidationCodeDAO.selectOneByUserId(user.getId());
        assertNotNull(userValidationCode);
        assertEquals(user.getId(), userValidationCode.getUserId());
        assertEquals(6, userValidationCode.getValidationCode().length());
    }

    @Test
    public void testRegister_passwordNotMatched_returnsBadRequest() throws Exception { // target_scenario_expectedResult
        var requestBody = "{\n" +
                "    \"username\": \"Stephen12\",\n" +
                "    \"nickname\": \"George\",\n" +
                "    \"address\": \"Canada\",\n" +
                "    \"email\": \"fanjianjin@gmail.com\",\n" +
                "    \"gender\": \"MALE\",\n" +
                "   \"password\": \"xx\",\n" +
                "   \"repeatPassword\": \"11111111111111111111111111111111111\"\n" +
                "}";
        this.mockMvc.perform(post("/users/register")
                        .content(requestBody)
                        .contentType("application/json"))
                .andExpect(status().isBadRequest()) // HTTP status == 200
                .andExpect(header().string("Content-Type", "application/json")) // Content-Type = ?
                .andExpect(jsonPath("$.code").value(1001))
                .andExpect(jsonPath("$.message").value("Passwords are not matched."));

        List<User> users = this.testUserDAO.selectByUsername("Stephen12");
        assertNotNull(users); // users != null
        assertTrue(users.isEmpty()); // expected, actual
    }

    @Test
    public void testRegister_passwordTooShort_returnsBadRequest() throws Exception { // target_scenario_expectedResult
        var requestBody = "{\n" +
                "    \"username\": \"Stephen12\",\n" +
                "    \"nickname\": \"George\",\n" +
                "    \"address\": \"Canada\",\n" +
                "    \"email\": \"fanjianjin@gmail.com\",\n" +
                "    \"gender\": \"MALE\",\n" +
                "   \"password\": \"xx\",\n" +
                "   \"repeatPassword\": \"xx\"\n" +
                "}";
        this.mockMvc.perform(post("/users/register")
                        .content(requestBody)
                        .contentType("application/json"))
                .andExpect(status().isBadRequest()) // HTTP status == 200
                .andExpect(header().string("Content-Type", "application/json")) // Content-Type = ?
                .andExpect(jsonPath("$.code").value(1002))
                .andExpect(jsonPath("$.message").value("Passwords is too short."));

        List<User> users = this.testUserDAO.selectByUsername("Stephen12");
        assertNotNull(users); // users != null
        assertTrue(users.isEmpty()); // expected, actual
    }

    @Test
    public void testRegister_userAlreadyRegistered_returnsBadRequest() throws Exception {

        // insert into the database directly.
        // TODO: should I call register?
        var user = User.builder()
                .username("Stephen12")
                .nickname("George")
                .address("Canada")
                .email("fanjianjin@gmail.com")
                .gender(Gender.MALE)
                .password("11111111111111111111111111111111111")
                .registerTime(new Date())
                .isValid(false)
                .build();

        this.testUserDAO.insert(user);

        // --

        var requestBody = "{\n" +
                "    \"username\": \"Stephen12\",\n" +
                "    \"nickname\": \"George\",\n" +
                "    \"address\": \"Canada\",\n" +
                "    \"email\": \"fanjianjin@gmail.com\",\n" +
                "    \"gender\": \"MALE\",\n" +
                "   \"password\": \"11111111111111111111111111111111111\",\n" +
                "   \"repeatPassword\": \"11111111111111111111111111111111111\"\n" +
                "}";

        this.mockMvc.perform(post("/users/register")
                        .content(requestBody)
                        .contentType("application/json"))
                .andExpect(status().isBadRequest()) // HTTP status == 200
                .andExpect(header().string("Content-Type", "application/json")) // Content-Type = ?
                .andExpect(jsonPath("$.code").value(1003))
                .andExpect(jsonPath("$.message").value("User is already registered."));

        List<User> users = this.testUserDAO.selectByUsername("Stephen12");
        assertNotNull(users); // users != null
        assertEquals(1, users.size()); // has previous

        // need to delete this data, since has no validation code
        this.testUserDAO.deleteAll();

    }

    @Test
    public void testRegister_emailAlreadyRegistered_returnsBadRequest() throws Exception {

        // insert
        var user = User.builder()
                .username("test1")
                .nickname("test1")
                .address("Canada")
                .email("fanjianjin@gmail.com")
                .gender(Gender.MALE)
                .password("11111111111111111111111111111111111")
                .registerTime(new Date())
                .isValid(false)
                .build();

        this.testUserDAO.insert(user);

        var requestBody = "{\n" +
                "    \"username\": \"Stephen12\",\n" +
                "    \"nickname\": \"George\",\n" +
                "    \"address\": \"Canada\",\n" +
                "    \"email\": \"fanjianjin@gmail.com\",\n" +
                "    \"gender\": \"MALE\",\n" +
                "   \"password\": \"11111111111111111111111111111111111\",\n" +
                "   \"repeatPassword\": \"11111111111111111111111111111111111\"\n" +
                "}";

        this.mockMvc.perform(post("/users/register")
                        .content(requestBody)
                        .contentType("application/json"))
                .andExpect(status().isBadRequest()) // HTTP status == 200
                .andExpect(header().string("Content-Type", "application/json")) // Content-Type = ?
                .andExpect(jsonPath("$.code").value(1004))
                .andExpect(jsonPath("$.message").value("This email is already registered."));

        List<User> users = this.testUserDAO.selectByEmail("fanjianjin@gmail.com");
        assertNotNull(users); // users != null
        assertEquals(1, users.size()); // has previous

        // need to delete this data, since has no validation code
        this.testUserDAO.deleteAll();



    }


    @Test
    public void testActivate_happyCase() throws Exception {
        User user = User.builder()
                .username("Stephen12")
                .nickname("xxxxx")
                .build();
        this.testUserDAO.insert(user);

        UserValidationCode userValidationCode = UserValidationCode.builder()
                .userId(user.getId())
                .validationCode("123456")
                .build();
        this.testUserValidationCodeDAO.insert(userValidationCode);

        var requestBody = "{\n" +
                "    \"username\": \"Stephen12\",\n" +
                "    \"validationCode\": \"123456\"\n" +
                "}";

        this.mockMvc.perform(post("/users/activate")
                        .content(requestBody)
                        .contentType("application/json"))
                .andExpect(status().isOk()) // HTTP status == 200
                .andExpect(header().string("Content-Type", "application/json")) // Content-Type = ?
                .andExpect(jsonPath("$.code").value(1000))
                .andExpect(jsonPath("$.message").value("Successful."));

        var users = this.testUserDAO.selectByUsername("Stephen12");
        assertNotNull(users); // users != null
        assertEquals(1, users.size()); // expected, actual

        user = users.get(0);
        assertTrue(user.getIsValid());

        userValidationCode = this.testUserValidationCodeDAO.selectOneByUserId(user.getId());
        assertNull(userValidationCode);
    }

    @Test
    public void testActivate_userNotExist_returnBadRequest() throws Exception {

        User user = User.builder()
                .username("Stephen12")
                .nickname("xxxxx")
                .build();
        this.testUserDAO.insert(user);

        UserValidationCode userValidationCode = UserValidationCode.builder()
                .userId(user.getId())
                .validationCode("123456")
                .build();
        this.testUserValidationCodeDAO.insert(userValidationCode);
        // delete the user
        this.testUserDAO.deleteByUsername(user.getUsername());

        var requestBody = "{\n" +
                "    \"username\": \"Stephen12\",\n" +
                "    \"validationCode\": \"123456\"\n" +
                "}";

        this.mockMvc.perform(post("/users/activate")
                        .content(requestBody)
                        .contentType("application/json"))
                .andExpect(status().isBadRequest()) // HTTP status == 400
                .andExpect(header().string("Content-Type", "application/json")) // Content-Type = ?
                .andExpect(jsonPath("$.code").value(1005))
                .andExpect(jsonPath("$.message").value("User doesn't exist."));

        var users = this.testUserDAO.selectByUsername("Stephen12");
        assertEquals(0, users.size()); // users != null

    }

    @Test
    public void testActivate_internalServiceError_returnBadRequest() throws Exception {

        User user = User.builder()
                .username("Stephen12")
                .nickname("xxxxx")
                .build();
        this.testUserDAO.insert(user);

        UserValidationCode userValidationCode = UserValidationCode.builder()
                .userId(000) // a random user id
                .validationCode("123456")
                .build();

        this.testUserValidationCodeDAO.insert(userValidationCode);

        var requestBody = "{\n" +
                "    \"username\": \"Stephen12\",\n" +
                "    \"validationCode\": \"123456\"\n" +
                "}";

        this.mockMvc.perform(post("/users/activate")
                        .content(requestBody)
                        .contentType("application/json"))
                .andExpect(status().isBadRequest()) // HTTP status == 400
                .andExpect(header().string("Content-Type", "application/json")) // Content-Type = ?
                .andExpect(jsonPath("$.code").value(1006))
                .andExpect(jsonPath("$.message").value("Internal service error."));


        var invalidCode = this.testUserValidationCodeDAO.selectOneByUserId(user.getId());
        assertNull(invalidCode);

    }


    @Test
    public void testActivate_validationFailed_returnBadRequest() throws Exception {

        User user = User.builder()
                .username("Stephen12")
                .nickname("xxxxx")
                .isValid(false)
                .build();
        this.testUserDAO.insert(user);

        UserValidationCode userValidationCode = UserValidationCode.builder()
                .userId(user.getId()) // a random user id
                .validationCode("123456")
                .build();

        this.testUserValidationCodeDAO.insert(userValidationCode);

        var requestBody = "{\n" +
                "    \"username\": \"Stephen12\",\n" +
                "    \"validationCode\": \"33333\"\n" +
                "}";

        this.mockMvc.perform(post("/users/activate")
                        .content(requestBody)
                        .contentType("application/json"))
                .andExpect(status().isBadRequest()) // HTTP status == 400
                .andExpect(header().string("Content-Type", "application/json")) // Content-Type = ?
                .andExpect(jsonPath("$.code").value(1007))
                .andExpect(jsonPath("$.message").value("Validation code does not match."));

        var users = this.testUserDAO.selectByUsername("Stephen12");
        assertNotNull(users); // users != null
        assertEquals(1, users.size()); // expected, actual

        user = users.get(0);
        assertEquals(false, user.getIsValid());

    }













}

/*
 * unit testing: test of single identity (class or method)
 * Junit: unit testing framework for Java
 *      - first test then coding. useful when some parts of the code are not yet written (when... do)
 *      - increases the productivity and the stability
 *      - 1. known input & expected input     assertEquals("hi", str);
 *      - 2. at least 2 cases for each requirement (one positive and one negative)
 */