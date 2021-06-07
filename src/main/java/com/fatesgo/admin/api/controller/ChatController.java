package com.fatesgo.admin.api.controller;
import com.alibaba.fastjson.JSON;
import com.fatesgo.admin.api.mapper.ChatMapper;
import com.fatesgo.admin.api.mapper.MenuMapper;
import com.fatesgo.admin.api.pojo.Message;
import com.fatesgo.admin.api.pojo.ResponseResult;
import com.fatesgo.admin.api.pojo.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "聊天模块")
@RequestMapping("/api")
public class ChatController {

    @Autowired(required = false)
    private ChatMapper chatMapper;
    ResponseResult result = new ResponseResult();

    @ApiOperation(value = "获取我的聊天列表")
    @GetMapping("/getUserChatList/{userId}")
    public ResponseResult getUserChatList(@PathVariable("userId") String userId) {
        List<Map<String,Object>> list = chatMapper.getUserChatList(userId);
        result.success("成功", list);
        return result;
    }

    @ApiOperation(value = "获取和对方的聊天记录")
    @GetMapping("/getMsgList")
    public ResponseResult getMsgList(String userId,String toUserId) {
        List<Map<String,Object>> list = chatMapper.getMsgList(userId,toUserId);
        for (Map<String, Object> objectMap : list) {
            if(!objectMap.get("type").equals("text")){
                objectMap.put("content", JSON.parseObject((String) objectMap.get("content")));

            }
        }
        result.success("成功", list);
        return result;
    }

    @ApiOperation(value = "获取除自己以外的用户列表")
    @GetMapping("/getUserListByUser/{userId}")
    public ResponseResult getUserListByUser(@PathVariable("userId") String userId) {
        List<User> list = chatMapper.getUserListByUser(userId);
        result.success("成功", list);
        return result;
    }

    @ApiOperation(value = "设置对方发送的信息已经阅读")
    @PutMapping("/setHaveRead")
    public ResponseResult setHaveRead(@RequestBody Message message) {
        int row = chatMapper.setHaveRead(message.getTo_userId(),message.getFrom_userId());
        result.success("成功",row);
        return result;
    }
}
