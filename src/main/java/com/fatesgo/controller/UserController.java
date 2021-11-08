package com.fatesgo.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.fatesgo.mapper.MenuMapper;
import com.fatesgo.mapper.UserMapper;
import com.fatesgo.pojo.ResponseResult;
import com.fatesgo.pojo.User;
import com.fatesgo.util.JwtUtil;
import com.fatesgo.util.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Api(tags = "用户模块")
public class UserController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    RedisUtil redisUtil;
    @Autowired(required = false)
    private MenuMapper menuMapper;
    @Autowired(required = false)
    private UserMapper userMapper;
    ResponseResult result = new ResponseResult();

    /**
     * 根据token获取用户信息
     */
    @ApiOperation(value = "根据token获取用户信息")
    @GetMapping("/getUserInfoByToken")
    public ResponseResult getUserInfoByToken() {
        String token = request.getHeader("token");
        Map<String, Object> map = new HashMap<>();
        Map user = (Map) redisUtil.get("user_info" + token);
        JSONObject userInfo = new JSONObject(user);
        map.put("user", userInfo);
        List<Map<String, Object>> menuList = menuMapper.getAllMenuByUserId((String) userInfo.get("id"));
        for (Map<String, Object> m : menuList) {
            String meta = (String) m.get("meta");
            JSONObject jsonObject = JSONObject.parseObject(meta);
            m.remove("meta");
            m.put("meta", jsonObject);
            List<Map<String, Object>> subMenuList = menuMapper.getAllMenuByPid((Integer) m.get("id"));
            for (Map<String, Object> subMap : subMenuList) {
                String subMetaStr = (String) subMap.get("meta");
                JSONObject jsonSubMeta = JSONObject.parseObject(subMetaStr);
                subMap.remove("meta");
                subMap.put("meta", jsonSubMeta);
                List<Map<String, Object>> btnList = menuMapper.getAllMenuBtnByMenuId((Integer) m.get("id"));
                subMap.put("btnList", btnList);
            }
            m.put("children", subMenuList);
        }
        map.put("menuList", menuList);
        result.success("成功", map);
        return result;
    }

    @ApiOperation(value = "修改用户信息")
    @PutMapping("/updateUserInfo")
    public ResponseResult updateUserInfo(@RequestBody User user) {
        int row = userMapper.updateUserInfo(user);
        result.success("成功", row);
        return result;
    }

    @ApiOperation(value = "注册新用户信息")
    @PostMapping("/register")
    public ResponseResult register(@RequestBody User user) {
        int row = userMapper.register(user);
        result.success("成功", row);
        return result;
    }


}


