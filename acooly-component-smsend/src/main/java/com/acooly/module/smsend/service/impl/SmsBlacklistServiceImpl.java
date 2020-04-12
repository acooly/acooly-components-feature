/**
 * create by zhangpu date:2015年8月28日
 */
package com.acooly.module.smsend.service.impl;


import com.acooly.module.smsend.SmsendProperties;
import com.acooly.module.smsend.domain.SmsBlackList;
import com.acooly.module.smsend.enums.StatusEnum;
import com.acooly.module.smsend.service.BlackListService;
import com.acooly.module.smsend.service.SmsBlacklistService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<String> getAll() {
        List<String> blackListFromDB = getBlackListFromDB();
        if (blackListFromDB != null) {
            return blackListFromDB;
        }
        return smsProperties.getBlacklist();
    }

    @Override
    public boolean inBlacklist(String mobileNo) {
        List<String> blackListFromDB = getBlackListFromDB();
        if (blackListFromDB != null) {
            return blackListFromDB.contains(mobileNo);
        }
        return smsProperties.getBlacklist().contains(mobileNo);
    }

    private List<String> getBlackListFromDB() {
        List<SmsBlackList> all = blackListService.getAll();
        List<String> res = Lists.newArrayList();
        if (all != null && !all.isEmpty()) {
            all.forEach(
                    smsBlackList -> {
                        if (smsBlackList.getStatus() == StatusEnum.enable) {
                            res.add(smsBlackList.getMobile());
                        }
                    });
            return res;
        }
        return null;
    }
}
