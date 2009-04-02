package org.mix3.tagging.error;

import org.apache.wicket.markup.html.WebPage;

public class ErrorPage extends WebPage{
	public ErrorPage(RuntimeException e){
		//System.out.println(e.getCause().getCause().getMessage());
	}
} 