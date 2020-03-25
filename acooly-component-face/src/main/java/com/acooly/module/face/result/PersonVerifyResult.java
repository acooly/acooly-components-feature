package com.acooly.module.face.result;

import com.acooly.core.common.facade.ResultBase;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author liangsong
 * @date 2020-03-24 11:14
 */
@Data
public class PersonVerifyResult extends ResultBase {

    /**
     * 流水id
     */
    private String logId;

    /**
     * 是否通过
     */
    private boolean isPass = true;

    /**
     * 相似比分值，正常分数时为[0~100]，推荐阈值80，超过即判断为同一人
     */
    private BigDecimal score;
}
