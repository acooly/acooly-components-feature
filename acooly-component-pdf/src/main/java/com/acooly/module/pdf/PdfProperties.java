package com.acooly.module.pdf;

import com.acooly.core.common.boot.Apps;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.ResourceLoader;
import org.springframework.validation.annotation.Validated;

import static com.acooly.module.pdf.PdfProperties.PREFIX;

/**
 * @author shuijing
 */
@Data
@Slf4j
@Validated
@ConfigurationProperties(prefix = PREFIX)
public class PdfProperties {
    public static final String PREFIX = "acooly.pdf";

    public boolean enable;
    /**
     * pdf模板路径，模板放在此路径下<b> 文件名作为key,带后缀，文件内容为模板内容
     */
    private String templatePath = "classpath:/pdf/templates/";
    /**
     * pdf要加载的图片路径
     */
    private String imagePath = "classpath:/pdf/images/";
    /**
     * pdf要自定义加载的字体路径，已默认加载中文字体
     */
    private String fontsPath = "classpath:/pdf/fonts/";

    private ResourceLoader resourceLoader;

    public ResourceLoader getResourceLoader() {
        if (resourceLoader != null) {
            return resourceLoader;
        }
        return Apps.getApplicationContext();
    }
}
