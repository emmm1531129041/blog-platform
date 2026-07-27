package org.example.springblogdemo.pojo.response;

import lombok.Data;
import org.example.springblogdemo.common.enums.ResultCodeEnum;

//统一返回格式
@Data
public class Result<T> {
    private int code; //业务码, 不是http状态码
    private String errMsg;
    private T data;

    public static <T> Result<T> ok(T data){
        Result result = new Result();
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setData(data);
        return result;
    }

    public static <T> Result<T> fail(String errMsg){
        Result result = new Result();
        result.setCode(ResultCodeEnum.FAIL.getCode());
        result.setErrMsg(errMsg);
        return result;
    }

    public static <T> Result<T> unlogin(){
        Result result = new Result();
        result.setCode(ResultCodeEnum.UNLOGIN.getCode());
        result.setErrMsg("用户未登录");
        return result;
    }
}

