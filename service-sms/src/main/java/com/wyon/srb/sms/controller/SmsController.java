package com.wyon.srb.sms.controller;

import com.wyon.common.exception.Assert;
import com.wyon.common.result.R;
import com.wyon.common.result.ResponseEnum;
import com.wyon.common.util.RandomUtils;
import com.wyon.common.util.RegexValidateUtils;
import com.wyon.srb.sms.service.SmsService;
import com.wyon.srb.sms.util.SmsProperties;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/sms")
public class SmsController {

    @Autowired
    SmsService smsService;

    @ApiOperation("获取验证码")
    @GetMapping("/send/{mobile}")
    public R send(
            @ApiParam(value = "手机号", required = true)
            @PathVariable String mobile){

        Assert.notNull(mobile, ResponseEnum.MOBILE_NULL_ERROR);
        Assert.isTrue(RegexValidateUtils.checkCellphone(mobile),ResponseEnum.MOBILE_ERROR);

        // 短信模板读取
        String templateCode = SmsProperties.TEMPLATE_CODE;

        // 短信参数
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("code", RandomUtils.getSixBitRandom());



        // 调用后端api
        String send = smsService.send(mobile, templateCode, paramMap);

        if(send.equals("OK")){
            return R.ok();
        }else {
            return R.error();
        }

    }
}
