// Developed by Yomal_Pasindu_16211304
//Transection Functionality 

package org.stock_market.entities;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement; 
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "stock_transactions")
public class StockTransactions implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	private String player;
	private String company;
	private int share_amount;
	private double transaction_amount;
	private String transaction_type;
	public String getPlayer() {
		return player;
	}
	@XmlElement
	public void setPlayer(String player) {
		this.player = player;
	}
	public String getCompany() {
		return company;
	}
	@XmlElement
	public void setCompany(String company) {
		this.company = company;
	}
	public int getShare_amount() {
		return share_amount;
	}
	@XmlElement
	public void setShare_amount(int share_amount) {
		this.share_amount = share_amount;
	}
	public double getTransaction_amount() {
		return transaction_amount;
	}
	@XmlElement
	public void setTransaction_amount(double transaction_amount) {
		this.transaction_amount = transaction_amount;
	}
	public String getTransaction_type() {
		return transaction_type;
	}
	@XmlElement
	public void setTransaction_type(String transaction_type) {
		this.transaction_type = transaction_type;
	}
	public StockTransactions(String player, String company, int share_amount, double transaction_amount,
			String transaction_type) {
		this.player = player;
		this.company = company;
		this.share_amount = share_amount;
		this.transaction_amount = transaction_amount;
		this.transaction_type = transaction_type;
	}
	public StockTransactions() {
		
	}
	
}
