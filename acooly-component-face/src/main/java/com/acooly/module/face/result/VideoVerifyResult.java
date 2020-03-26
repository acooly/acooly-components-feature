package com.acooly.module.face.result;

import com.acooly.core.common.facade.ResultBase;
import com.acooly.module.face.dto.FacePicDto;
import com.google.common.collect.Lists;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author liangsong
 * @date 2020-03-23 10:21
 */
@Data
public class VideoVerifyResult extends ResultBase {

    /**
     * 业务订单号
     */
    private String bizOrderNo;

    /**
     * 是否通过
     */
    private boolean isPass = true;

    /**
     * 人脸识别评分 范围[0,1]，分数越高则活体的概率越大
     */
    private BigDecimal score;

    /**
     * 生成的验证码
     */
    private String createCode;

    /**
     * 验证码的语音识别结果
     */
    private String identifyCode;

    /**
     * 验证码相似度 取值0~1 1代表完全一致 0代表完全不一致 推荐阈值0.75
     */
    private BigDecimal similarity;

    /**
     * 唇语验证是否通过
     */
    private boolean lipLanguage = false;

    /**
     * 验证结果图片
     */
    private List<FacePicDto> picDtoList = Lists.newArrayList();

    /**
     * 最高验证结果图片
     */
    private FacePicDto highScorePicDto;

    /**
     * 身份证相似比分值，正常分数时为[0~100]，推荐阈值80，超过即判断为同一人，选择同步进行公安验证时才会返回
     */
    private BigDecimal idCardScore;

    public void addFacePicDto(FacePicDto dto) {
        picDtoList.add(dto);
    }
}
