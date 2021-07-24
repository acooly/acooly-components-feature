/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2021-07-24 11:25
 */
package cn.acooly.component.content.audit.enums;

/**
 * @author zhangpu
 * @date 2021-07-24 11:25
 */

public enum AliyunComError {

    OK(200, "请求成功。"),
    PROCESSING(280, "任务正在执行中，建议您等待一段时间（例如5s）后再查询结果。"),
    BAD_REQUEST(400, "请求有误，通常由于请求参数不正确导致，请仔细检查请求参数。"),
    NOT_ALLOWED(401, "请求失败，通常是由于使用了不安全的图片、视频、语音链接地址。"),
    FORBIDDEN(403, "请求访问失败，通常由于您的图片、视频、语音链接无法访问导致，请确认公网是否可访问，并且无防盗链策略。"),
    NOT_FOUND(404, "待检测内容未找到，通常是由于您的图片、视频、语音内容无法下载导致，请确认内容可通过公网访问到。"),
    DOWNLOAD_FAILED(480, "下载失败，请确认待检测内容的大小、分辨率（如果有）在API的限制范围内。"),
    GENERAL_ERROR(500, "一般是服务端临时出错。建议重试，若持续返回该错误码，请通过工单联系我们。"),
    DB_FAILED(580, "数据库操作失败。建议重试，若持续返回该错误码，请通过工单联系我们。"),
    TIMEOUT(581, "超时。建议重试，若持续返回该错误码，请通过工单联系我们。"),
    CACHE_FAILED(585, "缓存出错。建议重试，若持续返回该错误码，请通过工单联系我们。"),
    ALGO_FAILED(586, "算法出错。请通过工单联系我们。"),
    MQ_FAILED(587, "中间件出错。请通过工单联系我们。"),
    EXCEED_QUOTA(588, "请求频率超出配额。默认配额：图片检测50张/秒，视频检测20路/秒，语音检测20路/秒，文本检测100条/秒。如果需要调整配额，请通过工单联系我们。说明 关于价格说明，请参见内容安全产品定价。"),
    TOO_LARGE(589, "待检测内容过大，请确保检测的内容在API的限制范围内。建议重试，若持续返回该错误码，请通过工单联系我们。"),
    BAD_FORMAT(590, "待检测内容格式错误，请确保检测的内容在API的限制范围内。"),
    CONNECTION_POOL_FULL(591, "连接池满。请通过工单联系我们。"),
    DOWNLOAD_TIMEOUT(592, "下载超时，下载时间限制为3s，请确保检测的内容大小在API的限制范围内。"),
    EXPIRED(594, "任务过期，如taskId过期。"),
    CATCH_FRAME_FAILED(595, "截帧失败，请通过工单联系我们。"),
    PERMISSION_DENY(596, "账号未授权、账号欠费、账号未开通、账号被禁等原因，具体可以参考返回的msg。");

    private final Integer code;
    private final String message;

    AliyunComError(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


    /**
     * 通过枚举值码查找枚举值。
     *
     * @param code 查找枚举值的枚举值码。
     * @return 枚举值码对应的枚举值。
     * @throws IllegalArgumentException 如果 code 没有对应的 Status 。
     */
    public static AliyunComError find(Integer code) {
        for (AliyunComError status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }

}
