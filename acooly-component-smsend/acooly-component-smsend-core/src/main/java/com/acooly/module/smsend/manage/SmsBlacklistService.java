/**
 * create by zhangpu date:2015年8月28日
 */
package com.acooly.module.smsend.manage;

import java.util.Set;

/**
 * @author zhangpu
 */
public interface SmsBlacklistService {

    Set<String> getAll();

    boolean inBlacklist(String mobileNo);
}
