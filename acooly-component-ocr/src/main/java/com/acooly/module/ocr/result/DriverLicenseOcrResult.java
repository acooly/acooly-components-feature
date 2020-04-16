package com.acooly.module.ocr.result;

import com.acooly.core.common.facade.ResultBase;

import lombok.Data;

/**
 * @author liangsong
 * @date 2020-03-26 09:40
 */
@Data
public class DriverLicenseOcrResult extends ResultBase {

   /**百度流水号**/
    private String logId;

   /**姓名**/
    private String name;

   /**身份证号**/
    private String idCardNo;

   /**性别**/
    private String gender;

   /**国籍**/
    private String nationality;

   /**住址**/
    private String address;

   /**出生日期,格式yyyy-MM-dd**/
    private String birthday;

   /**初次领证日期,格式yyyy-MM-dd**/
    private String firstIssueDate;

   /**准驾车型**/
    private String type;

   /**有效起始日期,格式yyyy-MM-dd**/
    private String validFrom;

   /**有效期限长度, 或者有效期限截止日期,新老驾驶证识别结果不一样，截止日期有可能是日期格式，也有可能是6年，10年，长期**/
    private String validTo;
}
