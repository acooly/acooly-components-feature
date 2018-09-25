package com.acooly.core.test.defence;

import com.acooly.module.defence.DefenceProperties;
import com.acooly.module.defence.url.SecurityParam;
import com.acooly.module.defence.url.UrlSecurityServiceImpl;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author qiubo@yiji.com
 */
@RestController
@RequestMapping("/url")
@Slf4j
public class UrlController {
    public static void main(String[] args) throws Exception {
        DefenceProperties defenceProperties = new DefenceProperties();
        UrlSecurityServiceImpl urlSecurityService = new UrlSecurityServiceImpl();
        urlSecurityService.setDefenceProperties(defenceProperties);
        urlSecurityService.afterPropertiesSet();
        String encrypt = urlSecurityService.encrypt("1");
        System.out.println(encrypt);
    }

    /**
     * 请求：http://127.0.0.1:8081/url/param?id=58bb0e40e63678e780590e5986cbd804be7b5a11379de9f4f6a6238287bd5779&name=a
     */
    @RequestMapping(value = "/param", method = RequestMethod.GET)
    public String param(@SecurityParam String id, String name) {
        return id + name;
    }

    /**
     * 支持类型转换
     *
     * <p>请求：http://127.0.0.1:8081/url/param1?id=58bb0e40e63678e780590e5986cbd804be7b5a11379de9f4f6a6238287bd5779
     */
    @RequestMapping(value = "/param1", method = RequestMethod.GET)
    public Integer param1(@SecurityParam Integer id) {
        return id;
    }

    /**
     * 支持POJO
     *
     * <p>请求：http://127.0.0.1:8081/url/param2?id=58bb0e40e63678e780590e5986cbd804be7b5a11379de9f4f6a6238287bd5779&name=bohr&age=12
     */
    @RequestMapping(value = "/param2", method = RequestMethod.GET)
    public MyDto param2(@SecurityParam({"id"}) MyDto app) {
        return app;
    }

    /**
     * 支持响应加密
     *
     * <p>请求：http://127.0.0.1:8081/url/param3?id=58bb0e40e63678e780590e5986cbd804be7b5a11379de9f4f6a6238287bd5779&name=bohr&age=12
     * 响应：{"id":"58bb0e40e63678e780590e5986cbd804be7b5a11379de9f4f6a6238287bd5779","name":"bohr","age":1}
     */
    @RequestMapping(value = "/param3", method = RequestMethod.GET)
    public @SecurityParam({"id"}) MyDto param3(@SecurityParam({"id"}) MyDto app) {
        log.info("{}", app);
        app.setAge(1);
        return app;
    }

    @RequestMapping(value = "/param4", method = RequestMethod.GET)
    public @SecurityParam({"id"}) List<MyDto> param4() {
        List<MyDto> dtos = Lists.newArrayList();
        MyDto myDto = new MyDto();
        myDto.setId(1l);
        myDto.setName("bohr");
        myDto.setAge(30);
        MyDto myDto1 = new MyDto();
        myDto1.setId(2l);
        myDto1.setName("na");
        myDto1.setAge(28);
        dtos.add(myDto);
        dtos.add(myDto1);
        return dtos;
    }

    @RequestMapping(value = "/param5", method = RequestMethod.GET)
    public List<MyDto> param5() {
        List<MyDto> dtos = Lists.newArrayList();
        MyDto myDto = new MyDto();
        myDto.setId(1l);
        myDto.setName("bohr");
        myDto.setAge(30);
        MyDto myDto1 = new MyDto();
        myDto1.setId(2l);
        myDto1.setName("na");
        myDto1.setAge(28);
        dtos.add(myDto);
        dtos.add(myDto1);
        return dtos;
    }
}
