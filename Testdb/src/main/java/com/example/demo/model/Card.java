package com.example.demo.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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
	private String cardNumber;
	private boolean active;
	private Long owner;
	@Enumerated(EnumType.ORDINAL)
	private CardForm form;
	private String owner_name;
	private String owner_surname;
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
	
	@OneToMany(mappedBy = "card", cascade = CascadeType.ALL)
	private Set<StatusChange> statuschange;
	
	// baza, kontroleri - ako su kontroleri komplicirani uvesti servis koji handla
	// zahtjeve s kontrolera prema bazi
	// kontroler ne smije baratati s bazom niti repom, već neki međuservis

	public Card(){
	}
	

	public String getCardNumber() {
                return AES.decrypt(this.cardNumber, this.owner_name + this.owner_surname);
	}


	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}


	public Long getOwner() {
		return owner;
	}


	public void setOwner(Long owner) {
		this.owner = owner;
	}


	public String getOwner_name() {
		return owner_name;
	}


	public void setOwner_name(String owner_name) {
		this.owner_name = owner_name;
	}


	public String getOwner_surname() {
		return owner_surname;
	}


	public void setOwner_surname(String owner_surname) {
		this.owner_surname = owner_surname;
	}


	public Deletion getDeletion() {
		return deletion;
	}


	public void setDeletion(Deletion deletion) {
		this.deletion = deletion;
	}


	
		

	public Set<StatusChange> getStatuschange() {
		return statuschange;
	}


	public void setStatuschange(Set<StatusChange> statuschange) {
		this.statuschange = statuschange;
	}


	public Card(int id, String cardNumber, boolean active, Long owner, CardForm form, String owner_name,
			String owner_surname, String cvv, String pin, String date_of_prod, String expires, int pin_changes,
			CardRequest request_id, CardType type, CardState status, boolean contactless_payment,
			boolean online_payment, int payment_limit, Deletion deletion, Set<StatusChange> statuschange) {
		super();
		this.id = id;
		this.cardNumber = cardNumber;
		this.active = active;
		this.owner = owner;
		this.form = form;
		this.owner_name = owner_name;
		this.owner_surname = owner_surname;
		this.cvv = cvv;
		this.pin = pin;
		this.date_of_prod = date_of_prod;
		this.expires = expires;
		this.pin_changes = pin_changes;
		this.request_id = request_id;
		this.type = type;
		this.status = status;
		this.contactless_payment = contactless_payment;
		this.online_payment = online_payment;
		this.payment_limit = payment_limit;
		this.deletion = deletion;
		this.statuschange = statuschange;
	}


	public Card(String card_number, Long owner, String ownername, String ownersurname, CardForm form, String cvv, String pin,
			String date_of_prod, String expires, CardRequest request_id, CardType type,
			int payment_limit) {
		super();
		this.owner_name = ownername;
		this.owner_surname = ownersurname;
		this.cardNumber = card_number;
		this.active = false;
		this.owner = owner;
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
		return AES.decrypt(this.cardNumber, this.owner_name + this.owner_surname);
	}

	public boolean isActive() {
		return active;
	}

	public Long getOwner_id() {
		return owner;
	}

	public CardForm getForm() {
		return form;
	}

	public String getCvv() {
		return AES.decrypt(this.cvv, this.cardNumber);
	}

	public String getPin() {
		return AES.decrypt(this.pin, this.cvv);
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
		this.cardNumber = card_number;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setOwner_id(Long owner_id) {
		this.owner = owner_id;
	}

	public void setForm(CardForm form) {
		this.form = form;
	}

	public void setCvv(String cvv) {
		this.cvv = AES.encrypt(cvv, this.cardNumber);
	}

	public void setPin(String pin) {
		this.pin = AES.encrypt(pin, this.cvv);
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
		return "Card [id=" + id + ", card_number=" + cardNumber + ", active=" + active + ", owner_id=" + owner
				+ ", form=" + form + ", cvv=" + cvv + ", pin=" + pin + ", date_of_prod=" + date_of_prod + ", expires="
				+ expires + ", pin_changes=" + pin_changes + ", request_id=" + request_id + ", type=" + type
				+ ", status=" + status + ", contactless_payment=" + contactless_payment + ", online_payment="
				+ online_payment + ", payment_limit=" + payment_limit + "]";
	}


	public String getCode() {
		return AES.encrypt(this.getDate_of_prod(), this.getCard_number()).substring(0, 8);
	}

}