package com.acooly.module.chat.jchat.dto;

import java.util.Map;

import org.hibernate.validator.constraints.NotBlank;

import com.acooly.core.utils.mapper.JsonMapper;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * jchat IM 文本内容body
 * 
 * @author cuifuq
 */
@Setter
@Getter
public class MessageBodyTextDto {

	/**
	 * 消息内容 （必填）
	 */
	@NotBlank
	@JsonProperty("text")
	private String text;

	/**
	 * 选填的json对象 开发者可以自定义extras里面的key value（选填）
	 */
	@JsonProperty("extras")
	private Map<String, Object> extras;

	@Override
	public String toString() {
		return JsonMapper.nonEmptyMapper().toJson(this);
	}
}
