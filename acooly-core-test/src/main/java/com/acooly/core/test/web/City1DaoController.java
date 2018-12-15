/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.acooly.core.test.web;

import com.acooly.core.common.dao.support.PageInfo;
import com.acooly.core.test.dao.City1MybatisDao;
import com.acooly.core.test.domain.City1;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author liuzh
 * @since 2015-12-19 11:10
 */
@RestController
@RequestMapping("/city1")
@Slf4j
public class City1DaoController {

    @Autowired
    private City1MybatisDao cityDao;

    @RequestMapping(value = "/selectById", method = RequestMethod.GET)
    @Transactional
    public City1 selectById(String id) {
        City1 City1 = cityDao.selectById(id);
        return City1;
    }

    @RequestMapping(value = "/selectByIn", method = RequestMethod.GET)
    @Transactional
    public List<City1> selectByIn() {
        return cityDao.selectByIn(Lists.newArrayList("1", "2"));
    }

    @RequestMapping(value = "/selectByIn1", method = RequestMethod.GET)
    @Transactional
    public List<City1> selectByIn1() {
        return cityDao.selectByIn1(Lists.newArrayList("1", "2"));
    }

    @RequestMapping(value = "/selectByIn2", method = RequestMethod.GET)
    @Transactional
    public List<City1> selectByIn2() {
        return cityDao.selectByIn2("1", "2");
    }

    @RequestMapping(value = "/selectAllByPage", method = RequestMethod.GET)
    public List<City1> selectAllByPage() {
        PageInfo<City1> pageInfo = new PageInfo<>();
        pageInfo.setCountOfCurrentPage(2);
        return cityDao.selectAllByPage(pageInfo);
    }

    @RequestMapping(value = "/selectAllByPage1", method = RequestMethod.GET)
    public PageInfo<City1> selectAllByPage1() {
        PageInfo<City1> pageInfo = new PageInfo<>();
        pageInfo.setCountOfCurrentPage(2);
        return cityDao.selectAllByPage1(pageInfo);
    }

    @RequestMapping(value = "/selectAll", method = RequestMethod.GET)
    public List<City1> selectAll() {
        return cityDao.selectAll();
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public City1 create() {
        City1 city1 = new City1();
        city1.setId(UUID.randomUUID().toString());
        city1.setName("a");
        city1.setState("b");
        cityDao.create(city1);
        return city1;
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public City1 get(String id) {
        cityDao.flush();
        City1 City1 = cityDao.get(id);
        log.info("{}", City1);
        return City1;
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public List<City1> getAll() {
        return cityDao.getAll();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public City1 update(City1 City1) {
        cityDao.update(City1);
        return cityDao.get(City1.getId());
    }

    @RequestMapping(value = "/remove", method = RequestMethod.GET)
    public void remove(City1 City1) {
        cityDao.remove(City1);
    }

    @RequestMapping(value = "/removeById", method = RequestMethod.GET)
    public void removeById(String id) {
        cityDao.removeById(id);
    }

    @RequestMapping(value = "/removes", method = RequestMethod.GET)
    public void removes(String... ids) {
        cityDao.removes(ids);
    }

    @RequestMapping(value = "/saves", method = RequestMethod.GET)
    public List<City1> saves() {
        List<City1> list = Lists.newArrayList();
        City1 City1 = new City1();
        City1.setName("new");
        City1.setState("dfd");
        City1.setId("1");
        list.add(City1);
        City1 city1 = new City1();
        city1.setName("new");
        city1.setState("dfd2");
        city1.setId("2");
        list.add(city1);
        cityDao.saves(list);

        return list;
    }

    //http://127.0.0.1:8081/city1/find?p=eq_name&v=a3
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public List<City1> find(String p, String v) {
        return cityDao.find(p, v);
    }

    @RequestMapping(value = "/findUniqu", method = RequestMethod.GET)
    public City1 findUniqu(String p, String v) {
        return cityDao.findUniqu(p, v);
    }

    @RequestMapping(value = "/find1", method = RequestMethod.GET)
    public List<City1> find1(String p) {
        int[] ids = {1, 2, 3};
        return cityDao.find("IN_id", ids);
    }

    @RequestMapping(value = "/find2", method = RequestMethod.GET)
    public List<City1> find2(String p) {
        List<String> list = Lists.newArrayList("1", "2", "3");
        return cityDao.find("IN_id", list);
    }

    @RequestMapping(value = "/list1", method = RequestMethod.GET)
    public List<City1> list1() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("EQ_name", "dfd2");
        map.put("EQ_state", "dfd1");
        return cityDao.list(map, null);
    }

    @RequestMapping(value = "/list2", method = RequestMethod.GET)
    public List<City1> list2() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("EQ_name", "dfd2");
        map.put("EQ_state", "dfd1");
        Map<String, Boolean> sortMap = Maps.newHashMap();
        sortMap.put("name", true);
        return cityDao.list(map, sortMap);
    }

    @RequestMapping(value = "/list3", method = RequestMethod.GET)
    public List<City1> list3() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("EQ_name", "dfd2");
        //		map.put("EQ_state", "dfd1");
        Map<String, Boolean> sortMap = Maps.newHashMap();
        sortMap.put("name", true);
        sortMap.put("state", false);
        return cityDao.list(map, sortMap);
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public PageInfo<City1> query() {
        PageInfo pageInfo = new PageInfo();
        Map<String, Object> map = Maps.newHashMap();
        map.put("EQ_name", "dfd2");
        //		map.put("EQ_state", "dfd1");
        Map<String, Boolean> sortMap = Maps.newHashMap();
        sortMap.put("name", true);
        sortMap.put("state", false);
        return cityDao.query(pageInfo, map, sortMap);
    }
}
