package org.fortyseven.smsg.stock_market.dao;

import java.util.ArrayList;
import java.util.List;

import org.fortyseven.smsg.stock_market.entities.MarketSector;

public class MarketSectorDAO {
	private static List<MarketSector> sector_list=new ArrayList<MarketSector>();
	
	public static List<MarketSector> getAll(){
		return sector_list;
	}
	
	public static List<MarketSector> update(MarketSector sector){
		return sector_list;
	}
	public static void init() {
		MarketSector sector=new MarketSector("Finance");
		sector_list.add(sector);
		sector=new MarketSector("Technology");
		sector_list.add(sector);
		sector=new MarketSector("Consumer Service");
		sector_list.add(sector);
		sector=new MarketSector("Manufacturing");
		sector_list.add(sector);
	}
}
