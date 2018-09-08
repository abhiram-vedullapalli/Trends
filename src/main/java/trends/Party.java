package trends;

public class Party {
private String name;
private long votes;
private float voteshare;
public Party(String name, long votes,float voteshare) {
	this.name = name;
	this.votes = votes;
	this.voteshare =voteshare;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public long getVotes() {
	return votes;
}
public void setVotes(long votes) {
	this.votes = votes;
}
public float getVoteshare() {
	return voteshare;
}
public void setVoteshare(float voteshare) {
	this.voteshare = voteshare;
}
@Override
public String toString() {
	return "Party [" + name + ", Votes=" + votes + ", Voteshare=" + voteshare + "]";
}

}
