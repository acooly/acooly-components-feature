package com.acooly.module.face.order;

import com.acooly.module.face.enums.ControlEnum;
import com.acooly.module.face.enums.LipIdentifyEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @author liangsong
 * @date 2020-03-23 09:54
 */
@Data
public class VideoVerifyOrder implements Serializable {

    /**
     * 相关联的业务订单号，原样返回
     */
    private String bizOrderNo;

    /**
     * 会话ID，为空，则不校验语音唇语验证
     */
    private String sessionId;

    /**
     * 唇语
     */
    private LipIdentifyEnum lipIdentify = LipIdentifyEnum.OFF;

    /**
     * 需要使用合成图功能时, 此项传入spoofing
     */
    private String faceField;

    /**
     * 视频文件输入流
     */
    private byte[] video;

    /**
     * 是否同步进行公安认证
     */
    private boolean personVerify = false;

    /**
     * 证件号
     */
    private String idCardNumber;

    /**
     * 姓名
     */
    private String name;

    /**
     * 图片质量控制
     */
    private ControlEnum qualityControl = ControlEnum.NONE;

    /**
     * 活体检测控制
     */
    private ControlEnum livenessControl = ControlEnum.NONE;
}
