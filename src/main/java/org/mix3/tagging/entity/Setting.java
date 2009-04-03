package org.mix3.tagging.entity;

import java.sql.Types;

import net.java.ao.Entity;
import net.java.ao.schema.SQLType;

public interface Setting extends Entity{
	@SQLType(Types.CLOB)
	public String getUrl();
	public void setUrl(String url);
	
	@SQLType(Types.CLOB)
	public String getUserId();
	public void setUserId(String userid);
	
	@SQLType(Types.CLOB)
	public String getPassword();
	public void setPassword(String password);
}
