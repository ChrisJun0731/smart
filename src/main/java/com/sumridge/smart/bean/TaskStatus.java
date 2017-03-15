package com.sumridge.smart.bean;

public enum TaskStatus {
	ToDo("todo"),
	Processing("processing"),
	Done("done"),
	Cancel("cancel");
	private String value;
	
	public String getValue(){
		return value;
	}
	
	private TaskStatus(String value){
		this.value = value;
	}
}
