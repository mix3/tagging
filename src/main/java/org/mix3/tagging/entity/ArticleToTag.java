package org.mix3.tagging.entity;

import net.java.ao.Entity;

public interface ArticleToTag extends Entity{
	public Article getArticle();
	public void setArticle(Article article);
	
	public Tag getTag();
	public void setTag(Tag tag);
}
