package com.fatesgo.admin.api.mapper;

import org.apache.ibatis.annotations.Select;
import java.util.List;
import java.util.Map;

public interface MenuMapper {

    @Select("select * from sys_menu")
    List<Map<String,Object>> getAllMenu();

    @Select("SELECT m.* FROM sys_user AS u LEFT JOIN sys_role_menu rm ON u.role_id = rm.role_id LEFT JOIN sys_menu m ON m.id = rm.menu_id WHERE u.id =#{userId}")
    List<Map<String,Object>> getAllMenuByUserId(String userId);
}
