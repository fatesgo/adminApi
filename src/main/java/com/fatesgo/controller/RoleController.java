package com.fatesgo.controller;
import com.fatesgo.mapper.RoleMapper;
import com.fatesgo.pojo.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

@RestController
@Api( tags = "系统模块")
@RequestMapping("/sys")
public class RoleController {
    @Autowired(required = false)
    private RoleMapper roleMapper;
    ResponseResult result = new ResponseResult();

    @ApiOperation(value = "获取全部角色")
    @GetMapping("/getAllRole")
    public ResponseResult getAllRole(){
        List<Map<String,Object>> list =roleMapper.getAllRole();
        result.success("成功",list);
        return result;
}
}
