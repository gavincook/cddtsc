package com.tomatorun.sms;

/**
 * 短信服务类
 * Created by Gavin on 2015/2/6.
 */
public interface SMSService {

    /**
     * 发送短信
     * @param phoneNumber
     * @param content
     */
    public void sendSms(String phoneNumber, String content);


}
