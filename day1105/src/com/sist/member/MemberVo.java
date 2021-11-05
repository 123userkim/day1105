package com.sist.member;

public class MemberVo {
	private int no;
	private String name;
	private String addr;
	private int age;
	private String phone;
	public MemberVo(int no, String name, String addr, int age, String phone) {
		super();
		this.no = no;
		this.name = name;
		this.addr = addr;
		this.age = age;
		this.phone = phone;
	}
	public MemberVo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
