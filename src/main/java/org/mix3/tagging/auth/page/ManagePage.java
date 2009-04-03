package org.mix3.tagging.auth.page;

import org.apache.wicket.markup.html.link.Link;
import org.mix3.tagging.auth.AuthenticatedWebPage;
import org.mix3.tagging.auth.MySession;
import org.mix3.tagging.page.SignInPage;

public class ManagePage extends AuthenticatedWebPage{
	public ManagePage(){
		add(new Link("logout"){
			@Override
			public void onClick() {
				((MySession)getSession()).setUserName(null);
				setResponsePage(SignInPage.class);
			}
		});
	}
}
