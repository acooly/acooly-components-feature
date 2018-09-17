package com.acooly.module.chat.jchat.analysis;

import java.util.Iterator;
import java.util.List;

import com.acooly.core.utils.mapper.JsonMapper;
import com.acooly.module.chat.jchat.dto.BatchUsersStatusDto;
import com.acooly.module.chat.jchat.dto.UsersStatusDto;
import com.google.common.collect.Lists;

/**
 * im 解析器
 * 
 * @author ThinkPad
 *
 */
public class JChatAnalysis {

	/**
	 * 解析 在线的用户
	 * @param stringJson
	 * @return
	 */
	public static List<String> userOnline(String stringJson) {
		List<String> userOnline = Lists.newArrayList();
		JsonMapper jsonMapper = JsonMapper.nonEmptyMapper();
		String body = "{\"usersStatusList\":" + stringJson + "}";
		BatchUsersStatusDto buso = jsonMapper.fromJson(body, BatchUsersStatusDto.class);
		List<UsersStatusDto> usersStatusList = buso.getUsersStatusList();
		for (UsersStatusDto usersStatusDto : usersStatusList) {
			int size = usersStatusDto.getDevices().size();
			if (size > 0) {
				if (usersStatusDto.getDevices().get(0).isLogin())
					userOnline.add(usersStatusDto.getUserName());
			}
		}
		return userOnline;
	}
	
	
	public static void main(String[] args) {
		String body = "[{\"devices\":[],\"username\":\"kefu002\"},{\"devices\":[{\"login\":false,\"online\":false,\"platform\":\"j\"}],\"username\":\"liaotian001\"},{\"devices\":[{\"login\":true,\"online\":true,\"platform\":\"j\"}],\"username\":\"wlf001\"},{\"devices\":[{\"login\":false,\"online\":false,\"platform\":\"j\"}],\"username\":\"kefu001\"}]";
		JChatAnalysis j=new JChatAnalysis();
		List<String> userName = j.userOnline(body);
		for (String s : userName) {
			System.out.println(s);
		}
		
	}
	
}
