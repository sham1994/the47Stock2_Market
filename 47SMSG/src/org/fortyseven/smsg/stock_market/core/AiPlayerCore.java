package org.fortyseven.smsg.stock_market.core;

import java.util.ArrayList;
import java.util.List;

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
import org.fortyseven.smsg.stock_market.entities.ShareValues;
import org.fortyseven.smsg.stock_market.entities.StockTransactions;

public class AiPlayerCore {
	private static List<Player> ai_players = new ArrayList<Player>();

	public static List<Player> getPlayers() {
		return ai_players;
	}
	
	public static void newAiPlayer(Player player) {
		ai_players.add(player);
	}

	public static void setPlayers(List<Player> players) {
		AiPlayerCore.ai_players = players;
	}
	
	public static void buy_shares(Player player) {
		List<ShareValues> stock_predictions=GameCore.getCompanyTrends();
		List<Company> available_to_buy=new ArrayList<Company>();
		List<Company> company_list=CompanyDAO.getAll();
		int current_round=GameCore.getCurrent_round()-1;
		for(ShareValues company_trend:stock_predictions) {
        	
        	int remaining_rounds=10-current_round;
        	int upcoming=remaining_rounds/2;
        	double current_value=company_trend.getRound_values()[current_round];
        	double future_value=company_trend.getRound_values()[(current_round+upcoming)];
        	double ratio=(future_value/current_value)*100;
        	if(ratio>=110) {
        		for(Company company:company_list) {
        			if(company.getCompany_Name().equals(company_trend.getCompany_name())) {
        				available_to_buy.add(company);
        			}
        		}
        	}
        }
		List<PlayerShares> playershares=new ArrayList<PlayerShares>();
		List<PlayerShares> sharelist=PlayerSharesDAO.getAll();
		for(PlayerShares share:sharelist) {
			if(share.getPlayer()==player.getPlayer_name()) {
				playershares.add(share);
			}
		}
		for(BankAccount bankAccount:BankAccountDAO.getAll()) {
    		if(bankAccount.getAccountHolder().equals(player.getPlayer_name())) {
    			BankAccount player_account=bankAccount;
    			double balance=player_account.getAccountBalance();
    			double buyable_balance=balance*2/3;
    			for(Company company:available_to_buy) {
    				if(buyable_balance>0) {
    					int buyable_shares=(int)(buyable_balance/company.getShare_Vlaue());
    					if(buyable_shares>0) {
    						for(ShareValues trend:stock_predictions) {
            					if(trend.getCompany_name().equals(company.getCompany_Name())) {
            						double current_value=trend.getRound_values()[current_round];
            						boolean found=false;
            					for(PlayerShares stock:playershares) {
                					if(stock.getCompany().equals(company.getCompany_Name())) {
                						stock.setStock_Count(stock.getStock_Count()+buyable_shares);
                						stock.setStock_Value(stock.getStock_Value()*current_value);
                						PlayerSharesDAO.update(stock);
                        				StockTransactions transaction=new StockTransactions(player.getPlayer_name(),stock.getCompany(),buyable_shares,current_value*buyable_shares,"BUY");
                        				StockTransactionsDAO.save(transaction);
                        				BankAccountDAO.withdraw(bankAccount, (current_value*buyable_shares));
                						MoneyTransaction account_transaction=new MoneyTransaction(bankAccount.getAccountHolder(), "WITHDRAW", (current_value*buyable_shares), (bankAccount.getAccountBalance()-(current_value*buyable_shares)));
                						MoneyTransactionDAO.save(account_transaction);
                						buyable_balance=buyable_balance-(current_value*buyable_shares);
                						player.setStock_value(player.getStock_value()+(current_value*buyable_shares));
                        				PlayerDAO.update(player);
                						found=true;
                					}
                				}
            					if(found==false) {
            						PlayerShares player_stock=new PlayerShares(player.getPlayer_name(),company.getCompany_Name(),buyable_shares,current_value*buyable_shares);
            						PlayerSharesDAO.save(player_stock);
            						StockTransactions transaction=new StockTransactions(player.getPlayer_name(),company.getCompany_Name(),buyable_shares,current_value*buyable_shares,"BUY");
                    				StockTransactionsDAO.save(transaction);
                    				BankAccountDAO.withdraw(bankAccount, (current_value*buyable_shares));
            						MoneyTransaction account_transaction=new MoneyTransaction(bankAccount.getAccountHolder(), "WITHDRAW", (current_value*buyable_shares), (bankAccount.getAccountBalance()-(current_value*buyable_shares)));
            						MoneyTransactionDAO.save(account_transaction);
            						buyable_balance=buyable_balance-(current_value*buyable_shares);
            						player.setStock_value(player.getStock_value()+(current_value*buyable_shares));
                    				PlayerDAO.update(player);
            					}
            					}
            				}
    					}
    				}
    			}
    			break;
    		}
    		
    	}
	}
	
	public static void sell_shares(Player player) {
		
		List<ShareValues> company_trends=GameCore.getCompanyTrends();
		List<PlayerShares> stocks=PlayerSharesDAO.getStocks(player.getPlayer_name());
		List<PlayerShares> player_stocks=new ArrayList<PlayerShares>();
		for(PlayerShares stock:stocks) {
			if(stock.getPlayer().equals(player.getPlayer_name())) {
				player_stocks.add(stock);
			}
		}
		for(ShareValues company_trend:company_trends) {
        	int round=GameCore.getCurrent_round()-1;
        	int remaining=10-round;
        	int future_up=remaining/2;
        	double current_value=company_trend.getRound_values()[round];
        	double future_value=company_trend.getRound_values()[(round+future_up)];
        	double ratio=(future_value/current_value)*100;
        	if(ratio>=10) {
        		for(PlayerShares stock:player_stocks) {
        			if(stock.getCompany().equals(company_trend.getCompany_name())) {
        				int shares=stock.getStock_Count();
        				int sellable=shares*2/3;
        				stock.setStock_Count(stock.getStock_Count()-sellable);
        				stock.setStock_Value(stock.getStock_Count()*current_value);
        				PlayerSharesDAO.update(stock);
        				StockTransactions transaction=new StockTransactions(player.getPlayer_name(),stock.getCompany(),sellable,current_value*sellable,"SELL");
        				StockTransactionsDAO.save(transaction);
        				player.setStock_value(player.getStock_value()-(current_value*sellable));
        				PlayerDAO.update(player);
        				for(BankAccount bank_account:BankAccountDAO.getAll()) {
        					if(bank_account.getAccountHolder().equals(player.getPlayer_name())) {
        						BankAccountDAO.deposit(bank_account, (current_value*sellable));
        						MoneyTransaction account_transaction=new MoneyTransaction(bank_account.getAccountHolder(), "DEPOSIT", (current_value*sellable), (bank_account.getAccountBalance()+(current_value*sellable)));
        						MoneyTransactionDAO.save(account_transaction);
        						break;
        					}
        				}
        			}
        		}
        	}
        }
	}
	
	public static void play() {
		for(Player player:ai_players) {
			sell_shares(player);
			buy_shares(player);
		}
	}
}
