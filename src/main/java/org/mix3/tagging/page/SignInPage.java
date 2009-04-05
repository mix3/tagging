package org.mix3.tagging.page;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.mix3.tagging.panel.SignInPanel;

public class SignInPage extends WebPage{
	public SignInPage(final PageParameters parameters){
//		Cookie[] cookies = ((WebRequest)getRequestCycle().getRequest()).getCookies();
//		for(Cookie c : cookies){
//			System.out.println("cookie -> "+c.getName()+":"+c.getValue());
//			if(c.getName().equals("signInPanel.signInForm.username")){
//				System.out.println("insert");
//				((MyAuthenticatedWebSession)Session.get()).authenticate(, password));
//			}
//		}
		add(new SignInPanel("signInPanel"));
	}
}
