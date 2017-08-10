package org.sonic.tcp.rpc.core.exception;

public enum RpcExceptionCodeEnum
{
    DATA_PARSER_ERROR("DATA_PARSER_ERROR","数据转换异常"),
    NO_BEAN_FOUND("NO_BEAN_FOUND","没有找到bean对象"),
    INVOKE_REQUEST_ERROR("INVOKE_REQUEST_ERROR","RPC请求异常"),
    NO_PROVIDERS("NO_PROVIDERS","没有服务提供")
    ;

    private String code;
    private String msg;

    RpcExceptionCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
