package com.yc.util;

/**
 * 消费条目
 * @author 沈俊羽
 *
 */
public class Integral {
 
	private String rid;//房间号
	private String days;//入住天数
	private String type;//房间类型
	private String price;//房价  元/天
	private String deposit;//押金
	private String other;//其他消费
	
	public Integral(){
		
	}
	public Integral(String goodsName,String count,String jifen,String cash,String deposit,String other){
		this.rid = goodsName;
		this.days = count;
		this.type = jifen;
		this.price = cash;
		this.deposit=deposit;
		this.other=other;
	}
 
	public String getRid() {
		return rid;
	}
 
	public void setRid(String goods_name) {
		this.rid = goods_name;
	}
 
	public String getDays() {
		return days;
	}
 
	public void setDays(String count) {
		this.days = count;
	}
 
	public String getType() {
		return type;
	}
 
	public void setType(String jifen) {
		this.type = jifen;
	}
 
	public String getPrice() {
		return price;
	}
 
	public void setPrice(String cash) {
		this.price = cash;
	}
	
	public String getDeposit() {
		return deposit;
	}
	
	public void setDeposit(String deposit) {
		this.deposit = deposit;
	}
	
	public String getOther() {
		return other;
	}
	
	public void setOther(String other) {
		this.other = other;
	}
	
	
 
}
