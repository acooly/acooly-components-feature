/**
 * create by zhangpu date:2015年8月28日
 */
package com.acooly.module.smsend.service.impl;


import com.acooly.module.smsend.SmsendProperties;
import com.acooly.module.smsend.domain.SmsBlackList;
import com.acooly.module.smsend.service.BlackListService;
import com.acooly.module.smsend.service.SmsBlacklistService;
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
    private SmsendProperties smsProperties;
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
