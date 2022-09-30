package com.iris.system.DAO;

import com.iris.system.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;


/* Design pattern: DAO (Data Access object)
    Separate low-level data accessing API or operations from services

    Manage standard operations on a data model
    -> store the query that manipulates the data model User

    CRUD: Create, Read, Update, Delete
 */

/*
    BoilerPlate: 样板，引用 a section of code that have to be included in many places with little or no alteration
    ex. <!DOCTYPE html>
 */

/*
    POJO: Pain Old Java Object, not bound by any restrictions (no implement or extend,
            no annotation, no restrictions on access modifiers)
    JavaBean: A standardized class that has restrictions (1. all properties are private with getter and setter)
                2. default public no-argument constructor, 3. implements serializable

 */

/* @Mapper
    A JavaBean mapper, that automatically map between two Java Beans?

    @Repository (close to DAO pattern)
    encapsulate storage, retrieval, update and delete behaviours that emulates a collection of objects
 */

@Mapper
@Repository
public interface UserDAO {

    //user.getUsername()
    @Insert("INSERT INTO `user` (username, nickname, password, register_time, gender, email, address, is_valid) " +
            "VALUES (#{username}, #{nickname}, #{password}, #{registerTime}, #{gender}, #{email}, #{address}, " +
            "#{isValid})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void insert(User user);

    @Select("select * from user where username=#{username}")
    List<User> selectByUsername(@Param("username") String username);

    @Select("select * from user where email=#{email}")
    List<User> selectByEmail(@Param("email") String email);

    @Update("UPDATE user SET is_valid = 1 WHERE id = #{userId}")
    void updateToValid(@Param("userId") int userId);
}
