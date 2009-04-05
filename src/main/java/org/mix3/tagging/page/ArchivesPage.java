package org.mix3.tagging.page;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.mix3.tagging.service.Service;

import com.google.inject.Inject;

public class ArchivesPage extends WebPage{
	@Inject
	protected Service service;
	
	public ArchivesPage(PageParameters parameters){
////		System.out.println(parameters.getInt("id"));
//		try {
//			List<ArticleModel> articleList = service.findTagToArticle(parameters.getInt("id"));
//			for(ArticleModel a : articleList){
//				System.out.println(a.getUrl());
//			}
//		} catch (StringValueConversionException e) {
//			throw new RuntimeErrorException(new Error("StringValueConversionException Error"), "StringValueConversionException Error");
//		} catch (SQLException e) {
//			throw new RuntimeErrorException(new Error("SQLException Error"), "SQLException Error");
//		}
	}
}
