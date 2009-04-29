package org.mix3.tagging.service;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class Module extends AbstractModule{
	protected void configure() {
		bind(Service.class)
		.to(ServiceImpl.class)
		.in(Scopes.SINGLETON);
	}
}
