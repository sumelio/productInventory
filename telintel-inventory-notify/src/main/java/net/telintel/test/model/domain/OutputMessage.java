package net.telintel.test.model.domain;

import java.util.Date; 

public class OutputMessage extends Message {

	private Date time;
	
	public OutputMessage(Message original, Date time) {
		//super(original.getId(), original.getMessage());
		super(original.getMessage(), original.getId(), original.getCount(), original.getCategory(), original.getProduct());
		
		this.time = time;
	}
	
	public Date getTime() {
		return time;
	}
	
	public void setTime(Date time) {
		this.time = time;
	}
}
