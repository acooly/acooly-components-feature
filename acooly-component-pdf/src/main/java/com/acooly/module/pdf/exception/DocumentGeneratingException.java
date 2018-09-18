package com.acooly.module.pdf.exception;

import com.acooly.core.common.exception.BusinessException;

/**
 * @author shuijing
 */
public class DocumentGeneratingException extends BusinessException {

    private static final long serialVersionUID = 1L;

    public DocumentGeneratingException() {
        super();
    }

    public DocumentGeneratingException(String message) {
        super(message);
    }

    public DocumentGeneratingException(String message, Throwable cause) {
        super(message, cause);
    }

    public DocumentGeneratingException(Throwable cause) {
        super(cause);
    }
}
