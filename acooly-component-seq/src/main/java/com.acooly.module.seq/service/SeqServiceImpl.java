package com.acooly.module.seq.service;

import com.acooly.module.seq.SeqProperties;
import com.acooly.module.seq.SeqService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @author qiubo@yiji.com
 */
@Component
public class SeqServiceImpl implements SeqService {
    private Map<String, KeyService> cache = Maps.newConcurrentMap();
    @Autowired
    private DataSource dataSource;
    @Autowired
    private SeqProperties idProperties;

    @Override
    public long nextId(String bussinesName) {
        KeyService keyService = cache.get(bussinesName);
        if (keyService == null) {
            synchronized (this) {
                if (keyService == null) {
                    keyService = new KeyService(dataSource, bussinesName, idProperties.getCountInBatch());
                    cache.put(bussinesName, keyService);
                }
            }
        }
        return keyService.nextId();
    }
}
