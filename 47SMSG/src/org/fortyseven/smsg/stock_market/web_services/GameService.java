package org.fortyseven.smsg.stock_market.web_services;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.fortyseven.smsg.stock_market.core.AiPlayerCore;
import org.fortyseven.smsg.stock_market.core.GameCore;
import org.fortyseven.smsg.stock_market.dao.BankAccountDAO;
import org.fortyseven.smsg.stock_market.dao.CompanyDAO;
import org.fortyseven.smsg.stock_market.dao.PlayerDAO;
import org.fortyseven.smsg.stock_market.dao.PlayerSharesDAO;
import org.fortyseven.smsg.stock_market.entities.BankAccount;
import org.fortyseven.smsg.stock_market.entities.Company;
import org.fortyseven.smsg.stock_market.entities.Player;
import org.fortyseven.smsg.stock_market.entities.PlayerShares;
import org.fortyseven.smsg.stock_market.entities.ShareValues;

@Path("/game")
public class GameService {
	
	@GET
    @Path("init")
    @Produces({ MediaType.APPLICATION_JSON })
    public String initMarket() {
		try {
        GameCore.initialize();
        return "Success";
		}catch(Exception e) {
        	return e.getMessage();
        }
    }
	
	@GET
    @Path("players")
    @Produces({ MediaType.APPLICATION_JSON })
    public List<Player> getPlayers() {
        return PlayerDAO.getAll();
    }
	
	@GET
    @Path("companies")
    @Produces({ MediaType.APPLICATION_JSON })
    public List<Company> getCompanies() {
        return CompanyDAO.getAll();
    }
	
	@GET
    @Path("current-round")
    @Produces({ MediaType.APPLICATION_JSON })
    public int getRound() {
        return GameCore.getCurrent_round();
    }
	
	@GET
    @Path("new-round")
    @Produces({ MediaType.APPLICATION_JSON })
    public int newRound() {
        GameCore.setCurrent_round(GameCore.getCurrent_round()+1);
        return GameCore.getCurrent_round();
    }
	
	@GET
    @Path("player-stock/{player_name}")
    @Produces({ MediaType.APPLICATION_JSON })
    public List<PlayerShares> getPlayerStocks(@PathParam("player_name") String player_name) {
        return PlayerSharesDAO.getStocks(player_name);
    }
	
	@GET
    @Path("bank-accounts")
    @Produces({ MediaType.APPLICATION_JSON })
    public List<BankAccount> getAccounts() {
        return BankAccountDAO.getAll();
    }
	
	@GET
    @Path("ai-players")
    @Produces({ MediaType.APPLICATION_JSON })
    public List<Player> getAIs() {
        return AiPlayerCore.getPlayers();
    }
	@GET
    @Path("company-trends")
    @Produces({ MediaType.APPLICATION_JSON })
    public List<ShareValues> getCompanyTrends() {
        return GameCore.getCompanyTrends();
    }
	@GET
    @Path("update_round/{player_name}")
    @Produces({ MediaType.APPLICATION_JSON })
    public Player updateRound(@PathParam("player_name") String player_name) {
		Player p=new Player();
		for(Player plyr:PlayerDAO.getAll()) {
			if(plyr.getPlayer_name().equals(player_name)) {
				p=plyr;
				int cur_round=p.getCurrent_round();
				if(cur_round<10) {
					p.setCurrent_round(p.getCurrent_round()+1);
					PlayerDAO.update(p);
				}
				
			}
		}
        return p;
    }
}
