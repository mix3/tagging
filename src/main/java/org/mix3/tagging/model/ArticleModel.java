package org.mix3.tagging.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.mix3.tagging.entity.Article;
import org.mix3.tagging.entity.Tag;

public class ArticleModel implements Serializable{
	private Integer id;
	private String title;
	private String excerpt;
	private String url;
	private List<TagModel> tagList;
	
	public ArticleModel(){}
	public ArticleModel(Article article){
		this.id = article.getID();
		this.title = article.getTitle();
		this.excerpt = article.getExcerpt();
		this.url = article.getUrl();
		List<TagModel> tagList = new ArrayList<TagModel>();
		if(article.getTags().length > 0){
			for(Tag tag : article.getTags()){
				tagList.add(new TagModel(tag));
			}
		}
		this.tagList = tagList;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getExcerpt() {
		return excerpt;
	}
	public void setExcerpt(String excerpt) {
		this.excerpt = excerpt;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<TagModel> getTagList() {
		return tagList;
	}
	public void setTagList(List<TagModel> tagList) {
		this.tagList = tagList;
	}
}
