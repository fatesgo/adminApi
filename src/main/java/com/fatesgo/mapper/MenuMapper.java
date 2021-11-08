package com.fatesgo.mapper;

import org.apache.ibatis.annotations.Select;
import java.util.List;
import java.util.Map;

public interface MenuMapper {

    @Select("select * from sys_menu")
    List<Map<String,Object>> getAllMenu();

    @Select("SELECT m.* FROM sys_user AS u LEFT JOIN sys_role_menu rm ON u.role_id = rm.role_id LEFT JOIN sys_menu m ON m.id = rm.menu_id WHERE  m.`level` =0 and u.id =#{userId}")
    List<Map<String,Object>> getAllMenuByUserId(String userId);

    @Select("SELECT\n" +
            "\t* \n" +
            "FROM\n" +
            "\tsys_menu \n" +
            "WHERE\n" +
            "\t`pid` = #{pId}")
    List<Map<String,Object>> getAllMenuByPid(int pId);

    @Select("SELECT\n" +
            "\tbtn_name,\n" +
            "\tbtn_key \n" +
            "FROM\n" +
            "\tsys_menu_btn \n" +
            "WHERE\n" +
            "\tmenu_id =#{menuId} ")
    List<Map<String,Object>> getAllMenuBtnByMenuId(Integer menuId);
}
