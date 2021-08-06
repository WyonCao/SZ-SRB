package com.wyon.srb.sms.service.impl;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.wyon.srb.sms.service.SmsService;
import com.wyon.srb.sms.util.SmsProperties;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public String send(String mobile, String templateCode, Map<String, Object> paramMap) {


        // 公共参数
        DefaultProfile profile = DefaultProfile.getProfile(
                SmsProperties.REGION_Id,
                SmsProperties.KEY_ID,
                SmsProperties.KEY_SECRET);

        // 创建阿里云api对象
        IAcsClient iAcsClient = new DefaultAcsClient(profile);

        // 请求参数
        CommonRequest commonRequest = new CommonRequest();
        commonRequest.setSysMethod(MethodType.POST);
        commonRequest.setSysDomain("dysmsapi.aliyuncs.com");
        commonRequest.setSysVersion("2017-05-25");
        commonRequest.setSysAction("SendSms");
        commonRequest.putQueryParameter("RegionId", SmsProperties.REGION_Id);
        commonRequest.putQueryParameter("PhoneNumbers", mobile);
        String decode = null;
        try {
            decode = URLDecoder.decode(SmsProperties.SIGN_NAME, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        commonRequest.putQueryParameter("SignName", decode);
        commonRequest.putQueryParameter("TemplateCode", templateCode);

        // 参数,传TemplateParam,这里面要穿json格式数据，而且也可以传一整个map
        Gson gson = new Gson();
        String codeParamJson = gson.toJson(paramMap);
        commonRequest.putQueryParameter("TemplateParam", codeParamJson);

        // 调用阿里云短信服务api,发送短信
        CommonResponse commonResponse = null;
        try {
            commonResponse = iAcsClient.getCommonResponse(commonRequest);
        } catch (ClientException e) {
            e.printStackTrace();
        }

        // 获得http请求状态
        int httpStatus = commonResponse.getHttpStatus();

        // 获得阿里云短信返回状态
        int status = commonResponse.getHttpResponse().getStatus();

        // 阿里返回结果的参数
        String data = commonResponse.getData();
        System.out.println(data);

        // 将返回结果转化成map
        Map<String,Object> responseMap = new HashMap<>();
        responseMap = gson.fromJson(data, responseMap.getClass());

        String code = (String)responseMap.get("Code");

        if(!StringUtils.isEmpty(code)&&"OK".equals(code)){
            System.out.println(code);
            // 发送成功，将短信存入redis

            redisTemplate.opsForValue().set("srb:sms:code:"+mobile,(String)paramMap.get("code"));
            return "OK";
        }else{
            return "error";
        }


    }
}
