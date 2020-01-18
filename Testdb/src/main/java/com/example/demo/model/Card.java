package com.example.demo.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.data.rest.core.annotation.RestResource;

import com.example.demo.model.enums.CardForm;
import com.example.demo.model.enums.CardState;
import com.example.demo.model.enums.CardType;

@Entity
public class Card {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String card_number;
	private boolean active;
	private int owner;
	@Enumerated(EnumType.ORDINAL)
	private CardForm form;
	private String cvv;
	private String pin;
	private String date_of_prod;
	private String expires;
	private int pin_changes;
	@OneToOne
	@JoinColumn(name = "CardRequest_id")
	@RestResource(path = "CardRequestAddress", rel = "request_id")
	private CardRequest request_id;
	@Enumerated(EnumType.ORDINAL)
	private CardType type;
	@Enumerated(EnumType.ORDINAL)
	private CardState status;
	private boolean contactless_payment;
	private boolean online_payment;
	private int payment_limit; // valuta??

	
	@OneToOne(mappedBy = "card", cascade = CascadeType.ALL)
	private Deletion deletion;
	
	@OneToOne(mappedBy = "card", cascade = CascadeType.ALL)
	private StatusChange statuschange;
	
	// baza, kontroleri - ako su kontroleri komplicirani uvesti servis koji handla
	// zahtjeve s kontrolera prema bazi
	// kontroler ne smije baratati s bazom niti repom, već neki međuservis

	Card(){
	}
	

	public Card(String card_number, int owner_id, CardForm form, String cvv, String pin,
			String date_of_prod, String expires, CardRequest request_id, CardType type,
			int payment_limit) {
		super();
		this.card_number = card_number;
		this.active = true;
		this.owner = owner_id;
		this.form = form;
		this.cvv = cvv;
		this.pin = pin;
		this.date_of_prod = date_of_prod;
		this.expires = expires;
		this.pin_changes = 0;
		this.request_id = request_id;
		this.type = type;
		this.status = CardState.ALIVE;
		this.contactless_payment = false;
		this.online_payment = false;
		this.payment_limit = payment_limit;
	}


	public int getId() {
		return id;
	}

	public String getCard_number() {
		return card_number;
	}

	public boolean isActive() {
		return active;
	}

	public int getOwner_id() {
		return owner;
	}

	public CardForm getForm() {
		return form;
	}

	public String getCvv() {
		return cvv;
	}

	public String getPin() {
		return pin;
	}

	public String getDate_of_prod() {
		return date_of_prod;
	}

	public String getExpires() {
		return expires;
	}

	public int getPin_changes() {
		return pin_changes;
	}

	public CardRequest getRequest_id() {
		return request_id;
	}

	public CardType getType() {
		return type;
	}

	public CardState getStatus() {
		return status;
	}

	public boolean isContactless_payment() {
		return contactless_payment;
	}

	public boolean isOnline_payment() {
		return online_payment;
	}

	public int getPayment_limit() {
		return payment_limit;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setCard_number(String card_number) {
		this.card_number = card_number;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setOwner_id(int owner_id) {
		this.owner = owner_id;
	}

	public void setForm(CardForm form) {
		this.form = form;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public void setDate_of_prod(String date_of_prod) {
		this.date_of_prod = date_of_prod;
	}

	public void setExpires(String expires) {
		this.expires = expires;
	}

	public void setPin_changes(int pin_changes) {
		this.pin_changes = pin_changes;
	}

	public void setRequest_id(CardRequest request_id) {
		this.request_id = request_id;
	}

	public void setType(CardType type) {
		this.type = type;
	}

	public void setStatus(CardState status) {
		this.status = status;
	}

	public void setContactless_payment(boolean contactless_payment) {
		this.contactless_payment = contactless_payment;
	}

	public void setOnline_payment(boolean online_payment) {
		this.online_payment = online_payment;
	}

	public void setPayment_limit(int payment_limit) {
		this.payment_limit = payment_limit;
	}

	@Override
	public String toString() {
		return "Card [id=" + id + ", card_number=" + card_number + ", active=" + active + ", owner_id=" + owner
				+ ", form=" + form + ", cvv=" + cvv + ", pin=" + pin + ", date_of_prod=" + date_of_prod + ", expires="
				+ expires + ", pin_changes=" + pin_changes + ", request_id=" + request_id + ", type=" + type
				+ ", status=" + status + ", contactless_payment=" + contactless_payment + ", online_payment="
				+ online_payment + ", payment_limit=" + payment_limit + "]";
	}

}