package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.data.rest.core.annotation.RestResource;

import com.example.demo.model.enums.StatusChangeType;

@Entity
public class StatusChange {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@OneToOne
	@JoinColumn(name = "Card")
	@RestResource(path = "CardAddress", rel = "card")
	private Card card;
	private StatusChangeType type;
	private String date;
	public int getId() {
		return id;
	}
	public Card getCard_id() {
		return card;
	}
	public StatusChangeType getType() {
		return type;
	}
	public String getDate() {
		return date;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setCard_id(Card card_id) {
		this.card = card_id;
	}
	public void setType(StatusChangeType type) {
		this.type = type;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "StatusChange [id=" + id + ", card_id=" + card + ", type=" + type + ", date=" + date + "]";
	}

}