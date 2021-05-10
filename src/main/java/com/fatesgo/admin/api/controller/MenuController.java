package com.fatesgo.admin.api.controller;

import com.fatesgo.admin.api.mapper.MenuMapper;
import com.fatesgo.admin.api.pojo.ResponseResult;
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
public class MenuController {

    @Autowired(required = false)
    private MenuMapper menuMapper;
    ResponseResult result = new ResponseResult();

    @ApiOperation(value = "获取全部菜单")
    @GetMapping("/getAllMenu")
    public ResponseResult getAllRole(){
        List<Map<String,Object>> list =menuMapper.getAllMenu();
        result.success("成功",list);
        return result;
    }
}
