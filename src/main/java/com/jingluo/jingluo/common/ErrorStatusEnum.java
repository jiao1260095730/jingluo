package com.jingluo.jingluo.common;

/**
 * @Description TODO
 * @Author 焦斌
 * @Date 2020/4/16 22:00
 */
public enum  ErrorStatusEnum {
    /*
    * 错误码整理：
    *   444: 未知异常
    *   10XX：连接工具：阿里云
    *   20XX：发送短信
    */

    unKnownErro("未知异常", 444),
    smsSendErro("发送短信失败", 2004),
    smsMinute("当前一分钟内已经发送过消息", 2003),
    smsHoursErro("当前一小时内发送消息次数已达5次", 2002),
    smsDaysErro("当日发送消息次数已达10次", 2001),
    fileUploadErro("使用阿里云工具上传图片异常", 1001);

    private String description;
    private int code;

    private ErrorStatusEnum (String description, int code) {
        this.description = description;
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
