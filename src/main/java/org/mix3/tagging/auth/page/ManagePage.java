package org.mix3.tagging.auth.page;

import org.apache.wicket.authorization.strategies.role.Roles;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.authorization.strategies.role.metadata.MetaDataRoleAuthorizationStrategy;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

@AuthorizeInstantiation({"USER", "ADMIN"})
public class ManagePage extends WebPage{
	private Label adminLabel = new Label("admin", "admin only");
	
	public ManagePage(){
		add(adminLabel);
		MetaDataRoleAuthorizationStrategy.authorize(adminLabel, RENDER, Roles.ADMIN);
	}
}
