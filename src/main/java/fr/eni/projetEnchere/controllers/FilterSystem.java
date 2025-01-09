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
 * Takes in a set of HTML request parameters, and parses them.
 * 
 * Builds a set of filters based on the SQL condition A %LIKE% B, and another
 * set of filters based on the condition A = B, resp. "filterMapLike" and 
 * "filterMapEquals".
 * 
 * To use: 
 * - new FilterSystem(set of HTML requests)
 * - get each filter map
 * 
 * The HTML requests need to be shaped as: 
 * [like/equals]_-_[key_name]_-_[key_value]
 * and can be chained with the _----_ splitter (both splitters being variable).
 * 
 * For example: 
 * - name="equals_-_status" value="IGNORE"
 * - name="equals_-_status" value="AuctionStarted"
 * - name="equals_-_status" th:value="'AuctionEnded_----_equals_-_idBuyer_-_'+${idMember}"
 * - name="equals_-_status" th:value="'AuctionEnded_----_equals_-_idVendor_-_'+${idMember}"
 * The filter system takes every 'name'/'value' pair coming from the HTML, concatenates each half with
 * a _-_ splitter, then processes the entire string as a list (_----_ separated) of instructions, 
 * with each instruction being [like/equals]_-_[key_name]_-_[key_value].
 * 
 */
public class FilterSystem {
	
	Logger logger = LoggerFactory.getLogger(ArticleController.class);
	
	private static String FILTER_SYS_INSTRUCT_SPLIT = "_----_";
	private static String FILTER_SYS_KEY_SPLIT = "_-_";
	
	// A LIKE %B% condition
	private Map<String, String> filterMapLike = new HashMap<String, String>();
	// A = B condition
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
