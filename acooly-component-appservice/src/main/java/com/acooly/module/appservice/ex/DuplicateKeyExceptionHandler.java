package com.acooly.module.appservice.ex;

import com.acooly.core.common.facade.ResultBase;
import com.acooly.core.common.facade.ResultCode;
import com.acooly.core.utils.enums.ResultStatus;
import org.springframework.dao.DuplicateKeyException;

/**
 * @author qiubo@yiji.com
 */
public class DuplicateKeyExceptionHandler implements ExceptionHandler<DuplicateKeyException> {

    public void handle(ExceptionContext<?> context, DuplicateKeyException e) {
        ResultBase res = context.getResponse();
        res.setStatus(ResultStatus.failure);
        res.setDetail(ResultCode.REQUEST_NO_NOT_UNIQUE.getMessage());
        res.setCode(ResultCode.REQUEST_NO_NOT_UNIQUE.getCode());
    }
}
