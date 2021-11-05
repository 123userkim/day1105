package com.sist.goods03;

public class GoodsVo {
	private int no;
	private String item;
	private int qty;
	private int price;
	public GoodsVo(int no, String item, int qty, int price) {
		super();
		this.no = no;
		this.item = item;
		this.qty = qty;
		this.price = price;
	}
	public GoodsVo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
}
