package com.iris.system.dao;


import com.iris.system.model.UserValidationCode;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TestUserValidationCodeDAO {

    @Insert("INSERT INTO user_validation_code (user_id, validation_code) VALUES (#{userId}, #{validationCode})")
    void insert(UserValidationCode userValidationCode);


    @Select("SELECT * FROM user_validation_code WHERE user_id = #{userId} ORDER BY id DESC LIMIT 1")
    UserValidationCode selectOneByUserId(@Param("userId") int userId);


    @Delete("DELETE FROM user_validation_code")
    void deleteAll();
}
