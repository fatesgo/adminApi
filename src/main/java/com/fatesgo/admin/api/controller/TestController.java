package com.fatesgo.admin.api.controller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping ( "/api" )
@RestController
public class TestController {


    @PostMapping("/operatorInformationManagement")
    public Map<String,Object> operatorInformationManagement(){

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String,Object> map = new HashMap<>();
        map.put("city", "南宁市");
        map.put("county", "西乡塘区");
        map.put("operator", "移动");
        map.put("principal", 0);
        list.add(map);

        Map<String,Object> result = new HashMap<>();
        result.put("items",list);
        result.put("totalCount",1);

        Map<String,Object> successMap = new HashMap<>();
        successMap.put("success", true);
        successMap.put("result", result);
        return successMap;
    }

    @PostMapping("/problemTypeManagement")
    public Map<String,Object> problemTypeManagement(){

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String,Object> map = new HashMap<>();
        map.put("Id", 1);
        map.put("issueKind", "维护类");
        map.put("bigClass", "市电故障");
        map.put("subClass", "市电杆路");
        map.put("issueCategory", "市电杆路");
        map.put("earlyWarning", "15个自然日");
        map.put("timeOut", "30个自然日");
        list.add(map);

        Map<String,Object> result = new HashMap<>();
        result.put("items",list);
        result.put("totalCount",1);

        Map<String,Object> successMap = new HashMap<>();
        successMap.put("success", true);
        successMap.put("result", result);
        return successMap;
    }

    @PostMapping("/siteInformationMaintenance")
    public Map<String,Object> siteInformationMaintenance(){

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String,Object> map = new HashMap<>();
        map.put("Id", 1);
        map.put("StationName", "南宁");
        map.put("StationCode", "NN-YD-201807-14682");
        map.put("city", "南宁市");
        map.put("county", "西乡塘区");
        map.put("Longitude", "102.01");
        map.put("Latitude", "103.02");
        map.put("UserName", "黄站长");
        list.add(map);

        Map<String,Object> result = new HashMap<>();
        result.put("items",list);
        result.put("totalCount",1);

        Map<String,Object> successMap = new HashMap<>();
        successMap.put("success", true);
        successMap.put("result", result);
        return successMap;
    }


    @PostMapping("/del")
    public Map<String,Object> del(){
        Map<String,Object> successMap = new HashMap<>();
        successMap.put("success", true);
        return successMap;
    }
}
