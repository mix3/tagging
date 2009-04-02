package org.mix3.tagging.page;

import java.sql.SQLException;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.util.string.StringValueConversionException;
import org.mix3.tagging.model.ArticleModel;
import org.mix3.tagging.service.Service;

import com.google.inject.Inject;

public class ArchivesPage extends WebPage{
	@Inject
	protected Service service;
	
	public ArchivesPage(PageParameters parameters){
//		System.out.println(parameters.getInt("id"));
		try {
			List<ArticleModel> articleList = service.findTagToArticle(parameters.getInt("id"));
			for(ArticleModel a : articleList){
				System.out.println(a.getUrl());
			}
		} catch (StringValueConversionException e) {
			throw new RuntimeErrorException(new Error("StringValueConversionException Error"), "StringValueConversionException Error");
		} catch (SQLException e) {
			throw new RuntimeErrorException(new Error("SQLException Error"), "SQLException Error");
		}
	}
}
