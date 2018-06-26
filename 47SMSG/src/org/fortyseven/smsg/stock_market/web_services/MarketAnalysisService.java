package org.fortyseven.smsg.stock_market.web_services;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.fortyseven.smsg.stock_market.core.GameCore;
import org.fortyseven.smsg.stock_market.entities.AnalystSuggession;
import org.fortyseven.smsg.stock_market.entities.ShareValues;

@Path("/analyst")
public class MarketAnalysisService {
	@GET
	@Path("stocks")
    @Produces({ MediaType.APPLICATION_JSON })
    public List<AnalystSuggession> getRecommendation() {
        List<ShareValues> company_trends=GameCore.getCompanyTrends();
        List<AnalystSuggession> recommendations=new ArrayList<AnalystSuggession>();
        for(ShareValues company_trend:company_trends) {
        	int round=GameCore.getCurrent_round()-1;
        	int remaining=10-round;
        	int future_up=remaining/2;
        	double current_value=company_trend.getRound_values()[round];
        	double future_value=company_trend.getRound_values()[(round+future_up)];
        	double ratio=(future_value/current_value)*100;
        	if(ratio>=110) {
        		AnalystSuggession recommendation=new AnalystSuggession(company_trend.getCompany_name(), "BUY");
        		recommendations.add(recommendation);
        	}
        	else if(ratio>=10 && ratio<100){
        		AnalystSuggession recommendation=new AnalystSuggession(company_trend.getCompany_name(), "SELL");
        		recommendations.add(recommendation);
        	}
        }
        return recommendations;
    }
	
}
