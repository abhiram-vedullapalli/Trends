package trends;

public class Party {
private String name;
private long votes;
private float voteshare;
private long seats;
public Party(String name, long votes,float voteshare) {
	this.name = name;
	this.votes = votes;
	this.voteshare =voteshare;
}
public Party(String name , long seats) {
	this.name = name;
	this.seats = seats;
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
public long getSeats() {
	return seats;
}
public void setSeats(long seats) {
	this.seats = seats;
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

//For printing number of Seats in Loksabha in Home Page
public String printingSeats() {
	return "Party : " + name + " Seats " + seats ;
}

}
