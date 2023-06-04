package com.acooly.module.syncdata;

import com.google.common.collect.Maps;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @author cuifuq
 */
@ConfigurationProperties(prefix = SyncDataProperties.PREFIX)
@Data
public class SyncDataProperties {


    public static final String PREFIX = "acooly.syncdata";

    private Boolean enable = true;

    /**
     * 积分系统-业务系统业务扩充枚举
     * <li>当业务系统定义的业务场景枚举无法覆盖实际场景，支持枚举扩充（例如：用户奖励，客服奖励等等）
     * <li>格式：acooly.syncdata.busiTypeEnum[user]=用户
     */
    private Map<String, String> busiTypeEnum = Maps.newHashMap();


    public Map<String, String> getBusiTypeEnum() {
        Map<String, String> busiTypes = busiTypeEnum;
        Map<String, String> maps = Maps.newHashMap();
        if (maps.isEmpty()) {
            maps.put("default", "默认");
        }
        busiTypes.putAll(maps);
        return busiTypeEnum;
    }


}
