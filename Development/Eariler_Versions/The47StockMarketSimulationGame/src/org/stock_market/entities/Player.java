package org.stock_market.entities;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement; 
import javax.xml.bind.annotation.XmlRootElement; 
@XmlRootElement(name = "player") 
public class Player implements Serializable {
	private static final long serialVersionUID = 1L;
	private String player_name;
	private double stock_value;
	
	public Player() {
		
	}
	public Player(String player_name, double stock_value) {
		this.player_name = player_name;
		this.stock_value = stock_value;
	}
	public String getPlayer_name() {
		return player_name;
	}
	@XmlElement
	public void setPlayer_name(String player_name) {
		this.player_name = player_name;
	}
	public double getStock_value() {
		return stock_value;
	}
	@XmlElement
	public void setStock_value(double stock_value) {
		this.stock_value = stock_value;
	}
	
	
}
