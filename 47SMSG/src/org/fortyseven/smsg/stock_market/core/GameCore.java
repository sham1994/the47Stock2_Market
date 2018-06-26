package org.fortyseven.smsg.stock_market.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.fortyseven.smsg.stock_market.dao.CompanyDAO;
import org.fortyseven.smsg.stock_market.dao.MarketSectorDAO;
import org.fortyseven.smsg.stock_market.dao.PlayerDAO;
import org.fortyseven.smsg.stock_market.entities.*;
public class GameCore {
	
	private static int current_round=1;
	private static List<Player> player_list;
	private static int[] market_trends=new int[10];
	private static int[] general_trends=new int[10];
	private static List<MarketSector> sector_trends=new ArrayList<MarketSector>();
	private static List<MarketEvent> event_list=new ArrayList<MarketEvent>();
	private static List<ShareValues> company_trends=new ArrayList<ShareValues>();
	public static boolean game_started=false;
	
	public static int[] getGeneral_trends() {
		return general_trends;
	}
	public static void setGeneral_trends(int[] generalTrends) {
		general_trends = generalTrends;
	}
	public static int getCurrent_round() {
		return current_round;
	}
	public static void setCurrent_round(int currentRound) {
		current_round = currentRound;
	}
	public static List<Player> getPlayer_list() {
		return player_list;
	}
	public static void setPlayer_list(List<Player> playerList) {
		player_list = playerList;
	}
	public static int[] getMarket_trends() {
		return market_trends;
	}
	public static void setMarket_trends(int[] marketTrends) {
		market_trends = marketTrends;
	}
	public static List<MarketSector> getSector_trends() {
		return sector_trends;
	}
	public static void setSector_trends(List<MarketSector> sectorTrends) {
		sector_trends = sectorTrends;
	}
	public static List<MarketEvent> getEvent_list() {
		return event_list;
	}
	public static List<ShareValues> getCompanyTrends(){
		return company_trends;
	}
	public void setEvent_list(List<MarketEvent> eventList) {
		event_list = eventList;
	}
	
	public static void initialize() {
		if(MarketSectorDAO.getAll().isEmpty()) {
			MarketSectorDAO.init();
		}
		if(CompanyDAO.getAll().isEmpty()) {
			CompanyDAO.init();
		}
		if(market_trends.length<10) {
			generateMarketTrends();
		}
		if(sector_trends.isEmpty()) {
			generateSectorTrends();
		}
		if(event_list.isEmpty()) {
			generateEventTrends();
		}
		if(general_trends.length<10) {
			generateGeneralTrends();
		}
		if(company_trends.isEmpty()) {
			calRoundStockValues();
		}
//		checkPlayers();
//		getCompanyValues();
	}
	
	
	private static void generateMarketTrends() {
		for(int i=0;i<10;i++){
		       market_trends[i] = -2 + (int) (Math.random() * ((2 - (-2)) + 1));
		}
	}
	
	private static void generateSectorTrends() {
		List<MarketSector> sectors=MarketSectorDAO.getAll();
		for(MarketSector sector:sectors) {
			for(int i=0;i<10;i++) {
				MarketSector trend=new MarketSector(sector.getSector_name());
				int sec = -3 + (int) (Math.random() * ((3 - (-3)) + 1));
				trend.setSector_Trend(sec);
				sector_trends.add(trend);
			}
		}
	}
	
	private static void generateEventTrends() {
		int end = 0;
		int start=0;
		int duration=0;
		String event_type="";
		while(end<10){
			if(end==0){
				start=2;
			}
			else{
				start=end+1;
			}
			duration = (int )(Math.random() * 8 + 1);
			end=start+duration;
			while(end>10){
				duration = (int )(Math.random() * 8 + 1);
				end=start+duration;               
			}
			//Random Market Component: 
			int range=-6 + (int) (Math.random() * ((5 - (-6)) + 1));
			if(range<=3 && range>=2 )
			    event_type="PROFIT_WARNING";
			if((range<=-3 && range>=-6)&&(range<=-1 && range>=-5) ){
				int chk = (int )(Math.random() * 2 + 1);
			    if(chk==1){
			        event_type="TAKE_OVER ";
			    }
			    if(chk==2){
			        event_type="SCANDAL ";
			    }
			}
			if(range<=-3 && range>=-6){
			    event_type="SCANDAL ";
			}
			if(range<=-1 && range>=-5){
			     event_type="TAKE_OVER ";
			}
			MarketEvent event= new MarketEvent(event_type,start,end,range);
			event_list.add(event);
		}
	}
	public static void checkPlayers() {
		if(PlayerDAO.getAll().size()<3) {
			while(PlayerDAO.getAll().size()<3) {
				Player player=new Player(getPlayerName());
				PlayerDAO.save(player);
				AiPlayerCore.newAiPlayer(player);
				AiPlayerCore.play();
			}
		}
		if(game_started==false) {
			game_started=true;
			GameTimer.RoundTimer();
		}
	}
//	private void getCompanyValues() {
//		
//	}
	private static void generateGeneralTrends() {
		for(int i=0;i<10;i++)
			general_trends[i] = -3 + (int) (Math.random() * ((3 - (-3)) + 1));
		
	}
	
	private static String getPlayerName() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }
	
	public static void calRoundStockValues() {
		
		List<Company> companies=CompanyDAO.getAll();
		for(Company company:companies) {
			double shareValue=0;
			double[] round_values=new double[10];
			for(int i=0;i<10;i++) {
				int round=GameCore.getCurrent_round()-1;
				if(i==0) {
					shareValue=company.getShare_Vlaue();
				}else {
					shareValue=round_values[i-1];
				}
				int sec_trend=0;
				List<MarketSector> company_sectors=new ArrayList<MarketSector>();
				for(MarketSector sector:sector_trends) {
					if(company.getCompany_Sector()==sector.getSector_name()) {
						company_sectors.add(sector);
					}
				}
				if(company_sectors.get(i)!=null) {
					sec_trend=company_sectors.get(i).getSector_Trend();
				}
				int event_trend=0;
				for(MarketEvent evnt:event_list) {
					if(i<=evnt.getEnd_turn() && i>=evnt.getStart_turn()) {
						event_trend=evnt.getEvent_value();
					}
				}
				shareValue=shareValue+market_trends[i]+general_trends[i]+sec_trend+event_trend;
				round_values[i]=shareValue;
				if(round==i) {
					company.setShare_Vlaue(round_values[i]);
					CompanyDAO.update(company);
				}
			}
			ShareValues company_trend=new ShareValues(company.getCompany_Name(), round_values);
			company_trends.add(company_trend);
		}
		
	}
}
