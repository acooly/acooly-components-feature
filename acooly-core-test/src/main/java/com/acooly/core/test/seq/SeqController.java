package com.acooly.core.test.seq;

import com.acooly.module.seq.SeqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qiubo@yiji.com
 */
@RestController
@RequestMapping(value = "/seq")
public class SeqController {

    @Autowired(required = false)
    private SeqService seqService;

    @RequestMapping("next")
    public Long next(String name) {
        return seqService.nextId(name);
    }
}
