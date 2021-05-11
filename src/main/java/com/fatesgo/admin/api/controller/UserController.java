package com.fatesgo.admin.api.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.fatesgo.admin.api.mapper.MenuMapper;
import com.fatesgo.admin.api.mapper.UserMapper;
import com.fatesgo.admin.api.pojo.ResponseResult;
import com.fatesgo.admin.api.pojo.User;
import com.fatesgo.admin.api.util.JwtUtil;
import com.fatesgo.admin.api.util.RedisUtil;
import io.swagger.annotations.Api;
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
@Api( tags = "用户模块")
public class UserController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    RedisUtil redisUtil;
    @Autowired(required = false)
    private MenuMapper menuMapper;
    ResponseResult result = new ResponseResult();

    /**
     * 根据token获取用户信息
     */
    @GetMapping("/getUserInfoByToken")
    public ResponseResult getUserInfoByToken(){
        String token = request.getHeader("token");
        Map<String, Object> map = new HashMap<>();
        Map user = (Map) redisUtil.get("user_info"+token);
        JSONObject  userInfo =  new JSONObject(user);
        map.put("user",userInfo);
        List<Map<String,Object>> menuList =menuMapper.getAllMenuByUserId((String) userInfo.get("id"));
        for (Map<String, Object> m : menuList)
        {
            String meta= (String) m.get("meta");
            JSONObject jsonObject=JSONObject.parseObject(meta);
            m.remove("meta");
            m.put("meta",jsonObject);
            List<Map<String,Object>> subMenuList =menuMapper.getAllMenuByPid((Integer) m.get("id"));
            for (Map<String, Object> subMap : subMenuList) {
                String subMetaStr= (String) subMap.get("meta");
                JSONObject jsonSubMeta=JSONObject.parseObject(subMetaStr);
                subMap.remove("meta");
                subMap.put("meta",jsonSubMeta);
                List<Map<String,Object>> btnList =menuMapper.getAllMenuBtnByMenuId((Integer) m.get("id"));
                subMap.put("btnList",btnList);
            }
            m.put("children",subMenuList);
        }
        map.put("menuList",menuList);
        result.success("成功",map);
        return result;
        }
    }


