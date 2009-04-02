package org.mix3.tagging.entity;

import java.sql.Types;

import net.java.ao.Entity;
import net.java.ao.ManyToMany;
import net.java.ao.schema.Default;
import net.java.ao.schema.SQLType;
import net.java.ao.schema.Unique;

public interface Tag extends Entity{
	@Unique
	@SQLType(Types.CLOB)
	public String getName();
	public void setName(String name);
	
	@ManyToMany(ArticleToTag.class)
	public Article[] getArticles();
}
