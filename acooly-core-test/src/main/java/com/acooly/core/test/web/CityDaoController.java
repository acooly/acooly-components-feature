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
import com.acooly.core.test.dao.CityMybatisDao;
import com.acooly.core.test.domain.City;
import com.acooly.core.common.type.DBMap;
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

/**
 * @author liuzh
 * @since 2015-12-19 11:10
 */
@RestController
@RequestMapping("/city")
@Slf4j
public class CityDaoController {

    @Autowired
    private CityMybatisDao cityDao;
    @RequestMapping(value = "/findByName", method = RequestMethod.GET)
    @Transactional
    public Map findByName(String name) {
        Map map = cityDao.findByName(name);
        return map;
    }


    @RequestMapping(value = "/selectById", method = RequestMethod.GET)
    @Transactional
    public City selectById(Long id) {
        City city = cityDao.selectById(id);
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return city;
    }

    @RequestMapping(value = "/selectAllByPage", method = RequestMethod.GET)
    public List<City> selectAllByPage() {
        PageInfo<City> pageInfo = new PageInfo<>();
        pageInfo.setCountOfCurrentPage(2);
        return cityDao.selectAllByPage(pageInfo);
    }

    @RequestMapping(value = "/selectAllByPage1", method = RequestMethod.GET)
    public PageInfo<City> selectAllByPage1() {
        PageInfo<City> pageInfo = new PageInfo<>();
        pageInfo.setCountOfCurrentPage(2);
        return cityDao.selectAllByPage1(pageInfo);
    }

    @RequestMapping(value = "/selectAll", method = RequestMethod.GET)
    public List<City> selectAll() {
        return cityDao.selectAll();
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public City create(City city) {
        DBMap<String, String> ext = new DBMap();
        ext.put("key","value");
        ext.put("key1","value1");
        city.setExt(ext);
        cityDao.create(city);
        return city;
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public City get(Long id) {
        cityDao.flush();
        City city = cityDao.get(id);
        log.info("{}", city);
        return city;
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public List<City> getAll() {
        return cityDao.getAll();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public City update(City city) {
        cityDao.update(city);
        return cityDao.get(city.getId());
    }

    @RequestMapping(value = "/remove", method = RequestMethod.GET)
    public void remove(City city) {
        cityDao.remove(city);
    }

    @RequestMapping(value = "/removeById", method = RequestMethod.GET)
    public void removeById(Long id) {
        cityDao.removeById(id);
    }

    @RequestMapping(value = "/removes", method = RequestMethod.GET)
    public void removes(Long... ids) {
        cityDao.removes(ids);
    }

    @RequestMapping(value = "/saves", method = RequestMethod.GET)
    public List<City> saves() {
        List<City> list = Lists.newArrayList();
        City city = new City();
        city.setName("new");
        city.setState("dfd");
        city.setId(4l);
        list.add(city);
        City city1 = new City();
        city1.setName("new");
        city1.setState("dfd2");
        city1.setId(7l);
        list.add(city1);
        cityDao.saves(list);

        return list;
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public List<City> find(String p, String v) {
        return cityDao.find(p, v);
    }

    @RequestMapping(value = "/findUniqu", method = RequestMethod.GET)
    public City findUniqu(String p, String v) {
        return cityDao.findUniqu(p, v);
    }

    @RequestMapping(value = "/find1", method = RequestMethod.GET)
    public List<City> find1(String p) {
        int[] ids = {1, 2, 3};
        return cityDao.find("IN_id", ids);
    }

    @RequestMapping(value = "/find2", method = RequestMethod.GET)
    public List<City> find2(String p) {
        List<String> list = Lists.newArrayList("1", "2", "3");
        return cityDao.find("IN_id", list);
    }

    @RequestMapping(value = "/list1", method = RequestMethod.GET)
    public List<City> list1() {
        java.util.Map<String, Object> map = Maps.newHashMap();
        map.put("EQ_name", "dfd2");
        map.put("EQ_state", "dfd1");
        return cityDao.list(map, null);
    }

    @RequestMapping(value = "/list2", method = RequestMethod.GET)
    public List<City> list2() {
        java.util.Map<String, Object> map = Maps.newHashMap();
        map.put("EQ_name", "dfd2");
        map.put("EQ_state", "dfd1");
        Map<String, Boolean> sortMap = Maps.newHashMap();
        sortMap.put("name", true);
        return cityDao.list(map, sortMap);
    }

    @RequestMapping(value = "/list3", method = RequestMethod.GET)
    public List<City> list3() {
        java.util.Map<String, Object> map = Maps.newHashMap();
        map.put("EQ_name", "dfd2");
        //		map.put("EQ_state", "dfd1");
        Map<String, Boolean> sortMap = Maps.newHashMap();
        sortMap.put("name", true);
        sortMap.put("state", false);
        return cityDao.list(map, sortMap);
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public PageInfo<City> query() {
        PageInfo pageInfo = new PageInfo();
        java.util.Map<String, Object> map = Maps.newHashMap();
        map.put("EQ_name", "dfd2");
        //		map.put("EQ_state", "dfd1");
        Map<String, Boolean> sortMap = Maps.newHashMap();
        sortMap.put("name", true);
        sortMap.put("state", false);
        return cityDao.query(pageInfo, map, sortMap);
    }
}
