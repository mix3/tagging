package org.mix3.tagging.model;

import java.io.Serializable;

import org.mix3.tagging.entity.Setting;

public class SettingModel implements Serializable{
	private String url = "";
	private String userid = "";
	private String password = "";
	
	public SettingModel(Setting setting){
		this.url = setting.getUrl();
		this.userid = setting.getUserId();
		this.password = setting.getPassword();
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUserId() {
		return userid;
	}
	public void setUserId(String userid) {
		this.userid = userid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
