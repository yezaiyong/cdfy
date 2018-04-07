package com.bsco.app.parameter;

import java.io.Serializable;

public class JsonResponse implements Serializable {

	private boolean state = true;
	private String message;
	private Object obj;

	public JsonResponse() {
	}

	public JsonResponse(boolean state) {
		super();
		this.state = state;
	}
	
	public JsonResponse(boolean state, String message) {
		super();
		this.state = state;
		this.message = message;
	}
	
	public JsonResponse(boolean state, String message, Object obj) {
		super();
		this.state = state;
		this.message = message;
		this.obj = obj;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

}
