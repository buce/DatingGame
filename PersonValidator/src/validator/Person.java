package validator;

import java.util.ArrayList;
import java.util.List;

public class Person {
	
	List<Double> weights = new ArrayList<Double>();
	
	public List<Double> getWeights() {
		return weights;
	}
	
	public void setWeights(List<Double> weights) {
		this.weights = weights;
	}
	
	public void addArrtibuteWeight(Double weight){
		this.weights.add(weight);
	}
	
}
