package com.acooly.module.chart.handle.data;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.acooly.module.ds.AbstractJdbcTemplateDao;
import com.google.common.collect.Lists;

/**
 * 数据查询服务器
 * 
 * @author cuifuq
 *
 */
@Service("chartDataQueryService")
public class ChartDataQueryService extends AbstractJdbcTemplateDao {

	public List<Map<String, Object>> querySql(String sql) {
		List<Map<String, Object>> lists = Lists.newArrayList();
		try {
			lists = jdbcTemplate.queryForList(sql);
		} catch (Exception e) {
			throw new RuntimeException("数据查询错误,请检查sql语句是否可以执行" + e);
		}
		return lists;
	}

}
