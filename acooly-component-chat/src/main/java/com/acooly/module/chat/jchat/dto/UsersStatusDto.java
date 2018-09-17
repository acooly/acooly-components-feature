package com.acooly.module.chat.jchat.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UsersStatusDto {

	@JsonProperty("username")
	private String userName;

	@JsonProperty("devices")
	private List<Devices> devices;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<Devices> getDevices() {
		return devices;
	}

	public void setDevices(List<Devices> devices) {
		this.devices = devices;
	}

	public static class Devices {
		private boolean login;
		private boolean online;
		private String platform;

		public boolean isLogin() {
			return login;
		}

		public void setLogin(boolean login) {
			this.login = login;
		}

		public boolean isOnline() {
			return online;
		}

		public void setOnline(boolean online) {
			this.online = online;
		}

		public String getPlatform() {
			return platform;
		}

		public void setPlatform(String platform) {
			this.platform = platform;
		}

	}

}
