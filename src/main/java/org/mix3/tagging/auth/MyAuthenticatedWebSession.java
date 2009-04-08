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
		System.out.println("check : "+userid+" : "+password);
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
			System.out.println("role check");
			if (service.getAdmin().equals(this.userName)) {
				System.out.println("admin : "+service.getAdmin()+" = "+this.userName);
				return new Roles(Roles.ADMIN);
			}
			System.out.println("guest : "+service.getAdmin()+" = "+this.userName);
			return new Roles(Roles.USER);
		}
		return null;
	}
	
	@Override
	public void signOut() {
		super.signOut();
		System.out.println("signout");
		this.userName = null;
	}
}
