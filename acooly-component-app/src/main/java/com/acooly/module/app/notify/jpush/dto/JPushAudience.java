/**
 * create by zhangpu date:2015年11月4日
 */
package com.acooly.module.app.notify.jpush.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author zhangpu
 * @date 2015年11月4日
 */
public class JPushAudience {

    private List<String> tag;

    @JsonProperty("tag_and")
    private List<String> tagAnd;

    private List<String> alias;

    @JsonProperty("registration_id")
    private List<String> registrationId;

    public void appendAlias(String a) {
        if (alias == null) {
            alias = Lists.newArrayList();
        }
        alias.add(a);
    }

    public List<String> getTag() {
        return tag;
    }

    public void setTag(List<String> tag) {
        this.tag = tag;
    }

    public List<String> getTagAnd() {
        return tagAnd;
    }

    public void setTagAnd(List<String> tagAnd) {
        this.tagAnd = tagAnd;
    }

    public List<String> getAlias() {
        return alias;
    }

    public void setAlias(List<String> alias) {
        this.alias = alias;
    }

    public List<String> getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(List<String> registrationId) {
        this.registrationId = registrationId;
    }
}
