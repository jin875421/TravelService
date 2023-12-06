package com.example.androdid.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;

public class SmsUtil {

    private static final String accessKeyId = "LTAI5tJfqKbppLkuuUJ8PGmC";
    private static final String accessKeySecret = "SXi0TgncidxF1pcLewthAMBPYHK4g5";
    private static final String doMain = "dysmsapi.aliyuncs.com";
    private static final String regionId = "cn-hangzhou";
    private static final String signName = "田子腾的项目";
    private static final String templateCode = "SMS_464085866";

    public static String sendSms(String phoneNumber) {

        // 生成随机验证码
        String code = generateRandomCode();
        System.out.println(phoneNumber+code);
        // 设置短信模板中的参数
        String templateParam = "{\"code\":\"" + code + "\"}";

        try {
            // 初始化acsClient,暂不支持region化
            DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
            IAcsClient acsClient = new DefaultAcsClient(profile);

            // 组装请求对象
            SendSmsRequest request = new SendSmsRequest();
            request.setPhoneNumbers(phoneNumber);
            request.setSignName(signName);
            request.setTemplateCode(templateCode);
            request.setTemplateParam(templateParam);

            // 发送请求并解析响应
            SendSmsResponse response = acsClient.getAcsResponse(request);
            System.out.println(response.getMessage()+response.getCode());
            if ("OK".equals(response.getCode())) {
                // 短信发送成功
                return code;
            } else {
                // 短信发送失败
                return "";
            }
        } catch (ServerException e) {
            e.printStackTrace();
            return "";
        } catch (ClientException e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String generateRandomCode() {
        // 生成随机6位数字验证码，实际业务中可以根据需求调整验证码长度和内容
        return String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
    }
}