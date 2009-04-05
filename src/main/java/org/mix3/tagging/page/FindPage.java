package org.mix3.tagging.page;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.mix3.tagging.service.Service;

import com.google.inject.Inject;

public class FindPage extends WebPage{
//	@Inject
//	protected Service service;
	
	private String url = "";
	
	@SuppressWarnings("serial")
	public FindPage(PageParameters parameters) throws Exception{
//		if(parameters.getString("url") != null){
//			url = URLDecoder.decode(parameters.getString("url"), "UTF-8");
//		}else{
//			throw new RuntimeErrorException(new Error("Parameters Empty Error"), "Parameters Empty Error");
//		}
//		final String serverurl = service.getSetting().getUrl();
////		final String serverurl = "http://192.1568.24.97:8080/";
//		try {
//			add(new ListView<TagModel>("tag", service.findArticleToTag(url)){
//				@Override
//				protected void populateItem(ListItem<TagModel> item) {
//					final TagModel tagModel = item.getModelObject();
//					item.add(new ExternalLink("tagName", serverurl+"archives/"+tagModel.getId()+"/"){
//						@Override
//						protected void onComponentTagBody(
//								MarkupStream markupStream, ComponentTag openTag) {
//							// TODO 自動生成されたメソッド・スタブ
//							replaceComponentTagBody(markupStream, openTag, tagModel.getName());
//						}
//					});
//				}
//			});
//		} catch (SQLException e) {
//			throw new RuntimeErrorException(new Error("SQLException Error"), "SQLException Error");
//		}
	}
}
