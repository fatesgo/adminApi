package com.fatesgo.admin.api.mapper;
import com.fatesgo.admin.api.pojo.Message;
import com.fatesgo.admin.api.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;


public interface ChatMapper {

    @Select("SELECT\n" +
            "\ta.* \n" +
            "FROM\n" +
            "\t(\n" +
            "\tSELECT\n" +
            "\t\tu.id,\n" +
            "\t\tu.nickname,\n" +
            "\t\tu.avatar,\n" +
            "\t\tc.content,\n" +
            "\t\tc.time,\n" +
            "\t\tc.type,\n" +
            "\t\t( SELECT IFNULL( SUM( state = 0 ), 0 ) FROM chat  WHERE to_userId = #{userId} AND from_userId = u.id ) AS unreadCount \n" +
            "\tFROM\n" +
            "\t\tchat c\n" +
            "\t\tLEFT JOIN sys_user u ON u.id = from_userId \n" +
            "\t\tOR u.id = c.to_userId \n" +
            "\tWHERE\n" +
            "\t\tu.id != #{userId} \n" +
            "\t\tAND ( c.from_userId = #{userId} OR c.to_userId = #{userId}) \n" +
            "\tORDER BY\n" +
            "\t\tc.time DESC \n" +
            "\t\tLIMIT 999999 \n" +
            "\t) AS a \n" +
            "GROUP BY\n" +
            "\ta.id")
    List<Map<String,Object>> getUserChatList(String userId);

    @Select("SELECT * FROM chat WHERE (to_userId=#{userId} and from_userId=#{toUserId} ) OR (to_userId=#{toUserId} and from_userId=#{userId})")
    List<Map<String,Object>> getMsgList(String userId,String toUserId);

    @Insert("INSERT INTO  chat (to_userId,from_userId,type,content,state) VALUES (#{to_userId},#{from_userId},#{type},#{content},#{state})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    int addChatMessage(String to_userId,String from_userId,String type,String content,int state);

    @Select("SELECT id,nickname,avatar FROM sys_user WHERE id !=#{userId}")
    List<User> getUserListByUser(String userId);

    @Update("UPDATE chat SET state = 1 WHERE to_userId =#{to_userId} and from_userId =#{from_userId}")
    int  setHaveRead(String to_userId,String from_userId);

}
