package org.mix3.tagging.entity;

import java.sql.Timestamp;
import java.sql.Types;

import net.java.ao.Entity;
import net.java.ao.ManyToMany;
import net.java.ao.schema.NotNull;
import net.java.ao.schema.SQLType;
import net.java.ao.schema.Unique;

public interface Post extends Entity{
	@SQLType(Types.CLOB)
	public String getTitle();
	public void setTitle(String title);
	
	@SQLType(Types.CLOB)
	public String getExcerpt();
	public void setExcerpt(String excerpt);
	
	@Unique
	@NotNull
	@SQLType(Types.CLOB)
	public String getUrl();
	public void setUrl(String url);
	
	@SQLType(Types.TIMESTAMP)
	public Timestamp getDate();
	public void setDate(Timestamp date);
	
	@ManyToMany(PostToTag.class)
	public Tag[] getTags();
	
	@ManyToMany(UserToPost.class)
	public User[] getUsers();
}
