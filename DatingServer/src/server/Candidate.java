package server;

import java.util.ArrayList;
import java.util.List;

public class Candidate {

	double score;
	private List<Double> values = new ArrayList<Double>();

    public Candidate() {
    }
    
    public Candidate(List<Double> values, double score) {
        this.values = values;
        this.score = score;
    }
	
        
        
	public double getScore() {
		return score;
	}
	
	public void setScore(double score) {
		this.score = score;
	}
	
	public void setValues(List<Double> values) {
		this.values = values;
	}

	public List<Double> getValues() {
		return values;
	}
	
}
