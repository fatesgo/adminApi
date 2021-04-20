package com.fatesgo.admin.api.pojo;

public class ResponseResult<T> {
    private int code;           //请求状态码
    private  String msg;         //返回内容
    private Object data;          //结果数据


    public void success(String msg, Object data)
    {
        this.setCode(1);
        this.setMsg(msg);
        this.setData(data);
    }
    public void error( String msg, Object data)
    {
        this.setCode(0);
        this.setMsg(msg);
        this.setData(data);
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }



}