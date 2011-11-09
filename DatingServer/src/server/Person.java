package server;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Person {
	
	List<Double> weights = new ArrayList<Double>();
	
	public Person() {
		// TODO Auto-generated constructor stub
	}
	
	public List<Double> getWeights() {
		return weights;
	}
	
	public void setWeights(List<Double> weights) {
		this.weights = weights;
	}
	
	public void addArrtibuteWeight(Double weight){
		this.weights.add(weight);
	}
	
	//TODO: validate everything
	public Person(String weightFile){
		try 
		{
			FileReader reader = new FileReader(weightFile);
			Scanner scanner = new Scanner(reader);
			scanner.next();
			while(scanner.hasNext())
			{
				String next = scanner.next();
				if(!next.contains("weights"))
					break;
			}
			while(scanner.hasNext())
			{	
				Double weight = Double.parseDouble(scanner.next());
				this.addArrtibuteWeight(weight);
			}
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
}
