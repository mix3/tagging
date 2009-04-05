package org.mix3.tagging;

import org.apache.wicket.Page;
import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.Response;
import org.apache.wicket.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.guice.GuiceComponentInjector;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.protocol.http.WebResponse;
import org.apache.wicket.request.target.coding.QueryStringUrlCodingStrategy;
import org.apache.wicket.settings.IExceptionSettings;
import org.mix3.tagging.auth.MyAuthenticatedWebSession;
import org.mix3.tagging.auth.page.ManagePage;
import org.mix3.tagging.error.ErrorPage;
import org.mix3.tagging.error.ErrorRequestCycle;
import org.mix3.tagging.page.PostPage;
import org.mix3.tagging.page.SignInPage;

public class WicketApplication extends AuthenticatedWebApplication{
	@Override
	protected void init() {
		super.init();
		
		// Guice Setting
		addComponentInstantiationListener(new GuiceComponentInjector(this));
		
		// Wicket Setting
		getMarkupSettings().setStripWicketTags(true);
		
		// Encode Setting
		getRequestCycleSettings().setResponseRequestEncoding("UTF-8");
		getMarkupSettings().setDefaultMarkupEncoding("UTF-8");
		
		// Mount Setting
		mount(new QueryStringUrlCodingStrategy("/post", PostPage.class));
//		mount(new QueryStringUrlCodingStrategy("/find", FindPage.class));
//		mount(new MixedParamUrlCodingStrategy("/archives", ArchivesPage.class, new String[]{"id"}));
		mountBookmarkablePage("/signin", SignInPage.class);
		mountBookmarkablePage("/manage", ManagePage.class);
		
		// Error Page Setting
		getApplicationSettings().setInternalErrorPage(ErrorPage.class);
		getExceptionSettings().setUnexpectedExceptionDisplay(IExceptionSettings.SHOW_INTERNAL_ERROR_PAGE);
	}
	
	public WicketApplication(){}
	
	@Override
	protected Class<? extends WebPage> getSignInPageClass() {
		return SignInPage.class;
	}

	@Override
	protected Class<? extends AuthenticatedWebSession> getWebSessionClass() {
		return MyAuthenticatedWebSession.class;
	}

	@Override
	public Class<? extends Page> getHomePage() {
		return SignInPage.class;
	}
	
	@Override
	public RequestCycle newRequestCycle(Request request, Response response) {
		return new ErrorRequestCycle(this, (WebRequest)request, (WebResponse)response);
	}
}
