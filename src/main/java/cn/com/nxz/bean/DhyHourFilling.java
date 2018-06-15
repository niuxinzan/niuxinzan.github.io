package cn.com.nxz.bean;

import java.io.Serializable;

public class DhyHourFilling implements Serializable{
	private static final long serialVersionUID = 1L;
	private String date;
	private String pattern;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getPattern() {
		return pattern;
	}
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	
}
