/**
 * create by zhangpu date:2015年11月3日
 */
package com.acooly.module.app.notify;

import java.util.List;

/**
 * APP 通知服务
 *
 * @author zhangpu
 * @date 2015年11月3日
 */
public interface AppNotifyService {

    /**
     * 群发
     *
     * @param notifyMessage 消息
     * @param alias       发送目标，一般使用唯一用户标识<用别名来标识一个用户。一个设备只能绑定一个别名，但多个设备可以绑定同一个别名。一次推送最多 1000 个。>
     * @param save          是否持久化 true:持久化(系统消息)
     */
    void group(NotifyMessage notifyMessage, List<String> alias, boolean save);

    void group(NotifyMessage notifyMessage, List<String> alias, String sender, boolean save);
    
    /**
     * 
     * @param notifyMessage 消息
     * @param alias 发送目标，一般使用唯一用户标识<用别名来标识一个用户。一个设备只能绑定一个别名，但多个设备可以绑定同一个别名。一次推送最多 1000 个。>
     * @param tags  (需要购买极光的vip) 发送目标，用标签来进行大规模的设备属性、用户属性分群<用标签来进行大规模的设备属性、用户属性分群。 一次推送最多 20 个。>
     * @param sender 发送者
     * @param save 是否持久化 true:持久化(系统消息)
     */
    void group(NotifyMessage notifyMessage, List<String> alias, List<String> tags, String sender, boolean save);
    

    /**
     * 广播
     *
     * @param notifyMessage
     */
    void broadcast(NotifyMessage notifyMessage, boolean save);
}
