package fr.eni.projetEnchere.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;


/*
 * A custom system to handle filters in a query decided from the HTML/Thymeleaf.
 * 
 * 
 * 
 * 
 */
public class FilterSystem {
	
	Logger logger = LoggerFactory.getLogger(ArticleController.class);
	
	private static String FILTER_SYS_INSTRUCT_SPLIT = "_----_";
	private static String FILTER_SYS_KEY_SPLIT = "_-_";
	
	// one dictionary for the A LIKE %B% condition, one for the A = B condition
	private Map<String, String> filterMapLike = new HashMap<String, String>();
	private Map<String, String> filterMapEquals = new HashMap<String, String>();
	
	
	// build from the set of entries coming from the HTML
	public FilterSystem(Set<Entry<String, String>> entrySet) {
		// browse all the html request parameters sent for us
		for (Entry<String, String> entry : entrySet) {
			String key = entry.getKey();
			String val = entry.getValue();
			if (val == null || val.isBlank() || val.isEmpty()) {val = "IGNORE";}
			if (!key.contains(FilterSystem.FILTER_SYS_KEY_SPLIT)) {
				logger.warn("Filter System: Warn: skipping non-instruction "+key);
				continue;
			}
			String rawInstructs = key+FilterSystem.FILTER_SYS_KEY_SPLIT+val;
			this.processFilterInstructions(rawInstructs);
		}
	}

	
	
	
	// processes a string-shaped-list of filter instructions
	private void processFilterInstructions(String rawInstructs) {
		// split the string list < <sql_mode, sql_name> > into all the pairs
		String[] instructionSplitKey = rawInstructs.split(FilterSystem.FILTER_SYS_INSTRUCT_SPLIT);
		for (String instruction : instructionSplitKey) {
			// [sql_mode, sql_name, sql_val]
			String[] modeNameVal = instruction.split(FilterSystem.FILTER_SYS_KEY_SPLIT);
			if (modeNameVal.length != 3) {
				logger.warn("Filter System: Warn: skipping badly shaped instruction "+instruction);
				continue;
			}
			String sql_mode = modeNameVal[0];
			String sql_name = modeNameVal[1];
			String sql_val = modeNameVal[2];
			
			this.processFilterKey(sql_mode, sql_name, sql_val);
		}
	}
	
	private void processFilterKey(String sql_mode, String sql_name, String sql_val) {
		// 
		if (!sql_mode.equals("like") && !sql_mode.equals("equals")) {
			logger.error("Filter System: Error: not recognising splitkey instruction " + sql_mode);
		}
		if (sql_val.equals("IGNORE") || sql_val.isBlank() || sql_val.isEmpty()) {
			logger.warn("Filter System: Warn: skipping key "+sql_name+" bc no value");
		}
		//TODO check that the sql_name is a name that exists in the sql query return?
		//	> really annoying possibly?
		else {
			if (sql_mode.equals("like")) {
				this.filterMapLike.put(sql_name, sql_val);
			}
			else if (sql_mode.equals("equals")) {
				this.filterMapEquals.put(sql_name, sql_val);
			}
		}
	}
	

	public Map<String, String> getFilterMapLike() {
		return filterMapLike;
	}
	public Map<String, String> getFilterMapEquals() {
		return filterMapEquals;
	}
	
	

	/* 
	 * 	toutes encheres
	 * get all (null, null, 1)
	 * 
	 * 	mes achats
	 * encheres en cours
	 * 	status = AuctionStarted
	 * 	is_found_bid = 1
	 * 
	 * encheres remportees non retirees
	 * 	status = AuctionEnded
	 * 	idBuyer = loggedMember.idMember
	 * 
	 * encheres remportees retirees
	 * 	status = Removed
	 * 	idBuyer = loggedMember.idMember
	 * 
	 * 
	 * 
	 * 	mes encheres
	 * ventes pre debut
	 * 	status = Created
	 * 	idVendor = loggedMember.idMember
	 * 
	 * ventes en cours
	 * 	status = AuctionStarted
	 * 	idVendor = loggedMember.idMember
	 * 
	 * ventes remportees non retirees
	 * 	status = AuctionEnded
	 * 	idVendor = loggedMember.idMember
	 * 
	 * ventes remportees retirees
	 * 	status = Removed
	 * 	idVendor = loggedMember.idMember
	 * 
	 * 
	 * 
	 * 
	 */
	
	
	
	
	
}
