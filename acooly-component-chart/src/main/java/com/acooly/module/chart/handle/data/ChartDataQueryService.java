package com.acooly.module.chart.handle.data;

import com.acooly.core.utils.Collections3;
import com.acooly.module.chart.entity.ChartItems;
import com.acooly.module.ds.AbstractJdbcTemplateDao;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 数据查询服务器
 *
 * @author cuifuq
 */
@Service("chartDataQueryService")
public class ChartDataQueryService extends AbstractJdbcTemplateDao {

    @Autowired
    private RedisTemplate<String, List<Map<String, Object>>> redisTemplate;

    public List<Map<String, Object>> querySql(String sql, long chartItemsId, long dataCacheTime) {
        String dataCacheKey = ChartItems.redisKey + chartItemsId;
        List<Map<String, Object>> lists = Lists.newArrayList();
        try {
            //获取redis缓存
            lists = redisTemplate.opsForValue().get(dataCacheKey);
            if (Collections3.isNotEmpty(lists)) {
                return lists;
            }
            lists = jdbcTemplate.queryForList(sql);
            //设置redis缓存
            if (dataCacheTime > 0) {
                redisTemplate.opsForValue().set(dataCacheKey, lists, dataCacheTime, TimeUnit.MINUTES);
            }
        } catch (Exception e) {
            throw new RuntimeException("数据查询错误,请检查sql语句是否可以执行" + e);
        }
        return lists;
    }

}
