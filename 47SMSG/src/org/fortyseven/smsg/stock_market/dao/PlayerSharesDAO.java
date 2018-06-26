package org.fortyseven.smsg.stock_market.dao;
import java.util.ArrayList;
import java.util.List;

import org.fortyseven.smsg.stock_market.entities.PlayerShares;
public class PlayerSharesDAO {
	private static List<PlayerShares> player_stocks = new ArrayList<PlayerShares>();
	public static List<PlayerShares> getAll(){
		return player_stocks;
	}
	
	public static void save(PlayerShares player_stock) {
		player_stocks.add(player_stock);	
	}
	public static List<PlayerShares> update(PlayerShares player_stock) {
		for(PlayerShares stock:player_stocks) {
			if((stock.getPlayer().equals(player_stock.getPlayer())) && (stock.getCompany().equals(player_stock.getCompany()))) {
				stock.setStock_Count(player_stock.getStock_Count());
				stock.setStock_Value(player_stock.getStock_Value());
			}
		}
		return player_stocks;
	}
	public static List<PlayerShares> getStocks(String player_name){
		List<PlayerShares> playerStocks = new ArrayList<PlayerShares>();
		for(PlayerShares stock:player_stocks) {
			if(stock.getPlayer().equals(player_name)) {
				playerStocks.add(stock);
			}
		}
		return playerStocks;
	}
}
