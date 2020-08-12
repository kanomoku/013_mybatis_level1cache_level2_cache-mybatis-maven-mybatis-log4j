package com.pojo;

public class LogInfo {
	private int id;
	private String accOut;
	private String accIn;
	private double money;
	public final int getId() {
		return id;
	}
	public final void setId(int id) {
		this.id = id;
	}
	public final String getAccOut() {
		return accOut;
	}
	@Override
	public String toString() {
		return "LogInfo [id=" + id + ", accOut=" + accOut + ", accIn=" + accIn + ", money=" + money + "]";
	}
	public final void setAccOut(String accOut) {
		this.accOut = accOut;
	}
	public final String getAccIn() {
		return accIn;
	}
	public final void setAccIn(String accIn) {
		this.accIn = accIn;
	}
	public final double getMoney() {
		return money;
	}
	public final void setMoney(double money) {
		this.money = money;
	}

}
