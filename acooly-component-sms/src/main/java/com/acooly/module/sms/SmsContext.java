/**
 * create by zhangpu date:2015年5月27日
 */
package com.acooly.module.sms;

/**
 * @author zhangpu
 */
public class SmsContext {
    /**
     * 是否需要check
     */
    private boolean needCheck = false;

    private String comments;

    private String clientIp;

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public boolean isNeedCheck() {
        return needCheck;
    }

    public void setNeedCheck(boolean needCheck) {
        this.needCheck = needCheck;
    }
}
