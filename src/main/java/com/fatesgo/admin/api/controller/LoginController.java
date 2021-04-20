package com.fatesgo.admin.api.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.fatesgo.admin.api.annotation.JwtToken;
import com.fatesgo.admin.api.mapper.UserMapper;
import com.fatesgo.admin.api.pojo.ResponseResult;
import com.fatesgo.admin.api.pojo.User;
import com.fatesgo.admin.api.util.JwtUtil;
import com.fatesgo.admin.api.util.RedisUtil;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class LoginController {


    @Autowired
    DefaultKaptcha defaultKaptcha;
    @Autowired
    RedisUtil redisUtil;
    @Autowired(required = false)
    private UserMapper userMapper;
    ResponseResult result = new ResponseResult();


    @GetMapping("/defaultKaptcha")
    public ResponseResult defaultKaptcha(HttpServletResponse httpServletResponse)
            throws Exception {
        byte[] captchaChallengeAsJpeg = null;
        String uuid = UUID.randomUUID().toString();
        String verifyCodeKey = "code"+uuid;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            // 生产验证码字符串并保存到session中
            String createText = defaultKaptcha.createText();
            redisUtil.set(verifyCodeKey,createText,60);//验证码为60s
            // 使用生成的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
            BufferedImage challenge = defaultKaptcha.createImage(createText);
            ImageIO.write(challenge, "jpg", jpegOutputStream);
        } catch (IllegalArgumentException e) {
            result.success(e.getMessage(),null);
            return result;
        }
        // 定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/jpeg");
        Map<String, Object> map = new HashMap<>();
        map.put("uuid",uuid);
        map.put("img",new BASE64Encoder().encode(captchaChallengeAsJpeg));
        result.success("成功",map);
        return result;
    }




    /**
     * 登录并获取token
     */
    @PostMapping("/login")
    public ResponseResult login(@RequestBody JSONObject userObj){
        String verificationCodeIn  = (String) redisUtil.get("code"+userObj.get("uuid"));
        redisUtil.del(verificationCodeIn);
        if (StringUtils.isEmpty(verificationCodeIn) || !verificationCodeIn.equals(userObj.get("code"))) {
             result.error("验证码错误，或已失效",null);
            return result;
        }
        User user = userMapper.Login(userObj.get("username"),userObj.get("password"));
        if(user==null){
            result.error("账号或者密码错误！",null);
            return result;
        }else{

            // 生成签名
            String token= JwtUtil.sign(user.getId());
            Map<String, Object> map = new HashMap<>();
            map.put("token",token);
            map.put("user",user);
            result.success("成功",map);
            redisUtil.set("user_info"+user.getId().toString(),user,1800);
            redisUtil.set("user_token"+user.getId().toString(),token,1800);
            return result;
        }
    }



    /**
     * 该接口需要带签名才能访问
     */
    @JwtToken
    @GetMapping("/getMessage")
    public String getMessage(){
        return "你已通过验证";
    }
}
