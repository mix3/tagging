package org.mix3.tagging.page;

import java.net.URLDecoder;
import java.sql.SQLException;

import javax.management.RuntimeErrorException;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.mix3.tagging.service.Service;

import com.google.inject.Inject;

public class PostPage extends WebPage{
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
		try {
			service.createArticle(title, excerpt, url, tag);
		} catch (SQLException e) {
			throw new RuntimeErrorException(new Error("SQLException Error"), "SQLException Error");
//			e.printStackTrace();
		}
		add(new Label("message", "Success"));
	}
}
