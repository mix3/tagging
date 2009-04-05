package org.mix3.tagging.model;

import java.io.Serializable;

import org.mix3.tagging.entity.Setting;

@SuppressWarnings("serial")
public class SettingModel implements Serializable{
	private String url = "";
	
	public SettingModel(Setting setting){
		this.url = setting.getUrl();
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
