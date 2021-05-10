package com.fatesgo.admin.api.mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;
import java.util.Map;

public interface  RoleMapper {

    @Select("select id,name from sys_role")
    List<Map<String,Object>> getAllRole();
}
