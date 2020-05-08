/**
 * create by zhangpu date:2015年8月28日
 */
package com.acooly.module.smsend.service;

import java.util.List;
import java.util.Set;

/**
 * @author zhangpu
 */
public interface SmsBlacklistService {

    Set<String> getAll();

    boolean inBlacklist(String mobileNo);
}
