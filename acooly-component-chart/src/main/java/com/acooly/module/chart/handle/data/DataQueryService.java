package com.acooly.module.chart.handle.data;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.acooly.module.ds.AbstractJdbcTemplateDao;

/**
 * 数据查询服务器
 * 
 * @author cuifuq
 *
 */
@Service("dataQueryService")
public class DataQueryService extends AbstractJdbcTemplateDao {

	public Map<String, Object> querySql(String sql) {

		List<Map<String, Object>> lists = jdbcTemplate.queryForList(sql);
		for (Map<String, Object> map : lists) {
			System.out.println(map);
		}
		return null;
	}

}
