package com.acooly.module.ocr.result;

import com.acooly.core.common.facade.ResultBase;
import com.acooly.module.ocr.enums.BankCardTypeOcrEnum;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cuifuq
 */
@Data
public class BankcardOcrResult extends ResultBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**百度流水号**/
	private String logId;

	/**银行卡卡号**/
	private String bankCardNumber;

	/**有效期  :格式：2002/09，NO VALID **/
	private String validDate;

	/**银行卡类型:不能识别; 借记卡; 信用卡**/
	private BankCardTypeOcrEnum bankCardType;

	/**银行名，不能识别时为空**/
	private String bankName;

}
