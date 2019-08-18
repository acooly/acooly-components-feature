/**
 * create by zhangpu date:2015年5月10日
 */
package com.acooly.module.appopenapi.dto;

import com.acooly.openapi.framework.common.annotation.OpenApiField;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 媒体文件 DTO
 *
 * @author zhangpu
 */
public class MediaInfo {

    @OpenApiField(desc = "标题", demo = "启动封面图", ordinal = 1)
    private String title;

    @NotEmpty
    @OpenApiField(desc = "缩略图地址", demo = "/xxx/xxx/aaa_thumbnail.jpg", ordinal = 2)
    private String thumbnail;

    @NotEmpty
    @OpenApiField(desc = "图片地址", demo = "/xxx/xxx/aaa.jpg", ordinal = 3)
    private String image;

    @OpenApiField(desc = "内容链接", constraint = "点击图片后链接的地址", demo = "/xxx/xxx/xxx.html", ordinal = 4)
    private String link;

    @OpenApiField(desc = "备注", demo = "just memo!", ordinal = 5)
    private String comments;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
