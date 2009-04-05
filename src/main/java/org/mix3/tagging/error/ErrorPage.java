package org.mix3.tagging.error;

import org.mix3.tagging.frame.Result;

public class ErrorPage extends Result{
	public ErrorPage(RuntimeException e){
		System.out.println("Error:"+e.getCause().getCause().getMessage());
	}

	@Override
	protected String Message() {
		return "Error";
	}
} 