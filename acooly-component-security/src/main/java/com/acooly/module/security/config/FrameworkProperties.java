package com.acooly.module.security.config;

import com.acooly.module.security.domain.User;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author qiubo
 */
@ConfigurationProperties("acooly.framework")
@Getter
@Setter
@ToString
public class FrameworkProperties implements Serializable {
    public static final int SHOW_STATE_YES = 0;
    public static final int SHOW_STATE_NO = 1;
    public static final int SHOW_MODE_AJAXLOAD = 1;
    public static final int SHOW_MODE_IFRAME = 2;
    public static final int LOAD_MODE_URL = 1;
    public static final int LOAD_MODE_HTML = 2;
    public static final String RESOURCE_TYPE_URL = "URL";
    public static final String RESOURCE_TYPE_FUNCTION = "FUNCTION";
    public static final String RESOURCE_TYPE_MENU = "MENU";
    private static final long serialVersionUID = 1L;
    public Map<Integer, String> showStates = Maps.newLinkedHashMap();
    public Map<Integer, String> showModes = Maps.newLinkedHashMap();
    public Map<Integer, String> loadModes = Maps.newLinkedHashMap();
    public Map<String, String> resourceTypes = Maps.newLinkedHashMap();
    /**
     * 登录短信验证码重新发送时间间隔 s
     */
    public int smsSendInterval = 30;
    private String title = "Acooly Boss 4.x";
    private String subtitle = "专注于业务开发，规范最佳实践，自动代码生成，提高70%效率！";
    private String logo = "/manage/assert/image/logo.png";
    private String copyright = "Copyright © 2016 acooly. All rights reserved";
    /** 外部扩展css */
    private List<String> styles = new ArrayList<>();
    /** 外部扩展js */
    private List<String> scripts = new ArrayList<>();

    /**
     * 是否开启同名用户登录互斥 开关 [未实现]
     */
    private boolean conflict = false;
    /**
     * 是否开启密码过期处理 开关
     */
    private boolean expire = true;
    /**
     * 多长时间密码过期 单位天
     */
    private int expireDays = 90;
    /**
     * 密码错误次数锁定 开关
     */
    private boolean loginLock = false;
    /**
     * 密码错误几次后触发锁定
     */
    private int loginLockErrorTimes = 5;
    /**
     * 密码锁定时长 秒
     */
    private long loginLockSeconds = 43200;
    /**
     * 密码格式组成规则
     */
    //[a-zA-Z]{1}[\\\\w]{7,15}  密码必须以字母开头，由字母、数字、下划线组成，长度8-16字节。
    private String passwordRegex = "[\\\\w]{6,16}";
    /**
     * 密码格式错误提示
     */
    private String passwordError = "密码由任意字母、数字、下划线组成，长度6-16字节";
    /**
     * 用户状态
     */
    private Map<Integer, String> userStatus = Maps.newLinkedHashMap();
    /**
     * 用户类型
     */
    private Map<Integer, String> userTypes = Maps.newLinkedHashMap();

    public FrameworkProperties() {
        userStatus.put(User.STATUS_ENABLE, "正常");
        userStatus.put(User.STATUS_LOCK, "冻结");
        userStatus.put(User.STATUS_EXPIRES, "密码过期");
        userStatus.put(User.STATUS_DISABLE, "禁用");
        userTypes.put(1, "管理员");
        userTypes.put(2, "操作员");
        showStates.put(SHOW_STATE_YES, "显示");
        showStates.put(SHOW_STATE_NO, "隐藏");
        showModes.put(SHOW_MODE_AJAXLOAD, "AJAX加载");
        showModes.put(SHOW_MODE_IFRAME, "IFrame加载");
        loadModes.put(LOAD_MODE_URL, "URL");
        loadModes.put(LOAD_MODE_HTML, "文本");
        resourceTypes.put(RESOURCE_TYPE_URL, "URL");
        resourceTypes.put(RESOURCE_TYPE_FUNCTION, "FUNCTION");
        resourceTypes.put(RESOURCE_TYPE_MENU, "MENU");
    }

    public void setTitle(String title) {
        if (!Strings.isNullOrEmpty(title)) {
            this.title = title;
            this.logo = null;
        }
    }
}