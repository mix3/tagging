package org.mix3.tagging.page;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.http.Cookie;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.util.value.ValueMap;
import org.mix3.tagging.auth.MySession;
import org.mix3.tagging.auth.page.ManagePage;
import org.mix3.tagging.model.SettingModel;
import org.mix3.tagging.service.Service;
import org.mix3.tagging.utils.Utils;

import com.google.inject.Inject;

public class SignInPage extends WebPage{
	@Inject
	protected Service service;
	private SettingModel settingModel;
	private Boolean check;
	
	public SignInPage(){
		Cookie[] cookies = ((WebRequest)getRequestCycle().getRequest()).getCookies();
		for(Cookie c : cookies){
			System.out.println("cookie -> "+c.getName()+":"+c.getValue());
			if(c.getName().equals("skip")){
				if(c.getValue().equals("true")){
					((MySession)Session.get()).setUserName(c.getValue());
				}
			}
		}
		
		try {
			settingModel = service.getSetting();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		add(new LoginForm("loginForm"));
	}
	
	class LoginForm extends Form<String>{
		private TextField textField = new TextField("id", new Model(""));
		private PasswordTextField passwordTextField = new PasswordTextField("pass", new Model(""));
		private CheckBox checkBox = new CheckBox("skip", new Model(check));
		
		public LoginForm(String id) {
			super(id);
			add(textField);
			add(passwordTextField.setRequired(false));
			add(checkBox);
			
			add(new Button("submit"){
				@Override
				public void onSubmit() {
					String uid = textField.getDefaultModelObjectAsString();
					String pass = null;
					Boolean skip = (Boolean)checkBox.getDefaultModelObject();
					
					//pass = Utils.digest(passwordTextField.getDefaultModelObjectAsString());
					pass = passwordTextField.getDefaultModelObjectAsString();
					if(settingModel.getUserId().equals(uid) && settingModel.getPassword().equals(pass)){
						((MySession)Session.get()).setUserName(uid);
						getWebRequestCycle().getWebResponse().addCookie(new Cookie("skip", skip.toString()));
						getWebRequestCycle().getWebResponse().addCookie(new Cookie("uid", uid));
						if(!continueToOriginalDestination()){
							setResponsePage(ManagePage.class);
						}
					}else{
						error("認証に失敗しました");
					}
				}
			});
		}
	}

}
