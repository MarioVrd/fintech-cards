package com.example.demo.model;

import com.example.demo.model.enums.CardState;

public class CardInfo {
	private String ownername;
	private String ownersurname;
	private String card_number;
	private String pin;
	private String cvv;
	private boolean online_transaction;
	private int ammount;
	
	public CardInfo() {
		super();
	}
	public CardInfo(String card_number, String pin, String cvv, boolean online_transaction) {
		super();
		this.card_number = card_number;
		this.pin = pin;
		this.cvv = cvv;
		this.online_transaction = online_transaction;
	}
	public String getCard_number() {
		return AES.decrypt(this.card_number, this.ownername + this.ownersurname);
	}
	public void setCard_number(String card_number) {
		this.card_number = card_number;
	}
	public String getPin() {
		return AES.decrypt(this.pin, this.cvv);
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public String getEncryptedCardNum() {
		return this.card_number;
	}
	
	public int getAmmount() {
		return ammount;
	}
	public void setAmmount(int ammount) {
		this.ammount = ammount;
	}
	public String getCvv() {
		return AES.decrypt(this.cvv, this.card_number);
	}
	public void setCvv(String cvv) {
		this.cvv = cvv;
	}
	
	public String getOwnername() {
		return ownername;
	}
	public void setOwnername(String ownername) {
		this.ownername = ownername;
	}
	public String getOwnersurname() {
		return ownersurname;
	}
	public void setOwnersurname(String ownersurname) {
		this.ownersurname = ownersurname;
	}
	public boolean isOnline_transaction() {
		return online_transaction;
	}
	public void setOnline_transaction(boolean online_transaction) {
		this.online_transaction = online_transaction;
	}
	public boolean checkCardValidity(Card card) {
		if (!card.isActive() || card.getStatus() != CardState.ALIVE) {
			return false;}
		if (this.getAmmount() > card.getPayment_limit() || this.isOnline_transaction() && !card.isOnline_payment() || !this.getCard_number().equals(card.getCard_number()) || !this.getCvv().equals(card.getCvv()) || !this.getOwnername().equals(card.getOwner_name()) || !this.getOwnersurname().equals(card.getOwner_surname()) || !this.getPin().equals(card.getPin()))
			return false;
		return true;
		
	}
	
	

}
