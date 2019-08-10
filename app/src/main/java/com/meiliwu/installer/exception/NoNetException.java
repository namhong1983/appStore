package com.meiliwu.installer.exception;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/4/6 14:10
 */


public class NoNetException extends Exception {

    public int code = -100;
    public String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NoNetException() {

    }
}
