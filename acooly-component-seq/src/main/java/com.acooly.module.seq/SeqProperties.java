package com.acooly.module.seq;

import com.acooly.core.common.exception.AppConfigException;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;

import static com.acooly.module.seq.SeqProperties.PREFIX;

/**
 * @author qiubo@yiji.com
 */
@ConfigurationProperties(PREFIX)
@Data
public class SeqProperties {
    public static final String PREFIX = "acooly.seq";
    private boolean enable = true;

    /**
     * 一批次取的号
     */
    private int countInBatch = 1000;

    @PostConstruct
    public void init() {
        if (countInBatch <= 0) {
            throw new AppConfigException(countInBatch + "必须大于1");
        }
    }
}
