package org.stock_market.web_services;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.stock_market.dao.BankAccountDAO;
import org.stock_market.dao.CompanyDAO;
import org.stock_market.dao.PlayerDAO;
import org.stock_market.dao.PlayerSharesDAO;
import org.stock_market.dao.StockTransactionsDAO;
import org.stock_market.entities.BankAccount;
import org.stock_market.entities.Company;
import org.stock_market.entities.Player;
import org.stock_market.entities.PlayerShares;
import org.stock_market.entities.StockTransactions;

@Path("/broker")
public class BrokerService {
	@GET
    @Produces({ MediaType.APPLICATION_JSON})
    public List<Company> getStockPrices() {
        List<Company> companies = CompanyDAO.getAll();
        return companies;
    }
	
	@GET
    @Path("/{player}")
    @Produces({ MediaType.APPLICATION_JSON})
    public Player getPlayer(@PathParam("player") String player) {
        return PlayerDAO.get(player);
    }
	
	@GET
    @Path("stock-trans/{player}")
    @Produces({ MediaType.APPLICATION_JSON})
    public List<StockTransactions> getTrans(@PathParam("player") String player) {
        return StockTransactionsDAO.getTransactions(player);
    }
	@GET
    @Path("player-stocks/{player}")
    @Produces({ MediaType.APPLICATION_JSON})
    public List<PlayerShares> getPlayerStocks(@PathParam("player") String player) {
        return PlayerSharesDAO.getStocks(player);
    }
	
	@POST
	@Path("new-player")
    @Produces({ MediaType.APPLICATION_JSON})
    public Player addplayer(Player player) {
		BankAccount bank_account=new BankAccount(player.getPlayer_name());
		BankAccountDAO.save(bank_account);
        return PlayerDAO.save(player);
    }
	
}
