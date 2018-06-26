package org.fortyseven.smsg.stock_market.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.fortyseven.smsg.stock_market.entities.Company;
import org.fortyseven.smsg.stock_market.entities.MarketSector;
public class CompanyDAO {
	private static List<Company> company_stocks = new ArrayList<Company>();
	public static List<Company> getAll(){
		
		return company_stocks;
	}
	
	public static void save(Company company) {
		company_stocks.add(company);
	}
	public static Company update(Company company) {
		for(Company company_stock:company_stocks) {
			if(company.getCompany_Name().equals(company_stock.getCompany_Name())) {
				company_stock.setNo_of_Stocks(company.getNo_of_Stocks());
				company_stock.setRandom_Trend(company.getRandom_Trend());
				company_stock.setShare_Vlaue(company.getShare_Vlaue());
			}
		}
		return company;
	}
	
	public static void init() {
		List<MarketSector> sector_list=MarketSectorDAO.getAll();
		for(MarketSector sector:sector_list) {
			for(int i=0;i<3;i++) {
				Random random=new Random();
				Company company=new Company(getCompanyName(),0,random.nextInt(100-80)+80,sector.getSector_name());
				save(company);
			}
		}
		
	}
	private static String getCompanyName() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

}
