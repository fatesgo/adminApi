package com.fatesgo.mapper;

import com.fatesgo.pojo.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

public interface UserMapper {
    @Select("select * from sys_user where username=#{username} and password=#{password}")
    User Login(Object username, Object password);

    @Select("select * from sys_user where id=#{userId}")
    User getUserInfoById(String userId);

    @Update("UPDATE sys_user SET nickname = #{nickname},avatar=#{avatar},clientid=#{clientid} WHERE id = #{id}")
    int updateUserInfo(User user);

    @Insert("INSERT INTO sys_user (username, password,nickname,avatar,clientid) VALUES (值1, 值2,....)")
    int register(User user);
}
