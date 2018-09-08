package trends;



import java.util.ArrayList;
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
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
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
		
		//For MP Constituency
		Key mpKey = KeyFactory.createKey(state + " Loksabha", loksabha);
		Entity mpseat;
		try {
			mpseat = ds.get(mpKey);
			if(mpseat.hasProperty(mp + "votes")) {
				long votes = (long) mpseat.getProperty(mp + "votes");
				votes = votes + 1;
				mpseat.setProperty(mp + "votes", votes);
			}else {
				mpseat.setProperty(mp + "votes", 1);
			}
			ds.put(mpseat);
		} catch (EntityNotFoundException e) {
			Entity mpConst = new Entity(state + " Loksabha",loksabha);
			mpConst.setIndexedProperty("MPseat", loksabha);
			mpConst.setProperty(mp + "votes", 1);
			Key mpConstKey = KeyFactory.createKey(state + " Loksabha", loksabha);
			ds.put(mpConst);
		}
		
		//For writing Winner Property in Kind
		
		Key winnerKey = KeyFactory.createKey(state + " Loksabha", loksabha);
		Entity winConst;
		try {
			winConst = ds.get(winnerKey);
			Map<String,Object> prop = winConst.getProperties();
			System.out.println("All propeties loaded are " + prop);
			List<Party> partyList = new LinkedList<>();
			
			long totalVotes = 0;
			for(Entry<String,Object> winSet : prop.entrySet()) {
				if(!winSet.getKey().equals("MPseat") && !winSet.getKey().equals("Winner")) {
					String partyName = winSet.getKey().replaceAll("votes", "");
					long partyVotes = (Long) winSet.getValue();
					totalVotes = totalVotes + partyVotes;
					Party p = new Party(partyName, partyVotes, 0);
					partyList.add(p);

				}
			}
			
			System.out.println("totalvotes" + totalVotes);
			Collections.sort(partyList, new VotesComparator());
			System.out.println("After sorting " + partyList);
			
			//calculating voteshare

			voteShare(partyList, totalVotes);
			System.out.println("After calculating voteshare " + partyList);
			
			
			//setting Winner in Datastore
			String winner = partyList.get(0).getName();
			winConst.setProperty("Winner", winner);
			ds.put(winConst);
			
			//calculating India wide tally
		
		//Querying to check whether this constituency exists in data store or not.
			Filter constituencyFilter = new FilterPredicate("Constituencies",FilterOperator.EQUAL, winnerKey);
			Query constituencyQuery = new Query("India").setFilter(constituencyFilter);
			PreparedQuery constituencyPQ = ds.prepare(constituencyQuery);
			Entity resultConst = constituencyPQ.asSingleEntity();
			if(resultConst == null) {
				Key partyKeyInIndia = KeyFactory.createKey("India", mp);
				Entity india ;
				try {
					india = ds.get(partyKeyInIndia);
					List<Key> constThisPartyWinning = (List<Key>) india.getProperty("Constituencies");
					long currentScoreThisParty ;
					if(constThisPartyWinning == null) {
						List<Key> handleNull = new ArrayList<>();
						handleNull.add(winnerKey);
						currentScoreThisParty = handleNull.size();
						india.setProperty("Party",mp);
						india.setProperty("NoOfSeats", currentScoreThisParty);
						india.setProperty("Constituencies", handleNull);
						ds.put(india);
					}else {
						constThisPartyWinning.add(winnerKey);
						currentScoreThisParty = constThisPartyWinning.size();
						india.setProperty("Party",mp);
						india.setProperty("NoOfSeats", currentScoreThisParty);
						india.setProperty("Constituencies", constThisPartyWinning);
						ds.put(india);

					}
				}catch (EntityNotFoundException e) {
					Entity myFirstSeat = new Entity("India",mp);
					Key myFirstSeatKey = KeyFactory.createKey("India", mp);
					List<Key> constituency = new ArrayList<>();
					constituency.add(winnerKey);
					long score = constituency.size();
					myFirstSeat.setProperty("Party",mp);
					myFirstSeat.setProperty("NoOfSeats", score);
					myFirstSeat.setProperty("Constituencies", constituency);
					ds.put(myFirstSeat);

				}
			}else {
				String dbValue = (String) resultConst.getProperty("Party");
				System.out.println("Party value got from Database : " + dbValue);
				List<Key> dbConst = (List<Key>) resultConst.getProperty("Constituencies");
				System.out.println("" + dbValue + " is winning these constituencies " + dbConst);
				long score = (long) resultConst.getProperty("NoOfSeats");
				System.out.println("No.of Constituencies " + dbValue + " is winning is " + score);

				if(!dbValue.equals(winner)) {
					System.out.println("New Winner in " + loksabha);
					dbConst.remove(winnerKey); 
					score = dbConst.size();
					resultConst.setProperty("NoOfSeats", score);
					resultConst.setProperty("Constituencies", dbConst);
					ds.put(resultConst);
					Key updateWinner = KeyFactory.createKey("India", winner);
					Entity dbWinner;
					try {
						dbWinner = ds.get(updateWinner);
						String winnerName = (String) dbWinner.getProperty("Party");
						System.out.println("" + winnerName + " is increasing its tally in loksabha");
						List<Key> winningConst = (List<Key>) dbWinner.getProperty("Constituencies");
						System.out.println("Old tally of " + winnerName + " is " + winningConst);
						long winningScore = (long) dbWinner.getProperty("NoOfSeats");
						System.out.println("Old score of " + winnerName + " is " + winningScore);
						if(winningConst == null) {
							List<Key> handleNullExceptionList = new ArrayList<>();
							handleNullExceptionList.add(winnerKey);
							winningScore = handleNullExceptionList.size();
							dbWinner.setProperty("NoOfSeats", winningScore);
							dbWinner.setProperty("Constituencies", handleNullExceptionList);
							ds.put(dbWinner);
						}else {
							winningConst.add(winnerKey);
							System.out.println("New tally of " + winnerName + " is " + winningConst);

							winningScore = winningConst.size();
							System.out.println("New score of " + winnerName + " is " + winningScore);
							dbWinner.setProperty("NoOfSeats", winningScore);
							dbWinner.setProperty("Constituencies", winningConst);

							ds.put(dbWinner);
						}

					}catch (EntityNotFoundException e) {
						Entity firstConstituency = new Entity("India",mp);
						Key firstConstKey = KeyFactory.createKey("India", mp);
						List<Key> constituency = new ArrayList<>();
						constituency.add(winnerKey);
						long firstTimeScore = constituency.size();
						firstConstituency.setProperty("Party", mp);
						firstConstituency.setProperty("NoOfSeats", firstTimeScore);
						firstConstituency.setProperty("Constituencies", constituency);
						ds.put(firstConstituency);
					}
										  
				}
				
			}	
		} catch (EntityNotFoundException e) {
			System.out.println("Entity not found");
		}
		
	}
	public static void voteShare(List<Party> partyList , long totalVotes) {
		
		for(int i = 0; i < partyList.size(); i++) {
			float voteShare = 0;
			float votes = partyList.get(i).getVotes();
			voteShare = (votes/totalVotes) * 100 ;
			partyList.get(i).setVoteshare(voteShare);
		}
	
	}
	
}
