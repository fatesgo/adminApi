package com.fatesgo.admin.api.mapper;

import com.fatesgo.admin.api.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

public interface UserMapper {
    @Select("select * from sys_user where user_name=#{userName} and password=#{passWord}")
    User Login(Object userName, Object passWord);


}
