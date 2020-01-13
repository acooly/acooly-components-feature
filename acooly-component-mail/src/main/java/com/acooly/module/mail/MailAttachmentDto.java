package com.acooly.module.mail;

import com.acooly.core.common.exception.BusinessException;
import com.google.common.io.Files;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author shuijing
 */
@Getter
@Setter
public class MailAttachmentDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 附件内容
     */
    @NotNull
    private byte[] content;

    /**
     * 附件描述
     */
    private String description;

    /**
     * 附件名称
     */
    @NotBlank
    private String name;

    public void setContentFile(File file) {
        if (file == null) {
            throw new BusinessException("邮件附件不能为null.");
        }
        try {
            this.content = Files.toByteArray(file);
            this.name = file.getName();
        } catch (IOException e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public String toString() {
        return "name: " + this.getName() + "description: " + this.getDescription();
    }
}
