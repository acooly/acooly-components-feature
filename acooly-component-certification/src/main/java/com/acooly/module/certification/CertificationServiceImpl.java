/*
 * 修订记录:
 * zhike@yiji.com 2017-04-21 10:14 创建
 *
 */
package com.acooly.module.certification;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.utils.enums.ResultStatus;
import com.acooly.module.certification.cert.*;
import com.acooly.module.certification.enums.*;
import com.acooly.module.certification.platform.entity.BankCertificationRecord;
import com.acooly.module.certification.platform.entity.CertificationRecord;
import com.acooly.module.certification.platform.service.BankCertificationRecordService;
import com.acooly.module.certification.platform.service.CertificationRecordService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.Assert;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 修订记录：
 *
 * @author zhike@yiji.com
 * @author shuijing
 */
@Slf4j
@Service("certificationService")
public class CertificationServiceImpl implements CertificationService {

    @Resource
    private RealNameAuthentication realNameAuthentication;

    @Resource
    private CertificationRecordService certificationRecordService;

    @Resource
    private BankCardCertService bankCardCertService;

    @Resource
    private BankCertificationRecordService bankCertificationRecordService;

    @Resource
    private EnterpriseBusinessInfoService enterpriseBusinessInfoService;

    @Resource
    private PhoneCertService phoneCertService;

    @Override
    public CertResult certification(String realName, String idCardNo) {
        long st = System.currentTimeMillis();
        CertResult result = new CertResult();
        CertificationRecord certificationRecord =
                certificationRecordService.findEntityByCarNoAndRealName(idCardNo, realName);
        try {
            if (certificationRecord != null && certificationRecord.getStatus() == 1) {
                result.setResultCode(ResultStatus.success.getCode());
                result.setResultMessage("实名认证成功");
                result.setBirthday(certificationRecord.getBirthday());
                result.setAddress(certificationRecord.getAddress());
                result.setSex(certificationRecord.getSex());
                result.setRealName(realName);
                result.setIdCardNo(idCardNo);
            } else {
                result = realNameAuthentication.certification(realName, idCardNo);
                saveRecord(result, certificationRecord);
            }
        } catch (RealNameAuthenticationException e) {
            result.setResultCode(e.getResultCode());
            result.setResultMessage(e.getResultMessage());
            throw e;
        }
        long et = System.currentTimeMillis();
        log.info("实名认证完成，花费时间: {} ms", (et - st));
        return result;
    }

    @Override
    public BankCardResult bankCardCertTwo(String realName, String cardNo) {
        Assert.hasText(realName);
        Assert.hasText(cardNo);
        return bankCardCertFour(realName, cardNo, null, null);
    }

    @Override
    public BankCardResult bankCardCertThree(String realName, String cardNo, String certId) {
        Assert.hasText(realName);
        Assert.hasText(cardNo);
        Assert.hasText(certId);
        return bankCardCertFour(realName, cardNo, certId, null);
    }

    @Override
    public BankCardResult bankCardCertFour(
            String realName, String cardNo, String certId, String phoneNum) {

        Assert.hasText(realName);
        Assert.hasText(cardNo);

        long st = System.currentTimeMillis();

        BankCardResult result = new BankCardResult();
        BankCertificationRecord record = bankCertificationRecordService.findEntityByCardNo(cardNo);
        try {
            if (record != null && record.getStatus() == 1) {
                result.setBelongArea(record.getBelongArea());
                result.setBankTel(record.getBankTel());
                result.setBrand(record.getBrand());
                result.setBankName(record.getBankName());
                result.setCardType(record.getCardType());
                result.setBankUrl(record.getBankUrl());
                result.setCardNo(record.getCardNo());
                result.setStatus(ResultStatus.success);
            } else {
                result = bankCardCertService.bankCardCert(realName, cardNo, certId, phoneNum);
                if (result.getStatus() == ResultStatus.failure) {
                    throw new CertficationException(ResultStatus.failure.getCode(), result.getDetail());
                }
                if (result.getStatus() == ResultStatus.success) {
                    saveBankCardCertRecord(result, record, realName, cardNo, certId, phoneNum);
                }
            }
        } catch (CertficationException e) {
            result.setCode(e.getResultCode());
            result.setStatus(ResultStatus.failure);
            result.setDetail(e.getResultMessage());
            throw e;
        }
        log.info("银行卡认证完成，耗时: {} ms", (System.currentTimeMillis() - st));
        return result;
    }

    @Override
    public EnterpriseBusinessInfoResult enterpriseBusinessInfo(String comInfo) {
        return enterpriseBusinessInfoService.enterpriseBusinessInfo(comInfo);
    }

    @Override
    public PhoneCertResult phoneCert(String realName, String certNo, String mobile) {
        Assert.hasText(realName);
        Assert.hasText(certNo);
        Assert.hasText(mobile);
        long st = System.currentTimeMillis();
        PhoneCertResult result = new PhoneCertResult();
        try {
            result = phoneCertService.phoneCert(realName, certNo, mobile);
        } catch (BusinessException e) {
            result.setStatus(ResultStatus.failure);
            result.setCode(e.getCode());
            result.setDetail(e.getMessage());
        }
        long et = System.currentTimeMillis();
        log.info("手机在网三要素认证，花费时间: {} ms，结果result={}", (et - st), result.toString());
        return result;
    }

    protected void saveRecord(CertResult result, CertificationRecord certificationRecord) {
        if (certificationRecord == null) {
            certificationRecord = new CertificationRecord();
        }
        certificationRecord.setStatus(
                StringUtils.equals(result.getResultCode(), ResultStatus.success.getCode()) ? 1 : 0);
        certificationRecord.setRealName(result.getRealName());
        certificationRecord.setIdCarNo(result.getIdCardNo());
        certificationRecord.setAddress(result.getAddress());
        certificationRecord.setBirthday(result.getBirthday());
        certificationRecord.setSex(result.getSex());
        certificationRecordService.save(certificationRecord);
    }

    protected void saveBankCardCertRecord(
            BankCardResult result,
            BankCertificationRecord entity,
            String realName,
            String cardNo,
            String certId,
            String phoneNum) {

        if (entity == null) {
            entity = new BankCertificationRecord();
        }
        entity.setCardType(result.getCardType());
        entity.setCardNo(cardNo);
        entity.setBankUrl(result.getBankUrl());
        entity.setBankTel(result.getBankTel());
        entity.setBankName(result.getBankName());
        entity.setBelongArea(result.getBelongArea());
        entity.setBrand(result.getBrand());
        entity.setRealName(realName);
        entity.setCertType(CertTypeEnum.cert_no);
        entity.setFailReason(result.getDetail());
        if (!StringUtils.isEmpty(certId)) {
            entity.setCertId(certId);
        }
        if (!StringUtils.isEmpty(phoneNum)) {
            entity.setPhoneNum(phoneNum);
        }
        if (ResultStatus.success == result.getStatus()) {
            entity.setStatus(1);
        }
        if (ResultStatus.failure == result.getStatus()) {
            entity.setStatus(0);
        }
        bankCertificationRecordService.save(entity);
    }
}
