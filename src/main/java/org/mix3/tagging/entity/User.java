package org.mix3.tagging.entity;

import java.sql.Types;

import net.java.ao.Entity;
import net.java.ao.ManyToMany;
import net.java.ao.schema.SQLType;
import net.java.ao.schema.Unique;

public interface User extends Entity{
	@Unique
	@SQLType(Types.CLOB)
	public String getUserId();
	public void setUserId(String userid);
	
	@SQLType(Types.CLOB)
	public String getPassword();
	public void setPassword(String password);
	
	@Unique
	@SQLType(Types.CLOB)
	public String getToken();
	public void setToken(String token);
	
	@ManyToMany(UserToPost.class)
	public Post[] getPosts();
}
