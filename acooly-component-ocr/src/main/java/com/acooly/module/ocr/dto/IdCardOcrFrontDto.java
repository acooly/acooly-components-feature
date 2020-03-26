package com.acooly.module.ocr.dto;

import com.acooly.core.utils.Dates;
import com.acooly.core.utils.Strings;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liangsong
 * @date 2020-03-25 16:21
 */
@Data
public class IdCardOcrFrontDto implements Serializable {

    /**
     * 姓名
     */
    private String name;

    /**
     * 民族（例如：汉）
     */
    private String nation;

    /**
     * 住址
     */
    private String address;

    /**
     * 身份证号
     */
    private String idCardNo;
    /**
     * 生日，例如：19890701
     */
    private String birthday;

    /**
     * 性别，例如：男
     */
    private String sex;

    /**
     * 出生年
     */
    private String year;

    /**
     * 出生月
     */
    private String month;

    /**
     * 出生日
     */
    private String day;

    public String getYear() {
        if (Strings.isNotBlank(birthday)) {
            Date date = Dates.parse(birthday, "yyyyMMdd");
            return String.valueOf(Dates.getYear(date));
        }
        return year;
    }

    public String getMonth() {
        if (Strings.isNotBlank(birthday)) {
            Date date = Dates.parse(birthday, "yyyyMMdd");
            return String.valueOf(Dates.getMonth(date));
        }
        return month;
    }

    public String getDay() {
        if (Strings.isNotBlank(birthday)) {
            Date date = Dates.parse(birthday, "yyyyMMdd");
            return String.valueOf(Dates.getDay(date));
        }
        return day;
    }
}
