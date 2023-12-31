package com.acooly.module.security.config;

import com.acooly.module.defence.password.PasswordStrength;
import com.acooly.module.security.domain.User;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author qiubo
 * @author zhangpu
 * <p>
 * todo:datagrid的动态列
 * todo:datagrid的列可调整宽度
 * todo:datagrid的列位置可调整
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

    private String title = "Acooly Boss 5.x";
    private String shorttitle = "<b>B</b>oss";
    private String subtitle = "专注业务，最佳实践，代码生成，提高效率";
    private String logo = "/manage/assert/image/logo.png";
    private String logoMini = "/manage/assert/plugin/adminlte3/img/AdminLTELogo.png";
    /**
     * 是否开启收藏夹功能，默认关闭（兼容老版本），如果开启，老系统需要执行升级SQL
     */
    private boolean enableFavorite = false;
    /**
     * 网站图标
     */
    private String icon;
    private String copyright = "Copyright © 2012 acooly. All rights reserved";

    /**
     * 默认样式，acooly,easyui, paiggio
     */
    private String defaultTheme = "acooly";

    /**
     * 字体大小
     */
    private int fontSize = 14;
    /**
     * 左侧菜单宽度
     */
    private int mainSidebarWidth = 250;

    /**
     * 外部扩展css
     */
    private List<String> styles = new ArrayList<>();

    /**
     * 外部扩展js
     */
    private List<String> scripts = new ArrayList<>();
    /**
     * 模块级外部扩展JS，用于组件内植入JS，解决scripts下标配置冲突问题。
     */
    private Map<String, List<String>> customScripts = Maps.newHashMap();

    private Map<String, List<String>> customStyles = Maps.newHashMap();

    /**
     * 是否开启同名用户登录互斥 开关 [未实现]
     */
    private boolean conflict = false;

    /**
     * 是否开启验证手机号唯一性验证，手机号（true:必须唯一，false:可以不唯一）
     */
    private boolean onlyMobile = true;

    /**
     * 是否开启验证邮箱唯一性验证，邮箱（true:必须唯一，false:可以不唯一）
     */
    private boolean onlyEmail = true;

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
     * 登录页面背景色和图片
     */
    private String loginViewBackColor = "#007cd9";
    private String loginViewBackImage = "/manage/assert/image/white-bg.png";

    /**
     * 用户状态
     */
    private Map<Integer, String> userStatus = Maps.newLinkedHashMap();
    /**
     * 用户类型
     */
    private Map<Integer, String> userTypes = Maps.newLinkedHashMap();

    /**
     * 密码强度
     * 控制：登录，设置/修改/找回密码等功能
     */
    private PasswordStrength passwordStrength = PasswordStrength.simple;

    private Plugin plugin = new Plugin();

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


    public Set<String> mergeScripts() {
        Set<String> scriptList = Sets.newLinkedHashSet();
        scriptList.addAll(this.scripts);
        for (List<String> customScript : this.customScripts.values()) {
            scriptList.addAll(customScript);
        }
        return scriptList;
    }

    public Set<String> mergeStyles() {
        Set<String> styleList = Sets.newLinkedHashSet();
        styleList.addAll(this.styles);
        for (List<String> customStyle : this.customStyles.values()) {
            styleList.addAll(customStyle);
        }
        return styleList;
    }


    /**
     * 前端插件库加载管理（JS和CSS插件库）
     */
    @Getter
    @Setter
    public static class Plugin implements Serializable {

        /**
         * 框架相关的JS是否DEBUG模式加载源文件
         */
        private boolean acoolyDebug = false;

        /**
         * LayUI插件
         */
        private boolean layui = true;
        /**
         * 多媒体编辑器
         */
        private boolean kindEditor = true;
        /**
         * EasyUI扩展
         */
        private boolean easyuiExtension = true;

        /**
         * 页面加载进度条，单点登录客户端建议关闭
         */
        private boolean pace = false;

        /**
         * 视频播放插件
         */
        private boolean videoJs = false;


        /**
         * bootstrip日期段选择
         */
        private boolean dateRangePicker = false;

        /**
         * bootstrip效果的check
         */
        private boolean icheck = false;

        /**
         * 客户端xss防御，除了等保三需要，没说明实际效果
         */
        private boolean xss = false;

        /**
         * 需要使用剪贴板时加载
         */
        private boolean clipboard = false;

        /**
         * PDF在线浏览支持
         */
        private boolean media = false;

        /**
         * datagrid扩展
         * 支持自定义显示列
         */
        private boolean datagridExt = false;
    }


}
