package com.acooly.module.face.result;

import com.acooly.core.common.facade.ResultBase;
import lombok.Data;

/**
 * @author liangsong
 * @date 2020-03-24 15:16
 */
@Data
public class IdMatchResult extends ResultBase {
    /**
     * 是否通过
     */
    private boolean isPass = true;
}
