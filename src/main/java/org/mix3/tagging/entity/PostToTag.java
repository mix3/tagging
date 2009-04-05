package org.mix3.tagging.entity;

import net.java.ao.Entity;

public interface PostToTag extends Entity{
	public Post getPost();
	public void setPost(Post post);
	
	public Tag getTag();
	public void setTag(Tag tag);
}
