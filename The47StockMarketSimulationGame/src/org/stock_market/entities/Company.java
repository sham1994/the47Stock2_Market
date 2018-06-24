package org.stock_market.entities;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement; 
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "company_stocks")
public class Company implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String Company_Name;
	private int No_of_Stocks;
	private double Share_Vlaue;
	
	public String getCompany_Name() {
		return Company_Name;
	}
	@XmlElement
	public void setCompany_Name(String company_Name) {
		Company_Name = company_Name;
	}
	public int getNo_of_Stocks() {
		return No_of_Stocks;
	}
	@XmlElement
	public void setNo_of_Stocks(int no_of_Stocks) {
		No_of_Stocks = no_of_Stocks;
	}
	public double getShare_Vlaue() {
		return Share_Vlaue;
	}
	@XmlElement
	public void setShare_Vlaue(double share_Vlaue) {
		Share_Vlaue = share_Vlaue;
	}
	public Company(String company_Name, int no_of_Stocks, double share_Vlaue) {
		Company_Name = company_Name;
		No_of_Stocks = no_of_Stocks;
		Share_Vlaue = share_Vlaue;
	}
	public Company() {
		
	}
	
	
	
}
