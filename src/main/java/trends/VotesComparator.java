package trends;
import java.util.Comparator;
public class VotesComparator implements Comparator<Party> {

	
	@Override
	public int compare(Party o1, Party o2) {
		// TODO Auto-generated method stub
		return (int) (o2.getVotes() - o1.getVotes());
	}

}
