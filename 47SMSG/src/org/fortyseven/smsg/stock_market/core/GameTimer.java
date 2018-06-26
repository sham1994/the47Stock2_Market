package org.fortyseven.smsg.stock_market.core;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.fortyseven.smsg.stock_market.dao.CompanyDAO;
import org.fortyseven.smsg.stock_market.dao.PlayerDAO;
import org.fortyseven.smsg.stock_market.dao.PlayerSharesDAO;
import org.fortyseven.smsg.stock_market.entities.*;
public class GameTimer {
	static int time;
    static Timer timer;
    public static void RoundTimer() {
    	int delay = 1000;
        int period = 1000;
        timer = new Timer();
        time = 120;
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                setInterval();
            }
        }, delay, period);
    }
    private static int setInterval() {
        if (time == 1) {
            timer.cancel();
            time=0;
            if(GameCore.getCurrent_round()<10) {
            	GameCore.setCurrent_round(GameCore.getCurrent_round()+1);
            	AiPlayerCore.play();
            	List<ShareValues> company_trends=GameCore.getCompanyTrends();
            	for(Company company:CompanyDAO.getAll()) {
            		for(ShareValues trend:company_trends) {
            			if(company.getCompany_Name().equals(trend.getCompany_name())){
            				company.setShare_Vlaue(trend.getRound_values()[GameCore.getCurrent_round()-1]);
            				CompanyDAO.update(company);
            				
            				for(PlayerShares stock:PlayerSharesDAO.getAll()) {
                    			if(company.getCompany_Name().equals(stock.getCompany())) {
                    				stock.setStock_Value(stock.getStock_Count()*trend.getRound_values()[GameCore.getCurrent_round()-1]);
                    				PlayerSharesDAO.update(stock);
                    			}
                    		}
            			}
            		}
            		
            	}
            	for(Player player:PlayerDAO.getAll()) {
            		List<PlayerShares> player_stocks=PlayerSharesDAO.getStocks(player.getPlayer_name());
            		double total_value=0;
            		for(PlayerShares stock:player_stocks) {
            			total_value=total_value+stock.getStock_Value();
            		}
            		player.setStock_value(total_value);
            		PlayerDAO.update(player);
            	}
            	RoundTimer();
            }
            return time;
        }
        else if (time==0){
            return time;
        }
        else{
        return --time;
        }
    }
}
