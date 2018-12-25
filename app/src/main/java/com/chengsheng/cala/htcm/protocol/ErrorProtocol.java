package com.chengsheng.cala.htcm.protocol;

/**
 * Author: 任和
 * CreateDate: 2018/12/25 4:33 PM
 * Description: 接口请求异常类
 */
public class ErrorProtocol {

    /**
     * message : 密码验证不通过
     */

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
