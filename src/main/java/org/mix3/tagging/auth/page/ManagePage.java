package org.mix3.tagging.auth.page;

import java.sql.SQLException;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;
import org.mix3.tagging.auth.AuthenticatedWebPage;
import org.mix3.tagging.auth.MySession;
import org.mix3.tagging.model.SettingModel;
import org.mix3.tagging.page.SignInPage;
import org.mix3.tagging.service.Service;

import com.google.inject.Inject;

public class ManagePage extends AuthenticatedWebPage{
	@Inject
	protected Service service;
	
	public ManagePage(){
		add(new Link("logout"){
			@Override
			public void onClick() {
				((MySession)getSession()).setUserName(null);
				setResponsePage(SignInPage.class);
			}
		});
		add(new SettingForm("settingForm"));
	}
	
	public class SettingForm extends Form{
		public SettingForm(String id) {
			super(id);
			try {
				setDefaultModel(new CompoundPropertyModel<SettingModel>(service.getSetting()));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			add(new TextField<String>("url"));
			add(new TextField<String>("userid"));
			add(new PasswordTextField("password"));
		}
		
		@Override
		protected void onSubmit() {
			try {
				service.setSetting((SettingModel)getDefaultModelObject());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			setResponsePage(ManagePage.class);
		}
	}
}
