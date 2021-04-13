package com.fatesgo.admin.api.mapper;

import com.fatesgo.admin.api.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

public interface UserMapper {
    /**
     *  通过id查询user对象
     * @param id 主键
     * @return user对象
     */
    @Select("select id,user_name from sys_user where id=#{id}")
    User queryUserById(int id);


    @Options(useGeneratedKeys = true, keyProperty = "id")
    //options用来定义主键返回,keyProperty指定主键对应的属性
    @Insert("insert into sys_user (userName) values (#{userName})")
    int insert(User user);

}
