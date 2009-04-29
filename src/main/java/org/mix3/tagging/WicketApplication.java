package org.mix3.tagging;

import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.guice.GuiceComponentInjector;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.protocol.http.WebResponse;
import org.apache.wicket.request.target.coding.MixedParamUrlCodingStrategy;
import org.apache.wicket.request.target.coding.QueryStringUrlCodingStrategy;
import org.apache.wicket.settings.IExceptionSettings;
import org.mix3.tagging.auth.MyAuthorizationStrategy;
import org.mix3.tagging.auth.MySession;
import org.mix3.tagging.auth.page.ManagePage;
import org.mix3.tagging.error.ErrorPage;
import org.mix3.tagging.error.ErrorRequestCycle;
import org.mix3.tagging.page.ArchivesPage;
import org.mix3.tagging.page.FindPage;
import org.mix3.tagging.page.PostPage;
import org.mix3.tagging.page.SignInPage;
import org.mix3.tagging.service.Service;
import org.mix3.tagging.service.ServiceImpl;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Singleton;

public class WicketApplication extends WebApplication{
	@Override
	public Session newSession(Request request, org.apache.wicket.Response response) {
		return new MySession(request);
	}
	
	@Override
	public RequestCycle newRequestCycle(Request request, Response response) {
		return new ErrorRequestCycle(this, (WebRequest)request, (WebResponse)response);
	}
	
	@Override
	protected void init() {
		// Guice Setting
		addComponentInstantiationListener(new GuiceComponentInjector(this, getModule()));
		
		// Wicket Setting
		getMarkupSettings().setStripWicketTags(true);
		getSecuritySettings().setAuthorizationStrategy(new MyAuthorizationStrategy());
		
		// Encode Setting
		getRequestCycleSettings().setResponseRequestEncoding("UTF-8");
		getMarkupSettings().setDefaultMarkupEncoding("UTF-8");
		
		// Mount Setting
		mount(new QueryStringUrlCodingStrategy("/post", PostPage.class));
		mount(new QueryStringUrlCodingStrategy("/find", FindPage.class));
		mount(new MixedParamUrlCodingStrategy("/archives", ArchivesPage.class, new String[]{"id"}));
		mountBookmarkablePage("/signin", SignInPage.class);
		mountBookmarkablePage("/manage", ManagePage.class);
		
		// Error Page Setting
		getApplicationSettings().setInternalErrorPage(ErrorPage.class);
		getExceptionSettings().setUnexpectedExceptionDisplay(IExceptionSettings.SHOW_INTERNAL_ERROR_PAGE);
		
		// SSL Setting
//		getRequestCycleSettings().setRenderStrategy(Settings.ONE_PASS_RENDER);
	}
	
	public WicketApplication(){}
	
	public Class<FindPage> getHomePage(){
		return FindPage.class;
	}
	
	private Module getModule() {
		return new Module() {
			public void configure(Binder binder) {
				binder.bind(Service.class).to(ServiceImpl.class).in(Singleton.class);
			}
		};
	}
}
