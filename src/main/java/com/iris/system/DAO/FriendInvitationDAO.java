package com.iris.system.DAO;

import com.iris.system.enums.FriendInvitationStatus;
import com.iris.system.model.FriendInvitation;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FriendInvitationDAO {

    @Select("select * from friend_invitation where receiver_user_id = #{receiverUserId} order by send_time desc limit #{start}, #{limit}")
    List<FriendInvitation> selectByReceiverUserId(@Param("receiverUserId") int receiverUserId,
                                                  @Param("start") int start,
                                                  @Param("limit") int limit);

    @Select("select * from friend_invitation where receiver_user_id = #{receiverUserId} AND sender_user_id = #{senderUserId}")
    FriendInvitation selectOneInvitation(@Param("receiverUserId") int receiverUserId,
                                         @Param("senderUserId") int senderUserId);


    @Insert("INSERT INTO friend_invitation (senderUserId, receiverUserId, sendTime, processTime, status, message) " +
            "VALUES (#{senderUserId}, #{receiverUserId}, #{sendTime}, #{processTime}, #{status}, #{message})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void insert(FriendInvitation friendInvitation);

    // update friendinvitation status by id
    @Update("UPDATE user SET status = #{status} WHERE id=#{id}")
    void updateFriendInvitationStatus(@Param("id") int id, @Param("status") FriendInvitationStatus status);


}