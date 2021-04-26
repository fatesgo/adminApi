package com.fatesgo.admin.api.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api( tags = "报表模块")
public class reportController {
    List<Map<String,Object>> list=null;
    @ApiOperation(value = "报表汇总-按分类1")
    @GetMapping("/getCategory1")
    public JSONObject getCategory() {
        list = new ArrayList<Map<String, Object>>();
        JSONObject json = new JSONObject();
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("city","地市合计");
        map.put("citySum",54117);
        map.put("a",1);
        map.put("otherClass",10086);
        return getJsonObject(json, map);
    }

    private JSONObject getJsonObject(JSONObject json, Map<String, Object> map) {
        list.add(map);
        json.put("success",true);
        Map<String,Object> map1 = new HashMap<>();
        map1.put("items",list);
        json.put("result",map1);
        return json;
    }

    @ApiOperation(value = "综合查询")
    @GetMapping("/getIntegratedQuery")
    public JSONObject getIntegratedQuery() {
       list = new ArrayList<Map<String, Object>>();
        JSONObject json = new JSONObject();
        Map<String,Object> map = new HashMap<>();
        map.put("questionCode","NN-YD-201705-00010");
        map.put("city","南宁市");
        map.put("CustomerName","移动");
        map.put("CaseTypeName","维护类");
        map.put("BigTypeName","动力配套故障修理");
        map.put("SubTypeName","温控维修");
        map.put("StationName","南宁上林县塘红乡石门村基站无线机房");
        map.put("QuestionDetail","空调不制冷");
        map.put("HappenTime","2017/4/25 0:00:00");
        map.put("SubmitTime","2017/5/12 0:00:00");
        map.put("Remark",null);
        map.put("SolutionDetail","整机更换空调");
        map.put("PlanCompleteTime","2017/5/12 0:00:00");
        map.put("RepairTypeName",null);
        map.put("CostTypeName","项目类");
        map.put("ArrangeTime","2017/7/31 0:00:00");
        map.put("ApprovalTime","2017/7/31 0:00:00");
        map.put("ArrivalTime","2017/7/31 0:00:00");
        map.put("ConstructCompleteTime","2017/7/31 0:00:00");
        map.put("AcceptTime","2017/7/31 0:00:00");
        map.put("ActualCompleteTime","2017/7/31 0:00:00");
        map.put("Status",1);
        return getJsonObject(json, map);
    }
}
