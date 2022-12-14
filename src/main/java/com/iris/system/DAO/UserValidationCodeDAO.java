package com.iris.system.DAO;

import com.iris.system.model.UserValidationCode;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserValidationCodeDAO {

    @Insert("INSERT INTO user_validation_code (user_id, validation_code) VALUES (#{userId}, #{validationCode})")
    void insert(UserValidationCode userValidationCode);

    @Select("SELECT * FROM user_validation_code WHERE user_id = #{userId} ORDER BY id DESC LIMIT 1")
    UserValidationCode selectOneByUserId(@Param("userId") int userId);

    @Delete("DELETE FROM user_validation_code WHERE id = #{id}")
    void delete(@Param("id") int id);
}
