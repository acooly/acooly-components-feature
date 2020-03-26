package com.acooly.module.ocr.result;

import com.acooly.core.common.facade.ResultBase;
import com.acooly.module.ocr.dto.IdCardOcrBackDto;
import com.acooly.module.ocr.dto.IdCardOcrFrontDto;
import com.acooly.module.ocr.enums.ImageStatusEnum;
import com.acooly.module.ocr.enums.RiskTypeEnum;
import lombok.Data;

/**
 * @author liangsong
 * @date 2020-03-25 14:50
 */
@Data
public class IdCardOcrResult extends ResultBase {

    /**
     * 本次请求的百度流水号
     */
    private String logId;

    /**
     * 状态
     */
    private ImageStatusEnum imageStatus;

    /**
     * 用于校验身份证号码、性别、出生是否一致
     * -1: 身份证正面所有字段全为空
     * 0: 身份证证号识别错误
     * 1: 身份证证号和性别、出生信息一致
     * 2: 身份证证号和性别、出生信息都不一致
     * 3: 身份证证号和出生信息不一致
     * 4: 身份证证号和性别信息不一致
     * 当识别为1时 isPass = true;
     */
    private String idCardNumberType;


    /**
     * 身份证类型，输入参数 detectRisk = true 时，则返回该字段
     */
    private RiskTypeEnum riskType;

    /**
     * 编辑软件的名称,输入参数 detectRisk = true 时，则返回该字段
     * 如果检测身份证被编辑过，该字段指定编辑软件名称，如:Adobe Photoshop CC 2014 (Macintosh),如果没有被编辑过则返回值为空
     */
    private String editTool;

    /**
     * 身份证正面信息
     */
    private IdCardOcrFrontDto idCardOcrFrontDto;

    /**
     * 身份证背面信息
     */
    private IdCardOcrBackDto idCardOcrBackDto;

}
