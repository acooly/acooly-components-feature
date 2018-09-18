/**
 * create by zhangpu date:2016年1月10日
 */
package com.acooly.module.app.portal;

import com.acooly.module.app.domain.AppVersion;

/**
 * App下载信息
 *
 * @author zhangpu
 * @date 2016年1月10日
 */
public class AppDownload {

    private String profile;

    private AppVersion iphone;

    private AppVersion android;

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public AppVersion getIphone() {
        return iphone;
    }

    public void setIphone(AppVersion iphone) {
        this.iphone = iphone;
    }

    public AppVersion getAndroid() {
        return android;
    }

    public void setAndroid(AppVersion android) {
        this.android = android;
    }
}
