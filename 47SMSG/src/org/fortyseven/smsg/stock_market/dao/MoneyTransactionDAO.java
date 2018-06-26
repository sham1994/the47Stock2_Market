package org.fortyseven.smsg.stock_market.dao;

import java.util.ArrayList;
import java.util.List;

import org.fortyseven.smsg.stock_market.entities.MoneyTransaction;
public class MoneyTransactionDAO {
	private static List<MoneyTransaction> account_transactions=new ArrayList<MoneyTransaction>();
	public static List<MoneyTransaction> getAll(){
		return account_transactions;
	}
	
	public static void save(MoneyTransaction account_transaction) {
		account_transactions.add(account_transaction);
	}
	
	public static List<MoneyTransaction> getTransactions(String account_holder){
		List<MoneyTransaction> transactions = new ArrayList<MoneyTransaction>();
		for(MoneyTransaction transaction:account_transactions) {
			if(transaction.getAccount_holder().equals(account_holder)) {
				transactions.add(transaction);
			}
		}
		return transactions;
	}
}
