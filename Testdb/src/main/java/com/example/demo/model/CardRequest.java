package com.example.demo.model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Random;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.example.demo.model.enums.CardForm;
import com.example.demo.model.enums.CardType;

@Entity
public class CardRequest {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    //verifikacija pri predaji zahtjeva??
    private CardForm form;
    private CardType type;
    private String address;
    private String date_of_req;
    private boolean creation_accepted;

    @OneToOne(mappedBy = "request_id", cascade = CascadeType.ALL)
    private Card card; 

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CardForm getForm() {
        return form;
    }

    public void setForm(CardForm form) {
        this.form = form;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate_of_req() {
        return date_of_req;
    }

    public void setDate_of_req(String date_of_req) {
        this.date_of_req = date_of_req;
    }

    public boolean isCreation_accepted() {
        return creation_accepted;
    }

    public void setCreation_accepted(boolean creation_accepted) {
        this.creation_accepted = creation_accepted;
    }
	
	
	@Override
	public String toString() {
		return "CardRequest [id=" + id + ", form=" + form + ", date_of_req=" + date_of_req + ", creation_accepted="
				+ creation_accepted + "]";
	}
	public  String generatePin()
	{
		Random random = new Random(System.currentTimeMillis());
		return Integer.toString(random.nextInt((10000-1000)+1)+1000);
	}
	public String generateCvv()
	{
		Random random = new Random(System.currentTimeMillis());
		return Integer.toString(random.nextInt((1000-100)+1)+100);
	}
	public String generateCardNumber() {
		String bin = "4444";
		int length = 16;

        Random random = new Random(System.currentTimeMillis());
        int randomNumberLength = length - (bin.length() + 1);

        StringBuilder builder = new StringBuilder(bin);
        for (int i = 0; i < randomNumberLength; i++) {
            int digit = random.nextInt(10);
            builder.append(digit);
        }

        int checkDigit = getCheckDigit(builder.toString());
        builder.append(checkDigit);

        return builder.toString();
    }
    @SuppressWarnings("unused")
	private int getCheckDigit(String number) {

        int sum = 0;
        for (int i = 0; i < number.length(); i++) {

            // Get the digit at the current position.
            int digit = Integer.parseInt(number.substring(i, (i + 1)));

            if ((i % 2) == 0) {
                digit = digit * 2;
                if (digit > 9) {
                    digit = (digit / 10) + (digit % 10);
                }
            }
            sum += digit;
        }

        int mod = sum % 10;
        return ((mod == 0) ? 0 : 10 - mod);
    }

        
    public Card createCard(Long owner_id, String ownername, String ownersurname, CardRequest request) {
        LocalDate date = LocalDate.now();
        int year = date.getYear() + 5;
        int month = date.getMonthValue();
        String expires = String.valueOf(month) + "/" + String.valueOf(year);
        String todaystr = date.toString();
        String card_numberEncrypted = AES.encrypt(this.generateCardNumber(), ownername + ownersurname);
        String cvv_encrypted = AES.encrypt(this.generateCvv(), card_numberEncrypted);
        String pin_encrypted  = AES.encrypt(this.generatePin(), cvv_encrypted);
        Card newcard = new Card(card_numberEncrypted, owner_id, ownername, ownersurname, this.getForm(), cvv_encrypted, pin_encrypted, todaystr, expires, request, this.getType(), 0);
        return newcard;
    }

}