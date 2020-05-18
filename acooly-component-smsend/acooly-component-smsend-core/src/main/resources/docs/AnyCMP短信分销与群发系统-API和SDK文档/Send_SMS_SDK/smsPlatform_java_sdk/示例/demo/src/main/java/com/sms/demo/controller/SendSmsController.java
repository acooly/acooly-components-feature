package com.sms.demo.controller;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.sms.demo.controller.input.BatchSendSmsInput;
import com.sms.demo.controller.input.SendSmsInput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 说明:
 * <br>@author zhangbo
 * <br>@date 2019/5/23 17:33
 *
 * <br>UpdateNote:
 * <br>UpdateTime:
 * <br>UpdateUser:
 */
@RestController
@Api("发送短信控制层")
public class SendSmsController {

    @ApiOperation("单发短信接口")
    @PostMapping("/sms/send")
    public String smsSend(@RequestBody SendSmsInput input) throws Exception{
        //创建DefaultProfile  regionId默认为ydy。将、accessKey，SerectKey分别替换为客户在短信平台的accessKey，SerectKey
        DefaultProfile profile = DefaultProfile.getProfile(input.getRegionId(), input.getAccessKeyId(), input.getSecretKeyId());
        IAcsClient client = new DefaultAcsClient(profile);
        //创建发送请求实体
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        //短信平台提供get请求类型接口
        //request.setMethod(MethodType.POST);
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        //短信平台单发接口地址
        request.setDomain("sms.anycmp.com:8080/sms/send");
        //发送参数
        request.putQueryParameter("PhoneNumbers", input.getPhoneNumbers());
        request.putQueryParameter("SignName", input.getSignName());
        request.putQueryParameter("TemplateCode", input.getTemplateCode());
        request.putQueryParameter("TemplateParam", input.getTemplateParam());

        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return response.getData();
        } catch (ServerException e) {
            return e.getErrCode();
        } catch (ClientException e) {
            return e.getErrCode();
        }
    }

    @ApiOperation("批发短信接口")
    @PostMapping("/sms/batch/send")
    public String smsBatchSend(@RequestBody BatchSendSmsInput input) throws Exception{
        //创建DefaultProfile  regionId默认为ydy。将、accessKey，SerectKey分别替换为客户在短信平台的accessKey，SerectKey
        DefaultProfile profile = DefaultProfile.getProfile(input.getRegionId(), input.getAccessKeyId(), input.getSecretKeyId());
        IAcsClient client = new DefaultAcsClient(profile);
        //创建发送请求实体
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        //短信平台提供get请求类型接口
        //request.setMethod(MethodType.POST);
        request.setVersion("2017-05-25");
        request.setAction("SendBatchSms");

        //短信平台单发接口地址
        request.setDomain("sms.anycmp.com:8080/sms/batch/send");
        //发送参数
        request.putQueryParameter("PhoneNumberJson", input.getPhoneNumberJson());
        request.putQueryParameter("SignNameJson", input.getSignNameJson());
        request.putQueryParameter("TemplateParamJson", input.getTemplateParamJson());
        request.putQueryParameter("TemplateCode", input.getTemplateCode());
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return response.getData();
        } catch (ServerException e) {
            return e.getErrCode();
        } catch (ClientException e) {
            return e.getErrCode();
        }
    }

}
