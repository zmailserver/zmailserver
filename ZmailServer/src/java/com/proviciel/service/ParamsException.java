/*
    Copyright © 2013 MLstate

    Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

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
