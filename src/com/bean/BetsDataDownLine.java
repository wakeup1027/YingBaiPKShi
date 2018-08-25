package com.bean;

import java.util.Date;

public class BetsDataDownLine {
	private String username;
	private double win;
	private double filult;
	private String status;
	private Date starttime;
	private Date overtime;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public double getWin() {
		return win;
	}
	public void setWin(double win) {
		this.win = win;
	}
	public double getFilult() {
		return filult;
	}
	public void setFilult(double filult) {
		this.filult = filult;
	}
	public Date getStarttime() {
		return starttime;
	}
	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}
	public Date getOvertime() {
		return overtime;
	}
	public void setOvertime(Date overtime) {
		this.overtime = overtime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "BetsDataDownLine [username=" + username + ", win=" + win + ", filult=" + filult + ", starttime="
				+ starttime + ", overtime=" + overtime + "]";
	}
	
}
