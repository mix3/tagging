package org.mix3.tagging.entity;

import net.java.ao.Entity;

public interface UserToPost extends Entity{
	public User getUser();
	public void setUser(User user);
	
	public Post getPost();
	public void setPoset(Post post);
}
