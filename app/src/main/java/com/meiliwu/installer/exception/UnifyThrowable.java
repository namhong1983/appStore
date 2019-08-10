package com.meiliwu.installer.exception;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/4/5 14:38
 */


public class UnifyThrowable extends Exception {

    private int code;
    private String message;

    public UnifyThrowable(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
