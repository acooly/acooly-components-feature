package com.acooly.core.test.web;

import com.acooly.core.test.vo.DeveloperVo;
import com.acooly.module.pdf.PdfGeneratorService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * @author xiyang@acooly.cn
 * @date 2018-09-26 14:33
 */
@Slf4j
@Controller
@RequestMapping(value = "/pdf")
public class PdfGeneratorTestController {

    @Autowired
    private PdfGeneratorService pdfGeneratorService;

    @RequestMapping("/download")
    public void demo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Content-disposition", "attachment;filename=" + "demo.pdf");
        response.setContentType("application/pdf");

        OutputStream os = response.getOutputStream();

        Map<String, Object> map = Maps.newHashMap();
        map.put("name", "xiyang");


        List<DeveloperVo> developers = Lists.newArrayList();

        for (int i = 0; i < 3; i++) {
            DeveloperVo developer = new DeveloperVo();
            developer.setName("name" + i);
            developer.setLastName("lastName" + i);
            developer.setMail("mail" + i);
            developers.add(developer);
        }
        map.put("developers", developers);

        pdfGeneratorService.generate("test.docx", map, os);
    }
}
