package com.acooly.module.chart.handle.analyse;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.acooly.module.chart.handle.analyse.dto.ShaftDataDto;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Service("chartDataAnalyseService")
public class ChartDataAnalyseService {

	private static final Logger logger = LoggerFactory.getLogger(ChartDataAnalyseService.class);

	/**
	 * x，y轴数据解析
	 * 
	 * @return
	 */
	public ShaftDataDto shaftDataAnalyse(String title, String xShaftJson, String yShaftJson,
			List<Map<String, Object>> dbData) {
		ShaftDataDto dto = new ShaftDataDto();
		try {
			dto.setTitle(title);

			if (dbData.isEmpty()) {
				dto.setxShaft(Maps.newHashMap());
				dto.setyShafts(Lists.newArrayList());
				logger.info("数据库查询数据为空,请检查sql语句");
				return dto;
			}

			// x轴数据映射+解析
			Map<String, Object> xShaftMap = (Map<String, Object>) JSON.parseObject(xShaftJson);
			Set<String> xShaftKey = xShaftMap.keySet();

			// y轴数据映射+解析
			Map<String, Object> yShaftMap = (Map<String, Object>) JSON.parseObject(yShaftJson);
			Set<String> yShaftKey = yShaftMap.keySet();

			Map<String, String> dataMaps = Maps.newHashMap();

			// 数据解析(key,values)
			for (Map<String, Object> map : dbData) {
				Set<String> keySets = map.keySet();
				for (String key : keySets) {
					String value = String.valueOf(map.get(key));
					String oldValue = (String) dataMaps.get(key);
					if (StringUtils.isBlank(oldValue)) {
						dataMaps.put(key, value);
					} else {
						dataMaps.put(key, oldValue + "," + value);
					}
				}
			}

			// x轴key值替换
			Map<String, Object> xShaft = Maps.newHashMap();
			for (String xNameEn : xShaftKey) {
				String xValues = dataMaps.get(xNameEn);
				String xShaftKeyCn = String.valueOf(xShaftMap.get(xNameEn));
				xShaft.put(xShaftKeyCn, xValues);
				dto.setxShaft(xShaft);
			}

			// y轴key值替换
			List<Map<String, Object>> yShafts = Lists.newArrayList();
			Map<String, Object> yShaft = Maps.newHashMap();
			for (String yNameEn : yShaftKey) {
				String yValues = dataMaps.get(yNameEn);
				String yShaftKeyCn = String.valueOf(yShaftMap.get(yNameEn));
				yShaft.put(yShaftKeyCn, yValues);
			}
			yShafts.add(yShaft);
			dto.setyShafts(yShafts);

		} catch (Exception e) {
			throw new RuntimeException("数据解析错误,请检查数据查询结果" + e);
		}
		logger.info("图表数据:标题：[{}],数据库查询结果：{},数据解析后结果：{}", title, dbData, dto);
		return dto;
	}
}
