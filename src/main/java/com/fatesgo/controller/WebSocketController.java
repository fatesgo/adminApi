package com.fatesgo.controller;
import com.alibaba.fastjson.JSON;
import com.fatesgo.controller.PushController;
import com.fatesgo.mapper.ChatMapper;
import com.fatesgo.mapper.UserMapper;
import com.fatesgo.pojo.Message;
import com.fatesgo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


@ServerEndpoint("/webSocket/{userId}")
@Component
public class WebSocketController {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static AtomicInteger onlineNum = new AtomicInteger();

    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketServer对象。
    private static ConcurrentHashMap<String, Session> sessionPools = new ConcurrentHashMap<>();

    @Autowired(required = false)
    private ChatMapper chatMapper;

    @Autowired(required = false)
    private UserMapper userMapper;

    public static WebSocketController webSocketController;

    @PostConstruct
    public void init() {
        webSocketController = this;
        webSocketController.chatMapper = this.chatMapper;
        webSocketController.userMapper = this.userMapper;
    }

    //发送消息
    public void sendMessage(Session session, String message) throws IOException {
        if (session != null) {
            synchronized (session) {
                System.out.println("发送数据：" + message);

                session.getBasicRemote().sendText(message);
            }
        }
    }

    //给指定用户发送信息
    public void sendInfo(String userId, String message) {
        Session session = sessionPools.get(userId);
        try {
            sendMessage(session, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 群发消息
    public void broadcast(String message) {
        for (Session session : sessionPools.values()) {
            try {
                sendMessage(session, message);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    //建立连接成功调用
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "userId") String userId) {
        sessionPools.put(userId, session);
        addOnlineCount();
        System.out.println("userId" + "加入webSocket！当前人数为" + onlineNum);
        List<Map<String,Object>> list = webSocketController.chatMapper.getUserChatList(userId);
        Map map= new HashMap<String,Object>();
        map.put("msg",null);
        map.put("userList",list);
        sendInfo(userId, JSON.toJSONString(map, true));
        // 广播上线消息
//        Message msg = new Message();
//        msg.setTime(new Date());
//        msg.setFrom_userId(userId);
//        msg.setContent(userId+"上线了!");
//        broadcast(JSON.toJSONString(msg,true));
    }

    //关闭连接时调用
    @OnClose
    public void onClose(@PathParam(value = "userId") String userId) {
        sessionPools.remove(userId);
        subOnlineCount();
        System.out.println(userId + "断开webSocket连接！当前人数为" + onlineNum);
        // 广播下线消息
//        Message msg = new Message();
//        msg.setTime(new Date());
//        msg.setFrom_userId(userId);
//        msg.setContent(userId+"下线了!");
//        broadcast(JSON.toJSONString(msg,true));
    }

    // to_userId=-1群发消息
    @OnMessage
    public void onMessage(String message) throws IOException {

        System.out.println("server message" + message);
        Message msg = JSON.parseObject(message, Message.class);

        if (msg.getTo_userId().equals("-1")) {
            broadcast(JSON.toJSONString(msg, true));
        } else {
            Session session = sessionPools.get(msg.getTo_userId());
            if(session==null){//对方离线，设置未读
                msg.setState(0);
            }
            List<Map<String,Object>> list =null;
            Map map= new HashMap<String,Object>();
            if(!"reading".equals(msg.getType())){
                Integer id =webSocketController.chatMapper.addChatMessage(msg.getTo_userId(),msg.getFrom_userId(),msg.getType(),msg.getContent().toString(),msg.getState());
                msg.setId(id.toString());
                list = webSocketController.chatMapper.getUserChatList(msg.getFrom_userId());
                map.put("msg",msg);
                map.put("userList",list);
                sendInfo(msg.getFrom_userId(), JSON.toJSONString(map, true));
                if(session!=null){//对方离线，就不发送给对方了
                    sendInfo(msg.getTo_userId(), JSON.toJSONString(map, true));
                }
                com.fatesgo.controller.PushController push = new PushController();
                User user = webSocketController.userMapper.getUserInfoById(msg.getTo_userId());
                String title ="你有一条来着"+user.getNickname()+"消息";
                push.appPush(user.getClientid(),title,title);

            }else{
                list = webSocketController.chatMapper.getUserChatList(msg.getFrom_userId());
                map.put("msg",msg);
                map.put("userList",list);
                sendInfo(msg.getFrom_userId(), JSON.toJSONString(map, true));
            }

        }
    }

    //错误时调用
    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("发生错误");
        throwable.printStackTrace();
    }

    public static void addOnlineCount() {
        onlineNum.incrementAndGet();
    }

    public static void subOnlineCount() {
        onlineNum.decrementAndGet();
    }

    public static AtomicInteger getOnlineNumber() {
        return onlineNum;
    }

    public static ConcurrentHashMap<String, Session> getSessionPools() {
        return sessionPools;
    }
}
