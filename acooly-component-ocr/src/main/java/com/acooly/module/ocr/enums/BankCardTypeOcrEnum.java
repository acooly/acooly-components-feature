package com.acooly.module.ocr.enums;

/**
 * @author cuifuq
 */

import com.acooly.core.utils.enums.Messageable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public enum BankCardTypeOcrEnum implements Messageable {

	/** 信用卡 ***/
	creditCard("creditCard", "信用卡"),

	/** 借记卡 ***/
	debitCard("debitCard", "借记卡"),

	/** 不能识别 ***/
	unknown("unknown", "不能识别"),

	;

	private final String code;
	private final String message;

	BankCardTypeOcrEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String code() {
		return code;
	}

	@Override
	public String message() {
		return message;
	}

	public static Map<String, String> mapping() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (BankCardTypeOcrEnum type : values()) {
			map.put(type.getCode(), type.getMessage());
		}
		return map;
	}

	/**
	 * 通过枚举值码查找枚举值。
	 *
	 * @param code 查找枚举值的枚举值码。
	 * @return 枚举值码对应的枚举值。
	 * @throws IllegalArgumentException 如果 code 没有对应的 Status 。
	 */
	public static BankCardTypeOcrEnum find(String code) {
		for (BankCardTypeOcrEnum status : values()) {
			if (status.getCode().equals(code)) {
				return status;
			}
		}
		return null;
	}

	/**
	 * 获取全部枚举值。
	 *
	 * @return 全部枚举值。
	 */
	public static List<BankCardTypeOcrEnum> getAll() {
		List<BankCardTypeOcrEnum> list = new ArrayList<BankCardTypeOcrEnum>();
		for (BankCardTypeOcrEnum status : values()) {
			list.add(status);
		}
		return list;
	}

	/**
	 * 获取全部枚举值码。
	 *
	 * @return 全部枚举值码。
	 */
	public static List<String> getAllCode() {
		List<String> list = new ArrayList<String>();
		for (BankCardTypeOcrEnum status : values()) {
			list.add(status.code());
		}
		return list;
	}

	@Override
	public String toString() {
		return this.code + ":" + this.message;
	}
}
