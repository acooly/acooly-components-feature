package com.acooly.module.chart.handle;

import com.acooly.core.common.exception.BusinessException;

/**
 * @author cuifuqiang
 */

public class ChartUtils {
	
	/**
	 * 验证sql的合法性
	 * @param sqlDataUpperCase
	 */
	public static void checkSql(String sqlDataUpperCase) {
		if (sqlDataUpperCase.contains("INSERT ") || sqlDataUpperCase.contains("DELETE ")
				|| sqlDataUpperCase.contains("UPDATE ") || sqlDataUpperCase.contains("CREATE ")
				|| sqlDataUpperCase.contains("DROP ") || sqlDataUpperCase.contains("ALTER ")
				|| sqlDataUpperCase.contains("LOCK ") || sqlDataUpperCase.contains("UNLOCK ")) {
			throw new BusinessException(
					"sql语句中不能出现 <br/>insert,<br/>delete,<br/>update,<br/>create,<br/>drop,<br/>alter,<br/>lock,<br/>unlock");
		}
	}

}
