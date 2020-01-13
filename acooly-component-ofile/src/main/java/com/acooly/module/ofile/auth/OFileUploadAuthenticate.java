/**
 * create by zhangpu date:2015年7月14日
 */
package com.acooly.module.ofile.auth;

import javax.servlet.http.HttpServletRequest;

/**
 * OFile上传认证接口
 *
 * <p>如果使用端需要对上传文件进行身份验证，可以自行实现该接口并放置于spring容器，组件会自动加载并使用客户化实现。
 *
 * @author zhangpu
 */
public interface OFileUploadAuthenticate {

    /**
     * 认证
     *
     * @param request
     */
    void authenticate(HttpServletRequest request);

    /**
     * 是否可用
     *
     * @return
     */
    default boolean isEnable() {
        return true;
    }

    /**
     * 优先级顺序
     * <p>
     * 越大越先执行
     *
     * @return
     */
    default int order() {
        return Integer.MAX_VALUE;
    }

}
