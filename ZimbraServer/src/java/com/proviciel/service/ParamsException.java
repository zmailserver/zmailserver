package com.proviciel.service;

import java.util.ArrayList;
import java.util.Iterator;

public class ParamsException extends Exception {

	private static final long serialVersionUID = -4318378215407723259L;
	
	private ArrayList<String> missings;
	
	public ParamsException(String msg){
		super(msg);
	}
	
	public ParamsException(){
		super();
		this.missings = new ArrayList<String>();
	}
	
	public void addMissing(String name){
		missings.add(name);
	}
	
	@Override
	public String getMessage(){
		StringBuilder msg= new StringBuilder();
		msg.append("Bad parameters: "+super.getMessage());
		int size = missings.size();
		if(size == 1) {
			msg.append(missings.get(0));
			msg.append(" is missing");
		} else if(size > 1){
			Iterator<String> iter = missings.iterator();
			while(iter.hasNext()){
				msg.append(iter.next());
				if(iter.hasNext()) msg.append(", ");
			}
			msg.append("are missing");
		}
		return msg.toString();
	}

}
