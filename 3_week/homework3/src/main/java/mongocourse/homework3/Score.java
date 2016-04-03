package mongocourse.homework3;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class Score {
	
	private String type;
	private double score;

	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public double getScore() {
		return score;
	}
	
	public void setScore(double score) {
		this.score = score;
	}
	
	@Override
	public String toString() {
		return "Score [type=" + type + ", score=" + score + "]";
	}
}
