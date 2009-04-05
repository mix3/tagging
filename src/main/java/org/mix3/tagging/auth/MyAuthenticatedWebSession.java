package org.mix3.tagging.auth;

import java.sql.SQLException;

import org.apache.wicket.Request;
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.authorization.strategies.role.Roles;
import org.mix3.tagging.service.Module;
import org.mix3.tagging.service.Service;
import org.mix3.tagging.service.ServiceImpl;

import com.google.inject.Guice;
import com.google.inject.Injector;

@SuppressWarnings("serial")
public class MyAuthenticatedWebSession extends AuthenticatedWebSession{
	private Service service;
	private String userName;
	
	public MyAuthenticatedWebSession(Request request) {
		super(request);
		Injector injector = Guice.createInjector(new Module());
		service = injector.getInstance(ServiceImpl.class);
	}
	
	@Override
	public boolean authenticate(String userid, String password) {
		if (this.userName == null) {
			try {
				if(service.signIn(userid, password)){
					this.userName = userid;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return (this.userName != null);
	}
	
	@Override
	public Roles getRoles() {
		if (isSignedIn()) {
			if (service.getAdmin().equals(this.userName)) {
				return new Roles(Roles.ADMIN);
			}
			return new Roles(Roles.USER);
		}
		return null;
	}
}
