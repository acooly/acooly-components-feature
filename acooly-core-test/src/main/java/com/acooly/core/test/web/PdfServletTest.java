package com.acooly.core.test.web;

import com.acooly.core.test.vo.PdfDemoVo;
import com.acooly.module.pdf.PDFService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.Map;

/**
 * @author shuijing
 */
@Controller
public class PdfServletTest {

    @Autowired(required = false)
    private PDFService pdfService;

    public static PdfDemoVo getVO() {
        // 模板对象,须继承AbstractDocumentVo
        PdfDemoVo pdfDemoVo = new PdfDemoVo();
        pdfDemoVo.setPolicyNo("0000000000000000000000000");
        pdfDemoVo.setHolderName("张三123abc");
        pdfDemoVo.setInsuredName("李四123abc");
        pdfDemoVo.setBeneficiaryName("测试受益人姓名");
        pdfDemoVo.setBranchName("北京");
        pdfDemoVo.setCompanyName("微软公司");
        pdfDemoVo.setDestination("英国,俄罗斯,冰岛,日内瓦,威尼斯小镇");
        pdfDemoVo.setHolderAdress("重庆市北部新区黄山大道中段9号木星科技大厦一区6楼");
        pdfDemoVo.setHolderPostCode("123456");
        pdfDemoVo.setInsuredBirthday("2013-11-10");
        pdfDemoVo.setInsuredIDNo("123456789012345678");
        pdfDemoVo.setInsuredName("爱新觉罗启星");
        pdfDemoVo.setInsuredPassportNo("测试护照号码123456789");
        pdfDemoVo.setInsuredPhone("13112345678");
        pdfDemoVo.setInsuredPingyinName("abcdefg");
        pdfDemoVo.setInsuredSex("女");
        pdfDemoVo.setIssueDate("2017-04-19");
        pdfDemoVo.setPeriod("十一年");
        pdfDemoVo.setPremium("1009.00");
        pdfDemoVo.setRelation("子女");
        pdfDemoVo.setRemarks("仅为测试");
        pdfDemoVo.setAccidentalSumInsured("150000");
        pdfDemoVo.setEmergencySumInsured("500000");
        pdfDemoVo.setMedicalSumInsured("220000");
        Map<String, String> names = Maps.newHashMap();
        names.put("shuijing", "水镜");
        pdfDemoVo.setNames(names);
        return pdfDemoVo;
    }

    @RequestMapping("/downloadPdf")
    public void demo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Content-disposition", "attachment;filename=" + "demo.pdf");
        response.setContentType("application/pdf");
        OutputStream os = response.getOutputStream();
        PdfDemoVo vo = getVO();
        pdfService.generate("pdftest.html", vo.getDataMap(), os);
    }
}
