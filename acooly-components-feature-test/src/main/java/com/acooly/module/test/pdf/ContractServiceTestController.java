package com.acooly.module.test.pdf;

import com.acooly.core.utils.Money;
import com.acooly.module.pdf.PdfGeneratorService;
import com.acooly.module.test.pdf.vo.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * @author xiyang@acooly.cn
 * @date 2018-10-19 11:23
 */
@Slf4j
@Controller
@RequestMapping(value = "/contract/test")
public class ContractServiceTestController {

    @Autowired
    private PdfGeneratorService pdfGeneratorService;

    /**
     * 房屋买卖运营共识规则
     */
    @ResponseBody
    @RequestMapping("/generate")
    public String generate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        File file = new File("/Users/lscoder/Downloads/p-yygsgz.pdf");
        AssetManagementRulesVo vo = new AssetManagementRulesVo();
        vo.setContractNo("JD-LJWPYY20181022001");

        List<String> pageInfos = Lists.newArrayList();
        pageInfos.add("1");
        pageInfos.add("2");
        pageInfos.add("3");
        pageInfos.add("4");
        pageInfos.add("5");
        pageInfos.add("6");
        pageInfos.add("7");

        vo.setPageInfos(pageInfos);
        List<Saler> list = Lists.newArrayList();
        list.add(new Saler("gz0001"));
        list.add(new Saler("gz0002"));
        list.add(new Saler("gz0003"));
        list.add(new Saler("gz0004"));
        vo.setSalers(list);
        vo.setHoldingPersonId("LJXJ1230092344dd2123");
        vo.setHoldingPerson("枭洪");
        vo.setLegalPerson("小小洪");
        vo.setLegalPersonAddress("重庆市渝北区鸳鸯街道美利山公园城市189-1-1");
        vo.setOperationCompany("重庆市冲天炮科技有限公司");
        vo.setHoldCity("重庆市");

        AssetShop assetShop = new AssetShop();
        assetShop.setCertAddress("重庆市渝北区两江星界3栋11-8");
        assetShop.setCertId("LJXJ1230092344dd2123");
        vo.setAssetShop(assetShop);

        pdfGeneratorService.generate("assetManagementRules.docx", vo, file);
        return "success";
    }

    /**
     * 房屋买卖四方合同
     */
    @ResponseBody
    @RequestMapping("/purchase")
    public String purchasePdf(HttpServletRequest request, HttpServletResponse response) throws IOException {
        File file = new File("/Users/lscoder/Downloads/purchase.pdf");
        AssetSalesContractVo vo = new AssetSalesContractVo();
        vo.setContractNo("JD-LJWPFW20181022001");
        vo.setSalerId("CSR20019DWEEEE02");

        List<Purchaser> list = Lists.newArrayList();
        list.add(new Purchaser("P100001", 5L, 50000L));
        list.add(new Purchaser("P100002", 10L, 120000L));
        list.add(new Purchaser("P100003", 20L, 200000L));
        list.add(new Purchaser("P100004", 70L, 1000000L));
        vo.setPurchasers(list);

        Long totalHoldSumShares = 0L;
        Long totalHoldSumAmount = 0L;
        for (Purchaser p : list) {
            totalHoldSumShares += p.getHoldShares();
            totalHoldSumAmount += p.getHoldSumAmount();
        }
        vo.setTotalHoldSumShares(totalHoldSumShares.toString());
        vo.setTotalHoldSumAmount(Money.yuan(totalHoldSumAmount).toString());
        vo.setHoldingPersonId("10001");
        vo.setHoldingPerson("枭洪");
        vo.setLegalPerson("小小洪");
        vo.setLegalPersonAddress("重庆市渝北区鸳鸯街道美利山公园城市189-1-1");
        vo.setOperationCompany("重庆市冲天炮科技有限公司");
        vo.setSaleAmount("1000000");
        vo.setSaleAmountCH("壹佰万");
        vo.setShares("100");
        vo.setShareAmount("10000");
        vo.setHouseAssignPeriod("30");

        AssetShop assetShop = new AssetShop();
        assetShop.setCertAddress("重庆市渝北区两江星界3栋11-8");
        assetShop.setArea("120.5");
        assetShop.setCertPurpose("公司办公");
        assetShop.setCertId("LJXJ1230092344dd2123");
        assetShop.setCertOwnerId("LJXJSYQ1230092344dd2123");
        assetShop.setCertLandId("LJXJTD1230092344dd2123");
        assetShop.setRent("Y");
        assetShop.setCertDesc("房屋很靠谱，质量很好，产权70年");
        assetShop.setDecorate("Y");
        assetShop.setLandGetMethod("LAND_GRANT");
        assetShop.setHouseOwnMethod("BUY");
        vo.setAssetShop(assetShop);

        AssetRent assetRent = new AssetRent();
        assetRent.setRenter("小花");
        assetRent.setBeginTime("2018-10-01");
        assetRent.setEndTime("2020-10-01");
        assetRent.setRentAmount("10000");
        assetRent.setRentAmountCH("壹万");
        assetRent.setRentCycle("MONTH");
        assetRent.setPaidAmount("1200");
        assetRent.setPaidAmountCH("壹仟贰佰");
        vo.setAssetRent(assetRent);

        pdfGeneratorService.generate("assetSalesContract.docx", vo, file);
        return "ok";
    }

    /**
     * 房产份额持有确认函
     */
    @ResponseBody
    @RequestMapping("/holding")
    public String holding(HttpServletRequest request, HttpServletResponse response) throws IOException {
        File file = new File("C:\\Users\\xiaohong\\p-holding.pdf");
        AssetHoldingShareVo vo = new AssetHoldingShareVo();
        vo.setHolderId("HD0001");
        vo.setHoldShares("10");
        vo.setHoldRate("30"); //%
        vo.setHoldingPersonId("10001");
        vo.setHoldingPerson("枭洪");
        vo.setLegalPerson("小小洪");
        vo.setLegalPersonAddress("重庆市渝北区鸳鸯街道美利山公园城市189-1-1");
        vo.setHoldDate("2018-09-23");

        pdfGeneratorService.generate("assetHoldingShare.docx", vo, file);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/trade")
    public String trade(HttpServletRequest request, HttpServletResponse response) throws IOException {
        File file = new File("C:\\Users\\xiaohong\\p-trade.pdf");
        AtomTradeVo vo = new AtomTradeVo();
        vo.setContractNo("JD-LJWPFW20181022001");
        vo.setHolderId("H000001");
        vo.setPurchaserId("P000001");
        vo.setHoldingPersonId("10001");
        vo.setHoldingPerson("枭洪");
        vo.setLegalPerson("小小洪");
        vo.setLegalPersonAddress("重庆市渝北区鸳鸯街道美利山公园城市189-1-1");
        vo.setYear("2018");
        vo.setMonth("10");
        vo.setDay("20");
        vo.setHoldRate("25");//%
        vo.setHoldShares("30");
        vo.setOperationCompany("重庆市冲天炮科技有限公司");
        vo.setManageCompany("重庆市冲天炮科技有限公司");
        vo.setSaleAmount("1000000");
        vo.setSaleAmountCH("壹佰万");
        vo.setTradeAmount("1200000");
        vo.setTradeAmountCH("壹佰贰拾万");

        AssetShop assetShop = new AssetShop();
        assetShop.setCertAddress("重庆市渝北区两江星界3栋11-8");
        assetShop.setArea("120.5");
        assetShop.setCertPurpose("公司办公");
        assetShop.setCertId("LJXJ1230092344dd2123");
        assetShop.setCertOwnerId("LJXJSYQ1230092344dd2123");
        assetShop.setCertLandId("LJXJTD1230092344dd2123");
        assetShop.setRent("Y");
        assetShop.setCertDesc("房屋很靠谱，质量很好，产权70年");
        assetShop.setDecorate("Y");
        assetShop.setLandGetMethod("LAND_GRANT");
        assetShop.setHouseOwnMethod("BUY");
        vo.setAssetShop(assetShop);

        AssetRent assetRent = new AssetRent();
        assetRent.setRenter("小花");
        assetRent.setBeginTime("2018-10-01");
        assetRent.setEndTime("2020-10-01");
        assetRent.setRentAmount("10000");
        assetRent.setRentAmountCH("壹万");
        assetRent.setRentCycle("MONTH");
        assetRent.setPaidAmount("1200");
        assetRent.setPaidAmountCH("壹仟贰佰");
        vo.setAssetRent(assetRent);

        pdfGeneratorService.generate("atomTrade.docx", vo, file);
        return "ok";
    }


    public static void main(String[] args) throws Exception {
        /** 初始化配置文件 **/
        Configuration configuration = new Configuration();
        String fileDirectory = "/Users/lscoder/coding/acooly/acooly-components-feature/acooly-components-feature-test/src/main/resources/pdf/templates";
        /** 加载文件 **/
        configuration.setDirectoryForTemplateLoading(new File(fileDirectory));
        /** 加载模板 **/
        Template template = configuration.getTemplate("test.ftl");
        File outFile = new File("/Users/lscoder/Downloads/test.doc");
        Writer out = new BufferedWriter((new OutputStreamWriter(new FileOutputStream(outFile))));
        Map map = Maps.newHashMap();
        map.put("code", "1235523452345");
        map.put("name", "2022年 贵州茅台酒 飞天 53%vol 800ml");
        map.put("url", getImageBase("/Users/lscoder/coding/acooly/acooly-components-feature/acooly-components-feature-test/src/main/resources/pdf/templates/test.jpg"));
        template.process(map, out);
    }


    public static String getImageBase(String src) throws Exception {
        if (src == null || src == "") {
            return "";
        }
        File file = new File(src);
        if (!file.exists()) {
            return "";
        }
        InputStream in = null;
        byte[] data = null;
        try {
            in = new FileInputStream(file);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }
}
