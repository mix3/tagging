package org.mix3.tagging.page;

import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.mix3.tagging.auth.AuthenticatedWebPage;
import org.mix3.tagging.model.TagModel;
import org.mix3.tagging.service.Service;

import com.google.inject.Inject;

public class PostPage extends AuthenticatedWebPage{
	@Inject
	protected Service service;
	
	private String title = "";
	private String excerpt = "";
	private String url = "";
	private String tag = "";
	
	public PostPage(PageParameters parameters) throws Exception{
		System.out.println(parameters.toString());
		if(parameters.getString("title") != null){
			title = URLDecoder.decode(parameters.getString("title"), "UTF-8");
		}
		if(parameters.getString("excerpt") != null){
			excerpt = URLDecoder.decode(parameters.getString("excerpt"), "UTF-8");
		}
		if(parameters.getString("url") != null){
			url = URLDecoder.decode(parameters.getString("url"), "UTF-8");
		}else{
			throw new RuntimeErrorException(new Error("URL Empty Error"), "URL Empty Error");
		}
		if(parameters.getString("tag") != null){
			tag = URLDecoder.decode(parameters.getString("tag"), "UTF-8");
		}
		
		/*
		try {
			service.createArticle(title, excerpt, url, tag);
		} catch (SQLException e) {
			throw new RuntimeErrorException(new Error("SQLException Error"), "SQLException Error");
//			e.printStackTrace();
		}
		add(new Label("message", "Success"));
		*/
		final String serverurl = service.getSetting().getUrl();
//		final String serverurl = "http://192.1568.24.97:8080/";
		try {
			List<TagModel> tagList = service.createArticle(title, excerpt, url, tag);
			add(new ListView<TagModel>("tag", tagList){
				@Override
				protected void populateItem(ListItem<TagModel> item) {
					final TagModel tagModel = item.getModelObject();
					item.add(new ExternalLink("tagName", serverurl+"archives/"+tagModel.getId()+"/"){
						@Override
						protected void onComponentTagBody(
								MarkupStream markupStream, ComponentTag openTag) {
							// TODO 自動生成されたメソッド・スタブ
							replaceComponentTagBody(markupStream, openTag, tagModel.getName());
						}
					});
				}
			});
		} catch (SQLException e) {
			throw new RuntimeErrorException(new Error("SQLException Error"), "SQLException Error");
		}
	}
}
