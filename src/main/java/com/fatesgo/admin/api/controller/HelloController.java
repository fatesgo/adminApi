package com.fatesgo.admin.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class HelloController {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @ResponseBody
    @RequestMapping("/map")
    public Map<String, Object> map() {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from sys_user");
        for (Map<String, Object> map : maps
        ) {
            System.out.println(map);
        }
        return maps.get(0);
    }

}
