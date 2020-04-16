package com.acooly.module.ocr.enums;

/**
 * @author liangsong
 * @date 2020-03-25 15:45
 */

import com.acooly.core.utils.enums.Messageable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public enum VehicleLicenseSideEnum implements Messageable {

	front("front", "识别行驶证主页"),

	back("back", "识别行驶证副页"),;

	private final String code;
	private final String message;

	VehicleLicenseSideEnum(String code, String message) {
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
		for (VehicleLicenseSideEnum type : values()) {
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
	public static VehicleLicenseSideEnum find(String code) {
		for (VehicleLicenseSideEnum status : values()) {
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
	public static List<VehicleLicenseSideEnum> getAll() {
		List<VehicleLicenseSideEnum> list = new ArrayList<VehicleLicenseSideEnum>();
		for (VehicleLicenseSideEnum status : values()) {
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
		for (VehicleLicenseSideEnum status : values()) {
			list.add(status.code());
		}
		return list;
	}

	@Override
	public String toString() {
		return this.code + ":" + this.message;
	}

}
