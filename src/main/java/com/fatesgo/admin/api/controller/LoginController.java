package com.fatesgo.admin.api.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.fatesgo.admin.api.annotation.JwtToken;
import com.fatesgo.admin.api.mapper.UserMapper;
import com.fatesgo.admin.api.pojo.User;
import com.fatesgo.admin.api.util.JwtUtil;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class LoginController {


    @Autowired
    DefaultKaptcha defaultKaptcha;

    @Autowired(required = false)
    private UserMapper userMapper;

    @GetMapping("/defaultKaptcha")
    public void defaultKaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws Exception {
        byte[] captchaChallengeAsJpeg = null;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            // 生产验证码字符串并保存到session中
            String createText = defaultKaptcha.createText();
            httpServletRequest.getSession().setAttribute("verificationCode", createText);
            // 使用生成的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
            BufferedImage challenge = defaultKaptcha.createImage(createText);
            ImageIO.write(challenge, "jpg", jpegOutputStream);
        } catch (IllegalArgumentException e) {
            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = httpServletResponse.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
    }




    /**
     * 登录并获取token
     */
    @PostMapping("/login")
    public Object login(@RequestBody JSONObject userObj,HttpServletRequest httpServletRequest){
        JSONObject jsonObject=new JSONObject();
        String verificationCodeIn = (String) httpServletRequest.getSession().getAttribute("verificationCode");
        httpServletRequest.getSession().removeAttribute("verificationCode");
        if (StringUtils.isEmpty(verificationCodeIn) || !verificationCodeIn.equals(userObj.get("code"))) {
            return "验证码错误，或已失效";
        }
        User user = userMapper.Login(userObj.get("userNmae"),userObj.get("passWord"));
        if(user!=null){
            return "账号或者密码错误！";
        }else{
            // 检验用户存在(这里假设用户存在，制造uuid为用户id)
            String userId = UUID.randomUUID().toString();
            // 生成签名
            String token= JwtUtil.sign(userId);
            jsonObject.put("token", token);
            return jsonObject;
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
