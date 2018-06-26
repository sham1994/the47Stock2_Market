package org.stock_market.dao;
import java.util.List;

import org.stock_market.entities.PlayerShares;

//Uvini Kulathunga 16211272




public class PlayerSharesDAO {
//this DAO(Data Access Object Responsible for Listing PlayerShares)
	public static List<PlayerShares> getAll(){
		List<PlayerShares> player_stocks = null;
		return player_stocks;
	}
	
	public static void save(PlayerShares player_stock) {
		
	}
	public static PlayerShares update(PlayerShares player_stock) {
		return player_stock;
	}
	public static List<PlayerShares> getStocks(String player_name){
		List<PlayerShares> account_transactions = null;
		return account_transactions;
	}


}
