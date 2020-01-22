package com.example.demo.model;

public class Code {
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Code(String code) {
		super();
		this.code = code;
	}
	public Code() {}

	public boolean checkCardCode(Card card) {
		if (this.getCode().equals(card.getCode()))
			return true;
		return false;
		
	}
	

}
