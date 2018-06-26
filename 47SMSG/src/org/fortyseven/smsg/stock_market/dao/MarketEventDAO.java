package org.fortyseven.smsg.stock_market.dao;
import java.util.ArrayList;
import java.util.List;

import org.fortyseven.smsg.stock_market.entities.MarketEvent;
public class MarketEventDAO {
	private static List<MarketEvent> event_list=new ArrayList<MarketEvent>();
	public static List<MarketEvent> getAll(){
		return event_list;
	}
	public static void save(MarketEvent event) {
		event_list.add(event);
	}
}
