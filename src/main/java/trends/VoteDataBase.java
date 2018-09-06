package trends;



import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

public class VoteDataBase {
	static DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
	
	//User data base
	
	public static void vote(String name ,String state , String loksabha , String mp , String assembly , String mla) {
		Entity user = new Entity(state,name);
		user.setProperty("Name", name);
		user.setProperty("Loksabha", loksabha);
		user.setProperty("MP",mp);
		user.setProperty("Assembly", assembly);
		user.setProperty("MLA", mla);
		
		ds.put(user);
		
		Filter constFilter = new FilterPredicate("MPseat" , FilterOperator.EQUAL,loksabha);
		Query constQuery = new Query(state + " Loksabha").setFilter(constFilter);
		PreparedQuery pq = ds.prepare(constQuery);
		Entity mpseat = pq.asSingleEntity();
		if(mpseat == null) {
			Entity mpConst = new Entity(state + " Loksabha",loksabha);
			mpConst.setIndexedProperty("MPseat", loksabha);
			mpConst.setProperty(mp + "votes", 1);
			ds.put(mpConst);

		}else {				
			if(mpseat.hasProperty(mp + "votes")) {
				long votes = (long) mpseat.getProperty(mp + "votes");
				votes = votes + 1;
				mpseat.setProperty(mp + "votes", votes);
				ds.put(mpseat);
			}else {
				mpseat.setProperty(mp + "votes", 1);
				ds.put(mpseat);
			}
		}
		
		//For writing Winner Property in Kind
		Filter winFilter = new FilterPredicate("MPseat",FilterOperator.EQUAL, loksabha);
		Query winQuery = new Query(state + " Loksabha").setFilter(winFilter);
		PreparedQuery winPQ = ds.prepare(winQuery);
		Entity winConst = winPQ.asSingleEntity();
		Map<String,Object> prop = winConst.getProperties();
		System.out.println("All propeties loaded are " + prop);
		
		List<Entry<String,Object>> partyList = new LinkedList<>();

		for(Entry<String, Object> winSet : prop.entrySet()) {
			if(!winSet.getKey().equals("MPseat") && !winSet.getKey().equals("Winner")) {
				partyList.add(winSet);
			}
		}
		
		//for finding which party had highest votes in particular constituency
		Entry<String,Object> winningParty = null;
		long temp = 0;
		long totalVotes = 0;
		System.out.println("List before sorting " + partyList);
		for(int i = 0 ; i < partyList.size() ; i++) {
			if((Long)partyList.get(i).getValue() > temp) {
				temp = (Long)partyList.get(i).getValue();
				winningParty = partyList.get(i);
			}
			totalVotes = totalVotes + (Long)partyList.get(i).getValue();
		}
		String modify = winningParty.getKey();
		String removeVotes = modify.replaceAll("votes", "");
		
		//setting Winner in Constituency
		winConst.setIndexedProperty("Winner", removeVotes);
		ds.put(winConst);
		
		for(int i =0 ; i < partyList.size() ; i ++) {
			
		}
		
		System.out.println("Value of Highest votes is : " + temp);
		System.out.println("Total votes polled are " + totalVotes);
		System.out.println("Winning party is  : " + removeVotes );
		
		
		
		
	}
}
