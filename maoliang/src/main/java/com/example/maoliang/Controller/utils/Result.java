package com.example.maoliang.Controller.utils;

public class Result {
    //用于封装控制层（Controller）返回给前端的结果
    private String page;
    private String msg;
    private Object data;//存放任何需要临时传到下一界面的数据
    //如果是长期存储，如admin数据，请放置session并编写读取session的函数以便前端读取
    //如果传输类型多请在dto界面增加xxxdata

    public Result(String page, String msg, Object data) {
        this.page = page;
        this.msg = msg;
        this.data = data;
    }
    public Result(String page, String msg) {
        this.page = page;
        this.msg = msg;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
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
