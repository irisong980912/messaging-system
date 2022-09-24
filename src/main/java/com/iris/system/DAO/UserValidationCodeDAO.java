package com.iris.system.DAO;

import com.iris.system.model.User;
import com.iris.system.model.UserValidationCode;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserValidationCodeDAO {

    @Insert("INSERT INTO `user_validation_code` (validation_code, user_id) " +
            "VALUES (#{validationCode}, #{userID}, #{password}, #{registerTime}, #{gender}, #{email}, #{address}, " +
            "#{isValid})")
    void insert(UserValidationCode userValidationCode);

    // SELECT statement return data in a table (a list)
    // TODO: select the most recently sent validation code object? why desc?
    @Select("select * from user_validation_code where user_id=#{userID} ORDER BY id DESC LIMIT 1")
    UserValidationCode selectRecentValidationCodeByUserID(@Param("user_id") int userID);

    @Delete("DELETE FROM user_validation_code WHERE user_id = #{userID}")
    void delete(@Param("user_id") int userID);
}
