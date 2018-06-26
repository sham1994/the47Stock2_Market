package org.stock_market.entities;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement; 
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "bank_account") 
public class BankAccount implements Serializable {
	//Ishara_Dushyanthi_16211295
	private static final long serialVersionUID = 1L;
	private String AccountHolder;
	private float AccountBalance;
	
	public BankAccount(String accountHolder) {
		AccountHolder = accountHolder;
		AccountBalance = 1000;
	}
	
	public BankAccount() {
		AccountBalance=1000;
	}


	public static BankAccount getAccount(String name) {
		return new BankAccount();
	}
	
	public String getAccountHolder() {
		return AccountHolder;
	}
	@XmlElement
	public void setAccountHolder(String accountHolder) {
		AccountHolder = accountHolder;
	}
	public float getAccountBalance() {
		return AccountBalance;
	}
	@XmlElement
	public void setAccountBalance(float accountBalance) {
		AccountBalance = accountBalance;
	}
}
