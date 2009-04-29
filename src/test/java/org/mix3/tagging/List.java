package org.mix3.tagging;

import java.sql.SQLException;

import org.mix3.tagging.service.Module;
import org.mix3.tagging.service.Service;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class List {
	@Inject
	private Service service;
	
	public static void main(String[] args){
		Injector injector = Guice.createInjector(new Module());
		List test = injector.getInstance(List.class);
		test.test();
	}
	
	public void test(){
		try {
			service.testList();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
}
