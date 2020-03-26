package com.acooly.module.face.web;

import com.acooly.core.common.web.support.JsonEntityResult;
import com.acooly.core.utils.mapper.BeanCopier;
import com.acooly.module.face.dto.VideoSessionDto;
import com.acooly.module.face.dto.VideoVerifyDto;
import com.acooly.module.face.order.VideoSessionCodeOrder;
import com.acooly.module.face.order.VideoVerifyOrder;
import com.acooly.module.face.result.VideoSessionResult;
import com.acooly.module.face.result.VideoVerifyResult;
import com.acooly.module.face.service.BaiduFaceService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author liangsong
 * @date 2020-03-20 14:30
 */
@CrossOrigin
@Controller
@RequestMapping("/baidu")
public class BaiduFaceApiController {

    @Autowired
    private BaiduFaceService baiduFaceService;

    /**
     * 获取视频语音验证码
     */
    @ResponseBody
    @GetMapping(value = {"/videoSessionCode"})
    @ApiOperation("获取视频语音验证码")
    @ApiImplicitParams({@ApiImplicitParam(name = "bizOrderNo", value = "业务订单号", required = true, paramType = "query"),
            @ApiImplicitParam(name = "minCodeLength", value = "生成的验证码最小长度 最大6 最小3 默认3", required = false, paramType = "query"),
            @ApiImplicitParam(name = "maxCodeLength", value = "生成的验证码最大长度 最大6 最小3 默认6", required = false, paramType = "query")})
    public JsonEntityResult<VideoSessionDto> videoSessionCode(String bizOrderNo, String minCodeLength, String maxCodeLength, HttpServletRequest request, HttpServletResponse response, Model model) {
        JsonEntityResult result = new JsonEntityResult();
        VideoSessionCodeOrder order = new VideoSessionCodeOrder();
        order.setBizOrderNo(bizOrderNo);
        order.setMinCodeLength(minCodeLength);
        order.setMaxCodeLength(maxCodeLength);
        VideoSessionResult videoSessionResult = baiduFaceService.videoSessionCode(order);
        if (!videoSessionResult.success()) {
            result.setSuccess(false);
            result.setMessage(videoSessionResult.getDetail());
        }
        VideoSessionDto dto = new VideoSessionDto();
        dto.setSessionId(videoSessionResult.getSessionId());
        dto.setCode(videoSessionResult.getCode());
        result.setEntity(dto);
        return result;
    }

    /**
     * 获取视频语音验证码
     */
    @ResponseBody
    @PostMapping(value = {"/videoVerify"})
    @ApiOperation("人脸视频验证")
    @ApiImplicitParams({@ApiImplicitParam(name = "bizOrderNo", value = "业务订单号", required = true, paramType = "query"),
            @ApiImplicitParam(name = "sessionId", value = "获取视频语音验证码返回的sessionId", required = true, paramType = "query"),
            @ApiImplicitParam(name = "name", value = "姓名（公安验证必传）", required = false, paramType = "query"),
            @ApiImplicitParam(name = "idCardNumber", value = "身份证号（公安验证必传）", required = false, paramType = "query"),
            @ApiImplicitParam(name = "personVerify", value = "是否同步进行公安验证", required = false, paramType = "query")})
    public JsonEntityResult<VideoVerifyDto> videoVerify(String bizOrderNo, String sessionId, String name, String idCardNumber, boolean personVerify, @ApiParam(value = "视频文件") @RequestParam("videoFile") MultipartFile videoFile,
                                                        HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        JsonEntityResult result = new JsonEntityResult();
        VideoVerifyOrder order = new VideoVerifyOrder();
        order.setBizOrderNo(bizOrderNo);
        order.setSessionId(sessionId);
        order.setName(name);
        order.setIdCardNumber(idCardNumber);
        order.setVideo(videoFile.getBytes());
        order.setPersonVerify(personVerify);
        VideoVerifyResult videoVerifyResult = baiduFaceService.videoVerify(order);
        if (!videoVerifyResult.success()) {
            result.setSuccess(false);
            result.setMessage(videoVerifyResult.getDetail());
        }
        VideoVerifyDto dto = new VideoVerifyDto();
        BeanCopier.copy(videoVerifyResult, dto, "picDtoList", "highScorePicDto");
        result.setEntity(dto);
        return result;
    }
}
