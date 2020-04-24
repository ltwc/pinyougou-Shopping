package com.lt.pojo;

import java.io.Serializable;

public class RespBean implements Serializable {
    private Integer code;
    private String message;
    private Object data;

    public RespBean(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public static RespBean ok(String message){
        return new RespBean(200,message);
    }
    public static RespBean ok(String message, Object data){
        return new RespBean(200,message,data);
    }
    public static RespBean error(String message, Object data){
        return new RespBean(500,message,data);
    }
    public static RespBean error(String message){
        return new RespBean(500,message);
    }


    public RespBean(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public RespBean(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getmessage() {
        return message;
    }

    public void setmessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
