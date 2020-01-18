package com.example.demo.model;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.data.rest.core.annotation.RestResource;

import com.example.demo.model.enums.StatusChangeType;

@Entity
public class Deletion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@OneToOne
	@JoinColumn(name = "Card")
	@RestResource(path = "CardAddress", rel = "card")
	private Card card;
	private String date;
	
	
	public Deletion(Card card) {
		this.card = card;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		this.date = dateFormat.format(date).toString();
		
	}
	public int getId() {
		return id;
	}
	public Card getCard_id() {
		return card;
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
	public void setDate(String date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "Deletion [id=" + id + ", card_id=" + card + ", date=" + date + "]";
	}


}
