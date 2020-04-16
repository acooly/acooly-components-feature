package com.acooly.module.ocr.portal.test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acooly.module.ocr.enums.IdCardSideEnum;
import com.acooly.module.ocr.enums.VehicleLicenseSideEnum;
import com.acooly.module.ocr.order.BankcardOcrOrder;
import com.acooly.module.ocr.order.BusinessLicenseOcrOrder;
import com.acooly.module.ocr.order.DriverLicenseOcrOrder;
import com.acooly.module.ocr.order.IdCardOcrOrder;
import com.acooly.module.ocr.order.VehicleLicenseOcrOrder;
import com.acooly.module.ocr.result.BankcardOcrResult;
import com.acooly.module.ocr.result.BusinessLicenseOcrResult;
import com.acooly.module.ocr.result.DriverLicenseOcrResult;
import com.acooly.module.ocr.result.IdCardOcrResult;
import com.acooly.module.ocr.result.VehicleLicenseOcrResult;
import com.acooly.module.ocr.service.OcrService;
import com.baidu.aip.util.Util;

import lombok.extern.slf4j.Slf4j;

@Profile("!online")
@Slf4j
@Controller
@RequestMapping(value = "/test/baidu/orc/")
public class TestBaiduOrcControl {

	@Autowired
	private OcrService ocrService;

	@ResponseBody
	@RequestMapping("idCardOcr")
	public Object idCardOcr(HttpServletRequest request, HttpServletResponse response) {
		try {
//			String imageFile = "C:\\\\Users\\\\ThinkPad\\\\Desktop\\\\测试图片\\\\a35f2184c298a17c09363f50299177e.png";
//			String imageFile = "C:\\\\Users\\\\ThinkPad\\\\Desktop\\\\测试图片\\\\a4037c14368202f87a2258b28dfe709.png";
			
			String imageFile = "C:\\\\Users\\\\ThinkPad\\\\Desktop\\\\测试图片\\\\202003191142508386621.jpg";
			
			byte[] image = Util.readFileByBytes(imageFile);
			IdCardOcrOrder order = new IdCardOcrOrder();
			order.setImage(image);
			order.setIdCardSide(IdCardSideEnum.front);
			IdCardOcrResult result = ocrService.idCardOcr(order);
			return result;
		} catch (Exception e) {
			log.error("idCardOcr失败:{}", e);
		}
		return "识别失败";
	}

	@ResponseBody
	@RequestMapping("driverLicenseOcr")
	public Object driverLicenseOcr(HttpServletRequest request, HttpServletResponse response) {
		try {
//			String imageFile = "C:\\\\Users\\\\ThinkPad\\\\Desktop\\\\测试图片\\\\驾驶证1.jpg";
			String imageFile = "C:\\\\Users\\\\ThinkPad\\\\Desktop\\\\测试图片\\\\驾驶证2.jpg";
			byte[] image = Util.readFileByBytes(imageFile);
			DriverLicenseOcrOrder order = new DriverLicenseOcrOrder();
			order.setImage(image);
			DriverLicenseOcrResult result = ocrService.driverLicenseOcr(order);
			return result;
		} catch (Exception e) {
			log.error("driverLicenseOcr失败:{}", e);
		}
		return "识别失败";
	}

	@ResponseBody
	@RequestMapping("businessLicenseOcr")
	public Object businessLicenseOcr(HttpServletRequest request, HttpServletResponse response) {
		try {
//			String imageFile = "C:\\\\Users\\\\ThinkPad\\\\Desktop\\\\测试图片\\\\91500000MA601XHCXD1.jpg";
			String imageFile = "C:\\\\Users\\\\ThinkPad\\\\Desktop\\\\测试图片\\\\冲天炮.jpg";
			
			byte[] image = Util.readFileByBytes(imageFile);
			BusinessLicenseOcrOrder order = new BusinessLicenseOcrOrder();
			order.setImage(image);
			BusinessLicenseOcrResult result = ocrService.businessLicenseOcr(order);
			return result;
		} catch (Exception e) {
			log.error("businessLicenseOcr失败:{}", e);
		}
		return "识别失败";
	}

	@ResponseBody
	@RequestMapping("bankcardOcr")
	public Object bankcardOcr(HttpServletRequest request, HttpServletResponse response) {
		try {
			String imageFile = "C:\\\\Users\\\\ThinkPad\\\\Desktop\\\\测试图片\\\\jianshe-bank.jfif";
//			String imageFile = "C:\\\\Users\\\\ThinkPad\\\\Desktop\\\\测试图片\\\\abc-bank.jfif";
			byte[] image = Util.readFileByBytes(imageFile);
			BankcardOcrOrder order = new BankcardOcrOrder();
			order.setImage(image);
			BankcardOcrResult result = ocrService.bankcardOcr(order);
			return result;
		} catch (Exception e) {
			log.error("bankcardOcr失败:{}", e);
		}
		return "识别失败";
	}

	@ResponseBody
	@RequestMapping("vehicleLicenseOcr")
	public Object vehicleLicenseOcr(HttpServletRequest request, HttpServletResponse response) {
		try {
//			String imageFile = "C:\\\\Users\\\\ThinkPad\\\\Desktop\\\\测试图片\\\\行驶证1.jpg";
//			String imageFile = "C:\\\\Users\\\\ThinkPad\\\\Desktop\\\\测试图片\\\\行驶证2.jpeg";
			String imageFile = "C:\\\\Users\\\\ThinkPad\\\\Desktop\\\\测试图片\\\\行驶证3.jpeg";
			byte[] image = Util.readFileByBytes(imageFile);
			VehicleLicenseOcrOrder order = new VehicleLicenseOcrOrder();
			order.setImage(image);
			order.setVehicleLicenseSide(VehicleLicenseSideEnum.back);
			VehicleLicenseOcrResult result = ocrService.vehicleLicenseOcr(order);
			return result;
		} catch (Exception e) {
			log.error("vehicleLicenseOcr失败:{}", e);
		}
		return "识别失败";
	}

}
