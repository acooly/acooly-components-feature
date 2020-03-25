package com.acooly.module.face.result;

import com.acooly.core.common.facade.ResultBase;
import lombok.Data;

/**
 * @author liangsong
 * @date 2020-03-20 14:55
 */
@Data
public class VideoSessionResult extends ResultBase {

    /**
     * 会话id，校验时需使用传入
     */
    private String sessionId;

    /**
     * 语音校验码
     */
    private String code;
}
