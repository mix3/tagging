package org.mix3.tagging.panel;

import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.value.ValueMap;
import org.mix3.tagging.auth.page.ManagePage;

@SuppressWarnings("serial")
public class SignInPanel extends Panel{
	private boolean includeRememberMe = true;
	private PasswordTextField password;
	private boolean rememberMe = true;
	private TextField<String> username;
	
	public final class SignInForm extends Form<Void>{
		private final ValueMap properties = new ValueMap();
		
		public SignInForm(final String id){
			super(id);
			add(username = new TextField<String>("username", new PropertyModel<String>(properties, "username")));
			add(password = new PasswordTextField("password", new PropertyModel<String>(properties, "password")));
			final WebMarkupContainer rememberMeRow = new WebMarkupContainer("rememberMeRow");
			add(rememberMeRow);
			rememberMeRow.add(new CheckBox("rememberMe", new PropertyModel<Boolean>(SignInPanel.this, "rememberMe")));
			setPersistent(rememberMe);
			rememberMeRow.setVisible(includeRememberMe);
		}
		
		public final void onSubmit(){
			if (signIn(getUsername(), getPassword())){
				onSignInSucceeded();
			}else{
				onSignInFailed();
			}
		}
	}
	
	public SignInPanel(final String id){
		this(id, true);
	}
	
	public SignInPanel(final String id, final boolean includeRememberMe){
		super(id);
		this.includeRememberMe = includeRememberMe;
		final FeedbackPanel feedback = new FeedbackPanel("feedback");
		add(feedback);
		add(new SignInForm("signInForm"));
	}
	
	public final void forgetMe(){
		getPage().removePersistedFormData(SignInPanel.SignInForm.class, true);
	}
	
	public String getPassword(){
		return password.getInput();
	}
	
	public boolean getRememberMe(){
		return rememberMe;
	}
	
	public String getUsername(){
		return username.getDefaultModelObjectAsString();
	}
	
	public void setPersistent(final boolean enable){
		username.setPersistent(enable);
	}
	
	public void setRememberMe(final boolean rememberMe){
		this.rememberMe = rememberMe;
		setPersistent(rememberMe);
	}
	
	public boolean signIn(String username, String password){
		return AuthenticatedWebSession.get().signIn(username, password);
	}
	
	protected void onSignInFailed(){
		error(getLocalizer().getString("signInFailed", this, "Sign in failed"));
	}
	
	protected void onSignInSucceeded(){
		if (!continueToOriginalDestination()){
			setResponsePage(ManagePage.class);
		}
	}
}
