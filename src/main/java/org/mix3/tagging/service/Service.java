package org.mix3.tagging.service;

import java.sql.SQLException;
import java.util.List;

import org.mix3.tagging.entity.Article;
import org.mix3.tagging.model.ArticleModel;
import org.mix3.tagging.model.SettingModel;
import org.mix3.tagging.model.TagModel;

import com.google.inject.ImplementedBy;

@ImplementedBy(ServiceImpl.class)
public interface Service {
	public void createArticle(String title, String excerpt, String url, String tag) throws SQLException;
	public List<TagModel> findArticleToTag(String url) throws SQLException;
	public List<ArticleModel> findTagToArticle(int tag) throws SQLException;
	public SettingModel getSettings() throws SQLException;
	public List<ArticleModel> searchArticle(String url) throws SQLException;
}
