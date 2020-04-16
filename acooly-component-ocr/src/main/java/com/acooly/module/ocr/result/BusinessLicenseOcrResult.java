package com.acooly.module.ocr.result;

import com.acooly.core.common.facade.ResultBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cuifuq
 */
@Data
public class BusinessLicenseOcrResult extends ResultBase {

	/**百度流水号**/
	private String logId;

	/**公司名称**/
	private String companyName;

	/**类型:有限责任公司**/
	private String type;

	/**法人**/
	private String legalPerson;

	/**地址**/
	private String address;

	/**有效期:永久**/
	private String validDate;

	/**成立日期,格式yyyy年MM月dd日**/
	private String buildDate;

	/**证件编号**/
	private String certificateNum;

	/**社会信用代码**/
	private String socialCreditCode;
}
