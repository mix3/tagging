package org.mix3.tagging.model;

import java.io.Serializable;

import org.mix3.tagging.entity.Settings;

public class SettingModel implements Serializable{
	private String url = "";
	
	public SettingModel(Settings settings){
		this.url = settings.getUrl();
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
