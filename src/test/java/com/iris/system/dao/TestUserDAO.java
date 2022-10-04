package com.iris.system.dao;

import com.iris.system.enums.Gender;
import com.iris.system.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

// Data access object
// CRUD: Create, Read, Update, Delete
@Mapper
@Repository // manage CURD operations
public interface TestUserDAO {

    // SQL field naming convention: lower_case
    // Java field naming convention: camelCase
    @Insert("INSERT INTO `user` (username, nickname, password, register_time, last_login_time, gender, email, address, is_valid) " +
            "VALUES (#{username}, #{nickname}, #{password}, #{registerTime}, #{lastLoginTime}, #{gender}, #{email}, #{address}, " +
            "#{isValid})")
    @Options(useGeneratedKeys = true, keyColumn= "id", keyProperty = "id")
    void insert(User user);

    @Select("SELECT * FROM `user` WHERE username=#{username}")
    List<User> selectByUsername(@Param("username") String username);

    @Select("select * from user where email=#{email}")
    List<User> selectByEmail(@Param("email") String email);

    @Select("DELETE FROM `user` WHERE username=#{username}")
    List<User> deleteByUsername(@Param("username") String username);

    @Delete("DELETE FROM user")
    void deleteAll();
}


/* Value Expression ${}: only get and %{}: get and set
 * ${user.name}
 * use in any standard or custom tag attribute that can accept an expression
 */