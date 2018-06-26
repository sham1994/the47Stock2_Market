package org.fortyseven.smsg.stock_market.web_services;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


import org.fortyseven.smsg.stock_market.core.GameCore;
import org.fortyseven.smsg.stock_market.dao.BankAccountDAO;
import org.fortyseven.smsg.stock_market.dao.CompanyDAO;
import org.fortyseven.smsg.stock_market.dao.MoneyTransactionDAO;
import org.fortyseven.smsg.stock_market.dao.PlayerDAO;
import org.fortyseven.smsg.stock_market.dao.PlayerSharesDAO;
import org.fortyseven.smsg.stock_market.dao.StockTransactionsDAO;
import org.fortyseven.smsg.stock_market.entities.BankAccount;
import org.fortyseven.smsg.stock_market.entities.Company;
import org.fortyseven.smsg.stock_market.entities.MoneyTransaction;
import org.fortyseven.smsg.stock_market.entities.Player;
import org.fortyseven.smsg.stock_market.entities.PlayerShares;
import org.fortyseven.smsg.stock_market.entities.StockTransactions;

@Path("/player-broker")
public class PlayerBrokerService {
	@GET
    @Produces({ MediaType.APPLICATION_JSON })
    public List<Company> getCompanies() {
        List<Company> company_list = CompanyDAO.getAll();
        return company_list;
    }
	
	@GET
    @Path("player-info/{player}")
    @Produces({ MediaType.APPLICATION_JSON })
    public Player getPlayer(@PathParam("player") String player) {
        return PlayerDAO.get(player);
    }
	@GET
    @Path("bankdetails/{player}")
    @Produces({ MediaType.APPLICATION_JSON })
    public BankAccount bankAccount(@PathParam("player") String player) {
        return BankAccountDAO.get(player);
    }
	
	@GET
    @Path("stocktransactions/{player}")
    @Produces({ MediaType.APPLICATION_JSON })
    public List<StockTransactions> getTrans(@PathParam("player") String player) {
        return StockTransactionsDAO.getTransactions(player);
    }
	@GET
    @Path("accounttransactions/{player}")
    @Produces({ MediaType.APPLICATION_JSON })
    public List<MoneyTransaction> getAccTrans(@PathParam("player") String player) {
        return MoneyTransactionDAO.getTransactions(player);
    }
	@GET
    @Path("playershares/{player}")
    @Produces({ MediaType.APPLICATION_JSON })
    public List<PlayerShares> getPlayerStocks(@PathParam("player") String player) {
        return PlayerSharesDAO.getStocks(player);
    }
	@GET
    @Path("players")
    @Produces({ MediaType.APPLICATION_JSON })
    public List<Player> getPlayers() {
        return PlayerDAO.getAll();
    }
	
	@GET
	@Path("addplayer/{player_name}")
    @Produces({ MediaType.APPLICATION_JSON })
    public boolean addplayer(@PathParam("player_name") String player_name) {
		Player player=new Player(player_name);
		boolean not_found=true;
		for(Player plyr:PlayerDAO.getAll()) {
			if(plyr.getPlayer_name().equals(player.getPlayer_name())) {
				not_found=false;
			}
		}
		if(not_found) {
			BankAccount bank_account=new BankAccount(player.getPlayer_name());
			BankAccountDAO.save(bank_account);
			PlayerDAO.save(player);
			GameCore.checkPlayers();
			return not_found;
		}else {
			return not_found;
		}
        
    }
	
	
	
	@GET
    @Path("sell/{player}/{company}/{stocks}")
    @Produces({ MediaType.APPLICATION_JSON })
    public boolean sellShares(@PathParam("player") String player,@PathParam("company") String company,@PathParam("stocks") int stocks) {
		List<PlayerShares> stocks_list=PlayerSharesDAO.getStocks(player);
		Player plyr=PlayerDAO.get(player);
		for(Company cmpny:CompanyDAO.getAll()) {
			if(cmpny.getCompany_Name().equals(company)) {
				for(PlayerShares ps:stocks_list) {
					if(ps.getCompany().equals(cmpny.getCompany_Name())) {
						ps.setStock_Count(ps.getStock_Count()-stocks);
						ps.setStock_Value(ps.getStock_Count()*cmpny.getShare_Vlaue());
        				PlayerSharesDAO.update(ps);
        				StockTransactions transaction=new StockTransactions(plyr.getPlayer_name(),ps.getCompany(),stocks,cmpny.getShare_Vlaue()*stocks,"SELL");
        				StockTransactionsDAO.save(transaction);
        				plyr.setStock_value(plyr.getStock_value()-(cmpny.getShare_Vlaue()*stocks));
        				PlayerDAO.update(plyr);
        				for(BankAccount bank_account:BankAccountDAO.getAll()) {
        					if(bank_account.getAccountHolder().equals(plyr.getPlayer_name())) {
        						BankAccountDAO.deposit(bank_account, (cmpny.getShare_Vlaue()*stocks));
        						MoneyTransaction account_transaction=new MoneyTransaction(bank_account.getAccountHolder(), "DEPOSIT", (cmpny.getShare_Vlaue()*stocks), (bank_account.getAccountBalance()+(cmpny.getShare_Vlaue()*stocks)));
        						MoneyTransactionDAO.save(account_transaction);
        						break;
        					}
        				}
					}
				}
			}
		}
        return true;
    }
	
	@GET
    @Path("buy/{player}/{company}/{stocks}")
    @Produces({ MediaType.APPLICATION_JSON })
    public boolean buyshares(@PathParam("player") String player,@PathParam("company") String company,@PathParam("stocks") int stocks) {
		List<PlayerShares> stocks_list=PlayerSharesDAO.getStocks(player);
		Player plyr=PlayerDAO.get(player);
		boolean found=false;
		for(Company cmpny:CompanyDAO.getAll()) {
			if(cmpny.getCompany_Name().equals(company)) {
				for(PlayerShares ps:stocks_list) {
					if(ps.getCompany().equals(cmpny.getCompany_Name())) {
						found=true;
        				for(BankAccount bank_account:BankAccountDAO.getAll()) {
        					if(bank_account.getAccountHolder().equals(plyr.getPlayer_name())) {
        						if(bank_account.getAccountBalance()>=(cmpny.getShare_Vlaue()*stocks)) {
        							ps.setStock_Count(ps.getStock_Count()+stocks);
        							ps.setStock_Value(ps.getStock_Count()*cmpny.getShare_Vlaue());
        	        				PlayerSharesDAO.update(ps);
        	        				StockTransactions transaction=new StockTransactions(plyr.getPlayer_name(),ps.getCompany(),stocks,cmpny.getShare_Vlaue()*stocks,"BUY");
        	        				StockTransactionsDAO.save(transaction);
        	        				plyr.setStock_value(plyr.getStock_value()+(cmpny.getShare_Vlaue()*stocks));
        	        				PlayerDAO.update(plyr);
        	        				cmpny.setNo_of_Stocks(cmpny.getNo_of_Stocks()+stocks);
        	        				CompanyDAO.update(cmpny);
        							MoneyTransaction account_transaction=new MoneyTransaction(bank_account.getAccountHolder(), "WITHDRAW", (cmpny.getShare_Vlaue()*stocks), (bank_account.getAccountBalance()-(cmpny.getShare_Vlaue()*stocks)));
            						MoneyTransactionDAO.save(account_transaction);
            						BankAccountDAO.withdraw(bank_account, (cmpny.getShare_Vlaue()*stocks));
        						}
        						break;
        					}
        				}
					}
				}
				if(found==false) {
					
    				for(BankAccount bank_account:BankAccountDAO.getAll()) {
    					if(bank_account.getAccountHolder().equals(plyr.getPlayer_name())) {
    						if(bank_account.getAccountBalance()>=(cmpny.getShare_Vlaue()*stocks)) {
    							PlayerShares player_stock=new PlayerShares(plyr.getPlayer_name(),cmpny.getCompany_Name(),stocks,cmpny.getShare_Vlaue()*stocks);
    							PlayerSharesDAO.save(player_stock);
    							StockTransactions transaction=new StockTransactions(plyr.getPlayer_name(),cmpny.getCompany_Name(),stocks,cmpny.getShare_Vlaue()*stocks,"BUY");
    		    				StockTransactionsDAO.save(transaction);
    		    				plyr.setStock_value(plyr.getStock_value()+(cmpny.getShare_Vlaue()*stocks));
    		    				PlayerDAO.update(plyr);
    		    				CompanyDAO.update(cmpny);
    							MoneyTransaction account_transaction=new MoneyTransaction(bank_account.getAccountHolder(), "WITHDRAW", (cmpny.getShare_Vlaue()*stocks), (bank_account.getAccountBalance()-(cmpny.getShare_Vlaue()*stocks)));
        						MoneyTransactionDAO.save(account_transaction);
        						BankAccountDAO.withdraw(bank_account, (cmpny.getShare_Vlaue()*stocks));
    						}
    						break;
    					}
    				}
    				
				}
			}
		}
        return true;
    }
	
	@GET
    @Path("stock-count/{company_name}/{player_name}")
    @Produces({ MediaType.APPLICATION_JSON })
    public double getStockCount(@PathParam("company_name") String company_name,@PathParam("player_name") String player_name) {
        return PlayerSharesDAO.getShareCount(company_name, player_name);
    }
	
}
