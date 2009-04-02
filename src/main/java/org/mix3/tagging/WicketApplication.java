package org.mix3.tagging;

import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.Response;
import org.apache.wicket.guice.GuiceComponentInjector;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.protocol.http.WebResponse;
import org.apache.wicket.request.target.coding.MixedParamUrlCodingStrategy;
import org.apache.wicket.request.target.coding.QueryStringUrlCodingStrategy;
import org.apache.wicket.settings.IExceptionSettings;
import org.mix3.tagging.error.ErrorPage;
import org.mix3.tagging.error.ErrorRequestCycle;
import org.mix3.tagging.page.ArchivesPage;
import org.mix3.tagging.page.FindPage;
import org.mix3.tagging.page.PostPage;

public class WicketApplication extends WebApplication{
	@Override
	public RequestCycle newRequestCycle(Request request, Response response) {
		return new ErrorRequestCycle(this, (WebRequest)request, (WebResponse)response);
	}
	
	public WicketApplication(){}
	
	protected void init() {
		// Wicket Setting
		getMarkupSettings().setStripWicketTags(true);
		
		// Guice Setting
		addComponentInstantiationListener(new GuiceComponentInjector(this));
		
		// Encode Setting
		getRequestCycleSettings().setResponseRequestEncoding("UTF-8");
		getMarkupSettings().setDefaultMarkupEncoding("UTF-8");
		
		// Mount Setting
		mount(new QueryStringUrlCodingStrategy("/post", PostPage.class));
		mount(new QueryStringUrlCodingStrategy("/find", FindPage.class));
		mount(new MixedParamUrlCodingStrategy("/archives", ArchivesPage.class, new String[]{"id"}));
		
		// Error Page Setting
		getApplicationSettings().setInternalErrorPage(ErrorPage.class);
		getExceptionSettings().setUnexpectedExceptionDisplay(IExceptionSettings.SHOW_INTERNAL_ERROR_PAGE);
	}
	
	public Class<HomePage> getHomePage(){
		return HomePage.class;
	}
}
