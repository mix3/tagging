package org.mix3.tagging.entity;

import java.sql.Types;

import net.java.ao.Entity;
import net.java.ao.schema.SQLType;

public interface Settings extends Entity{
	@SQLType(Types.CLOB)
	public String getUrl();
	public void setUrl(String url);
}
