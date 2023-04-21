package com.example.pethospital.message;


@SuppressWarnings("unused")
public enum MessageCodeEnum {
    /**
     * 常规错误码
     */
    INVALID_PARAMS(9001, "参数有误或参数为空"),
    NOT_SUPPORT(9002, "请求方式错误"),
    FREQUENTLY_REQUEST(9003, "操作频繁"),
    REQUEST_EXPIRED(9004, "请求过期"),
    PASSWORD_WRONG(9005, "密码错误"),
    HTTP_CONNECTION_OVERTIME(9998, "连接超时"),
    ERROR(9999, "系统异常"),
    SIGN_ERROR(1000, "签名异常"),

    FILE_SIGN_ERROR(1001, "文件签名异常"),
    /**
     * 泛用错误码
     */
    OK(200, "请求通过"),
    NO(201, "请求不通过");



    private final int code;
    private final String message;

    MessageCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
