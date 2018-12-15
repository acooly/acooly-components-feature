package com.acooly.core.test.web;

import com.acooly.module.obs.ObsService;
import com.acooly.module.obs.common.model.ObjectMetadata;
import com.acooly.module.obs.common.model.ObjectResult;
import com.acooly.module.obs.common.model.ObsObject;
import com.acooly.module.obs.common.util.BinaryUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.Date;

/**
 * @author shuijing
 */
@Slf4j
@RestController
@RequestMapping(value = "/obstest")
public class ObsTestController {
    final int instreamLength = 128 * 1024;
    @Autowired(required = false)
    private ObsService obsService;

    public static InputStream genFixedLengthInputStream(long fixedLength) {
        byte[] buf = new byte[(int) fixedLength];
        for (int i = 0; i < buf.length; i++) buf[i] = 'a';
        return new ByteArrayInputStream(buf);
    }

    @RequestMapping("aliyun")
    public void testAliyunObs() {
        String bucketName = "yijifu-acooly";
        String key = "test/logo.png";
        File file = new File("D:\\tmp\\t.log");
        ObjectResult result = obsService.putObject(bucketName, key, file);
        log.info("put result:" + result.toString());
    }

    @RequestMapping("aliyunInput")
    public void testAliyunInput() {
        String bucketName = "yijifu-acooly";
        String key = "test/abc";
        ObjectResult result =
                obsService.putObject(bucketName, key, genFixedLengthInputStream(instreamLength));
        log.info("put result:" + result.toString());
    }

    @RequestMapping("aliyunInputMeta")
    public void aliyunInputMeta() {

        String content = "Hello OSS";
        String bucketName = "yijifu-acooly";
        String key = "test/abc.txt";
        ObjectMetadata meta = new ObjectMetadata();
        // 设置上传文件长度
        meta.setContentLength(content.length());
        meta.setExpirationTime(DateUtils.addDays(new Date(), 30));
        // 设置上传MD5校验
        String md5 = BinaryUtil.toBase64String(BinaryUtil.calculateMd5(content.getBytes()));
        meta.setContentMD5(md5);
        // 设置上传内容类型
        meta.setContentType("text/plain");
        ObjectResult result =
                obsService.putObject(bucketName, key, genFixedLengthInputStream(instreamLength), meta);
        log.info("put result:" + result.toString());
    }

    @RequestMapping("aliyunInputMetaFile")
    public void aliyunInputMetaFile() {
        File file = new File("D:\\tmp\\logo.png");

        String bucketName = "yijifu-acooly";
        String key = "test/logoAbc.png";

        ObjectMetadata meta = new ObjectMetadata();
        meta.setCacheControl("no-cache");
        meta.addUserMetadata("property", "property-value");

        ObjectResult result = obsService.putObject(bucketName, key, file, meta);
        log.info("put result:" + result.toString());
    }

    @RequestMapping("aliyun/getObject")
    public void getObject() throws IOException {
        String bucketName = "yijifu-acooly";
        String key = "test/logoAbc.png";
        ObsObject object = obsService.getObject(bucketName, key);
        InputStream inputStream = object.getObjectContent();
        FileOutputStream out = new FileOutputStream(new File("D:\\tmp\\logoAbc.png"));
        IOUtils.copy(inputStream, out);
        out.flush();
        out.close();
        inputStream.close();
    }

    @RequestMapping("aliyun/deleteObject")
    public void deleteObject() throws IOException {
        String bucketName = "yijifu-acooly";
        String key = "test/logoAbc.png";
        obsService.deleteObject(bucketName, key);
    }
}
