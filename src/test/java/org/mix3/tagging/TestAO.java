package org.mix3.tagging;

import org.mix3.tagging.service.Module;
import org.mix3.tagging.service.Service;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class TestAO {
	@Inject
	private Service service;
	
	public static void main(String[] args){
		Injector injector = Guice.createInjector(new Module());
		TestAO test = injector.getInstance(TestAO.class);
		test.test();
	}
	
	private void test(){
		try {
			//service.createPost("","","http://","test1\n\rtest2 test3\ntest4　\rtest5");
			service.createPost("", "","","http://","");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
