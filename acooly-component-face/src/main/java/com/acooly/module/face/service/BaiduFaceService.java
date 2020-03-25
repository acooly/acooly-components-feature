package com.acooly.module.face.service;

import com.acooly.module.face.order.IdMatchOrder;
import com.acooly.module.face.order.PersonVerifyOrder;
import com.acooly.module.face.order.VideoSessionCodeOrder;
import com.acooly.module.face.order.VideoVerifyOrder;
import com.acooly.module.face.result.IdMatchResult;
import com.acooly.module.face.result.PersonVerifyResult;
import com.acooly.module.face.result.VideoSessionResult;
import com.acooly.module.face.result.VideoVerifyResult;

/**
 * @author liangsong
 * @date 2020-03-20 14:54
 */
public interface BaiduFaceService {

    /**
     * 获取视频语音验证码
     *
     * @param order
     * @return
     */
    VideoSessionResult videoSessionCode(VideoSessionCodeOrder order);

    /**
     * 人脸语音+视频验证+公安身份认证(可选)
     *
     * @param order
     * @return
     */
    VideoVerifyResult videoVerify(VideoVerifyOrder order);

    /**
     * 身份认证（人脸质量检测（可选）->活体检测（可选）->公安身份验证（必选））
     *
     * @param order
     * @return
     */
    PersonVerifyResult personVerify(PersonVerifyOrder order);

    /**
     * 身份证和姓名对比
     * @param order
     * @return
     */
    IdMatchResult idMatch(IdMatchOrder order);
}
