package com.fatesgo.admin.api.pojo;

import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import springfox.documentation.spring.web.json.Json;

public class Message {
    // 发送id
    public String id;
    // 发送者id
    public String from_userId;
    // 接收者id
    public String to_userId;
    // 发送的内容
    public Object content;
    // 发送的类型 系统消息 ，用户消息
    public String type;
    // 在线状态
    public String state;
    // 发送时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrom_userId() {
        return from_userId;
    }

    public void setFrom_userId(String from_userId) {
        this.from_userId = from_userId;
    }

    public String getTo_userId() {
        return to_userId;
    }

    public void setTo_userId(String to_userId) {
        this.to_userId = to_userId;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }




    @Override
    public String toString() {
        return "Message [from_userId=" + from_userId + ", to_userId=" + to_userId + ", content=" + content + ", type=" + type
                + ", time=" + time + ", state=" + state +"]";
    }

}