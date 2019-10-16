package com.yc.util;
/**
 * 房间数据
 * @author 沈俊羽
 *
 */
public class Price {

	private double price=0;//价格
	private String type;//房间类型
	private String date;//时间
	
	public Price() {
	}
	public Price(double price, String type, String date) {
		super();
		this.price = price;
		this.type = type;
		this.date = date;
	}
	
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
}
