package com.acooly.module.sso.dic;

/**
 * @author shuijing
 */
public enum AuthResult {
    LOGIN_URL_NULL(1, "sso登录,请配置 sso 系统登录页面地址"),
    LOGIN_ERROR_DOMAIN(2, "域名检查失败,无法获取认证信息,请sso系统统一用一个二级域名，如 acooly.com"),
    LOGIN_REDIRECT(3, "重定向到sso登录"),
    LOGIN_AUTHENTICATION_TIME_OUT(4, "认证信息失效"),
    AUTHENTICATION_TAMPER(7, "认证信息被篡改"),
    AUTHENTICATION_ACCESS(8, "验证成功(没有被篡改, 没有失效)");

    private int value;
    private String description;

    /**
     * @param value
     * @param description
     */
    AuthResult(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
