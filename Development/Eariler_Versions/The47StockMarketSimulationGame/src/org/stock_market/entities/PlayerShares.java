package org.stock_market.entities;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement; 
import javax.xml.bind.annotation.XmlRootElement; 
@XmlRootElement(name = "player_stocks")

//Uvini Kulathunga 16211272
public class PlayerShares implements Serializable {
	private static final long serialVersionUID = 1L;
	private String player;
	private String Company;
	private int Stock_Count;
	private double Stock_Value;
	
	public String getPlayer() {
		return player;
	}
	@XmlElement
	public void setPlayer(String player) {
		this.player = player;
	}
	public String getCompany() {
		return Company;
	}
	@XmlElement
	public void setCompany(String company) {
		Company = company;
	}
	public int getStock_Count() {
		return Stock_Count;
	}
	@XmlElement
	public void setStock_Count(int stock_Count) {
		Stock_Count = stock_Count;
	}
	public double getStock_Value() {
		return Stock_Value;
	}
	@XmlElement
	public void setStock_Value(double stock_Value) {
		Stock_Value = stock_Value;
	}
	public PlayerShares() {
		
	}
	public PlayerShares(String player, String company, int stock_Count, double stock_Value) {
		super();
		this.player = player;
		Company = company;
		Stock_Count = stock_Count;
		Stock_Value = stock_Value;
	}
}
