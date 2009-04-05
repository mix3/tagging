package org.mix3.tagging.frame;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

public abstract class Result extends WebPage{
	public Result(){
		add(new Label("message", Message()));
	}
	protected abstract String Message();
}
