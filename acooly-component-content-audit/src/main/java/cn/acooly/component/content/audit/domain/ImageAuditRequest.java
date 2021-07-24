/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2021-07-23 16:17
 */
package cn.acooly.component.content.audit.domain;

import cn.acooly.component.content.audit.enums.ImageScenes;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.UUID;

/**
 * @author zhangpu
 * @date 2021-07-23 16:17
 */
@Slf4j
@Data
@NoArgsConstructor
public class ImageAuditRequest {

    private String bizType = "default";

    /**
     * 指定检测场景。取值：
     * porn：图片智能鉴黄
     * terrorism：图片暴恐涉政
     * ad：图文违规
     * qrcode：图片二维码
     * live：图片不良场景
     * logo：图片logo
     * 支持指定多个场景，例如，[“porn”, “terrorism”]表示对图片同时进行智能鉴黄和暴恐摄政检测。
     */
    @NotEmpty
    private List<String> scenes = Lists.newArrayList();

    @NotEmpty
    private List<ImageAuditTask> tasks = Lists.newArrayList();


    public ImageAuditRequest(String imageUrl) {
        addTask(imageUrl);
    }

    public void addScenes(ImageScenes... imageScenes) {
        for (ImageScenes is : imageScenes) {
            addScene(is.code());
        }
    }

    public void addScene(String scene) {
        this.scenes.add(scene);
    }

    public void addTask(String dataId, String imageUrl) {
        this.tasks.add(new ImageAuditTask(dataId, imageUrl));
    }

    public void addTask(String imageUrl) {
        this.addTask(UUID.randomUUID().toString(), imageUrl);
    }


}
