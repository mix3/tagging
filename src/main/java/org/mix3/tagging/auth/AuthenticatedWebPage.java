package org.mix3.tagging.auth;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebResponse;

public class AuthenticatedWebPage extends WebPage{
		public AuthenticatedWebPage(){
			
			System.out.println("AuthenticatedWebPage : "+getRequestCycle().getRequest().getURL());
		}
}
