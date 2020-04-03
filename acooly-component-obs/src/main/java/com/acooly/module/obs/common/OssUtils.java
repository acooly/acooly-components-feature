package com.acooly.module.obs.common;

import com.acooly.core.utils.Dates;
import com.acooly.core.utils.Ids;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * @author liangsong
 * @date 2020-04-03 17:08
 */
public class OssUtils {

    public static String getUrlByKey(String protocol, String bucketName, String endpoint, String key) {
        return protocol.concat(bucketName).concat(endpoint).concat("/").concat(key);
    }

    public static String generateKey(String rootPackage, String filePath) {
        String result = "";
        String basePath = Dates.format(new Date(), "/yyyy/MM/dd");
        String randomId = Ids.oid();
        String fileExt = getFileExtention(filePath);
        return result.concat(rootPackage).concat(basePath).concat(randomId).concat(fileExt);
    }
    private static String getFileExtention(String filePath) {
        return StringUtils.substringAfterLast(filePath, ".");
    }
}
