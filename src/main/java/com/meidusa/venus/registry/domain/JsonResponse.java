package com.meidusa.venus.registry.domain;

import com.meidusa.fastjson.JSON;

/**
 * Created by huawei on 12/18/15.
 */
public class JsonResponse<T> implements Jsonable{

    private int code;
    private String message;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toJson(){
        return JSON.toJSONString(this);
    }
}
