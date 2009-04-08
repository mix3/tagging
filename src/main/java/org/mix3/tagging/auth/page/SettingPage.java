package org.mix3.tagging.auth.page;

import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.mix3.tagging.model.SettingModel;
import org.mix3.tagging.service.Service;

import com.google.inject.Inject;

@AuthorizeInstantiation("ADMIN")
public class SettingPage extends WebPage{
	@Inject
	protected Service service;
	
	public SettingPage(){
		add(new SettingForm("settingForm"));
	}
	
	public class SettingForm extends Form{
		public SettingForm(String id) {
			super(id);
			this.setDefaultModel(new CompoundPropertyModel<SettingModel>(service.getSetting()));
			add(new TextField<String>("url"));
		}
		
		@Override
		protected void onSubmit() {
			service.setSetting((SettingModel)getDefaultModelObject());
			setResponsePage(SettingPage.class);
		}
	}
}
