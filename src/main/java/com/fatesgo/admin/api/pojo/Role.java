package com.fatesgo.admin.api.pojo;

import java.util.ArrayList;
import java.util.stream.Stream;

public class Role extends ArrayList {

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private  String name;


}
