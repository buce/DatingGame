package server;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;


public class ValidateP {
	
	public static void validatemain(int noOfAttributes,String fileName,Person person) {

		
		
		Pattern pattern = Pattern.compile("^(-)?\\d{1}(\\.\\d{0,2})?$");
		double positiveAttrSum = 0.0;
		double negativeAttrSum = 0.0;
		int attributeCounter = 0;

		try 
		{
			FileReader reader = new FileReader(fileName);
			Scanner sc = new Scanner(reader);
			while(sc.hasNext())
			{
				String str = sc.next();
				if(str.contains("weights"))
					break;
			}
			while(sc.hasNext())
			{	
				attributeCounter++;
				String weightString = sc.next().trim();
				
				if(!pattern.matcher(weightString).matches()){
					throw new InputMismatchException("Input file weight "+ weightString + " do not match the required pattern.");
				}
				Double weight = Double.parseDouble(weightString);
				
				assert(weight <=1 && weight>= -1) : "Provided weighs do not lie in range [-1, 1]";
				
				if(weight>0)
					positiveAttrSum = positiveAttrSum + weight;
				else
					negativeAttrSum = negativeAttrSum + weight;
					
				person.addArrtibuteWeight(weight);
			}
			assert(attributeCounter == noOfAttributes) : "Number of attribtes provided do not match the required number.";
			
			DecimalFormat twoDecimalFormat = new DecimalFormat("#.##");
			
			assert(Double.valueOf(twoDecimalFormat.format(positiveAttrSum)) == 1) : "Sum of positive weights is not 1 but " + positiveAttrSum;
			assert(Double.valueOf(twoDecimalFormat.format(negativeAttrSum)) == -1) : "Sum of negative weights is not -1 but " + negativeAttrSum;
		}
		catch (NumberFormatException e) 
		{
			System.out.println("Unable to parse the weights provided.\n" + e.getMessage());
		}
		catch (FileNotFoundException e) 
		{
			                 System.out.println("File Not Found Exception");
		}
		
		System.out.println("Input file validated!!!");
	}

}