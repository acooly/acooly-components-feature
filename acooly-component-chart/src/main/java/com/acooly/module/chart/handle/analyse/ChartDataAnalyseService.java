package com.acooly.module.chart.handle.analyse;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.acooly.module.chart.handle.analyse.dto.ShaftDataDto;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Service("chartDataAnalyseService")
public class ChartDataAnalyseService {

	/**
	 * x，y轴数据解析
	 * 
	 * @return
	 */
	public ShaftDataDto shaftDataAnalyse(String title, String xShaftJson, String yShaftJson,
			List<Map<String, Object>> dbData) {

		ShaftDataDto dto = new ShaftDataDto();
		dto.setTitle(title);

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

		System.out.println(dto);

		return dto;
	}
}
