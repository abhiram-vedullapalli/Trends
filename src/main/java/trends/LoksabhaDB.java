package trends;

import java.util.LinkedList;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.PropertyProjection;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
public class LoksabhaDB {
	static DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

	public static List<Party> loksabhaSeats(){
		List<Party> loksabhaTally = new LinkedList<>();
		
		Filter seatsFilter = new FilterPredicate("NoOfSeats",FilterOperator.GREATER_THAN_OR_EQUAL, 1);
		Query seatsQuery = new Query("India");
		seatsQuery.addProjection(new PropertyProjection("NoOfSeats", Long.class));
		seatsQuery.addProjection(new PropertyProjection("Party",String.class));
		seatsQuery.setFilter(seatsFilter);
		seatsQuery.addSort("NoOfSeats",Query.SortDirection.DESCENDING);
		PreparedQuery seatsPQ = ds.prepare(seatsQuery);
		
		for(Entity result : seatsPQ.asIterable()) {
			String partyName = (String) result.getProperty("Party");
			long partySeats = (long) result.getProperty("NoOfSeats");
			Party p = new Party(partyName, partySeats);
			loksabhaTally.add(p);
		}
		System.out.println("Current Positions in Loksabha are " + loksabhaTally);
		return loksabhaTally;
		
	}
}
