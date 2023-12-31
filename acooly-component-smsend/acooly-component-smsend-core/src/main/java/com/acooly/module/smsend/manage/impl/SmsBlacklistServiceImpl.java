/**
 * create by zhangpu date:2015年8月28日
 */
package com.acooly.module.smsend.manage.impl;


import com.acooly.module.smsend.SmsSendProperties;
import com.acooly.module.smsend.entity.SmsBlackList;
import com.acooly.module.smsend.manage.BlackListService;
import com.acooly.module.smsend.manage.SmsBlacklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zhangpu
 */
@Service
public class SmsBlacklistServiceImpl implements SmsBlacklistService {
    @Autowired
    private SmsSendProperties smsProperties;
    @Autowired
    private BlackListService blackListService;

    @Override
    public Set<String> getAll() {
        Set<String> blackListFromDB = getBlackListFromDB();
        blackListFromDB.addAll(smsProperties.getBlacklist());
        return blackListFromDB;
    }

    @Override
    public boolean inBlacklist(String mobileNo) {
        return getAll().contains(mobileNo);
    }

    private Set<String> getBlackListFromDB() {
        return blackListService.getEffective().stream().map(SmsBlackList::getMobile).collect(Collectors.toSet());
    }
}
