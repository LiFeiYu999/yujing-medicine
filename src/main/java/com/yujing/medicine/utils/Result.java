package com.yujing.medicine.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Result<T> implements Serializable {
    private Integer code;
    private String msg;
    private T data;

    private Result(){}
    // 当接受到错误时进行error
    public static Result error(String msg){
        Result r=new Result();
        r.setCode(201);
        r.setMsg(msg);
        return r;
    }

    //当接受到正确是进行success
    public static Result success(Object data) {
        Result r = new Result();
        r.setCode(200);
        r.setMsg("操作成功");
        r.setData(data);
        return r;
    }
    public static Result success(String msg,String data) {
        Result r = new Result();
        r.setCode(200);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

}
