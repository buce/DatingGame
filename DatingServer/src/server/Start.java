package server;

import java.io.IOException;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Start {
	
	public static int port;
	public static String machineName;
	public static int noOfArrtributes;
	public static String weightFile;
	public static ArrayList<Candidate> candidates;
	public static Person person;
        public static socketConnection oSocket = null;
        public static Double maximumScore = -2.0;
        public static List<Double> BestCandidate = new ArrayList<Double>();
        public static ArrayList<Candidate> MatchMakerList = new ArrayList<Candidate>();
	
	public static void main(String[] args) throws UnknownHostException, IOException {
            machineName = args[0];
            port = Integer.parseInt(args[1]);
            noOfArrtributes = Integer.parseInt(args[2]);
            weightFile = args[3];
            
            person = new Person();
            ValidateP oV = new ValidateP();
            
            oV.validatemain(noOfArrtributes, weightFile, person);

            candidates = new ArrayList<Candidate>();
            
            
            generateRandomCandidates(noOfArrtributes);
            String out = null;
            out = "candidates:\n";
            for(int i=0;i<20;i++){
                out = out + candidates.get(i).getValues()+":["+candidates.get(i).score+"]\n";
            }
            out = out + "give candidate1:\n";
            oSocket = new socketConnection(machineName, port);
            oSocket.socketWrite(out);
            System.out.println(out);
           long usertime = 0;
           long starttime = System.currentTimeMillis()/1000;
           long endtime = 0;
            for(int i=0;i<20;i++){
               
                String input = oSocket.socketRead();
                System.out.println(input);
                
                // To check User usage time
                endtime = System.currentTimeMillis()/1000;
                usertime = usertime + (endtime-starttime);
                
                if(usertime>120){
                    oSocket.socketWrite("Sorry you have exceeded the time limit 2mins "+usertime +"s\nBye!!!\n");
                    System.out.println("Sorry you have exceeded the time limit 2mins "+usertime +"s\n Bye!!!\n");
                    System.exit(1);
                    
                }
                
                List<Double> oC = parseCandidate(input);
                Double score = getScores(oC);
                if(score==1.0){
                    // Ideal Candidate
                    BestCandidate = oC;
                    MatchMakerList.add(new Candidate(oC,score));
                    String feedback = oC+":["+score+"]\n";
                    feedback = feedback + "Bingo!!! You found your ideal candidate in "+(i+1)+" moves Bye!!!\n";
                    System.out.println(feedback);
                    oSocket.socketWrite(feedback);
                    System.out.println("Best Candidate:");
                    System.out.println(BestCandidate); 
                    break;
                }
                if(score>maximumScore){
                    BestCandidate = oC;
                    maximumScore = score;
                }
                
                MatchMakerList.add(new Candidate(oC,score));
                if(MatchMakerList.size()==20){
                    //exhausted
                     System.out.println("candidate"+(i+1));
                    String feedback = oC+":["+score+"]\n";
                     feedback = feedback + "You have exhausted 20 candidates your best score:"+maximumScore+" Bye!!!\n";
                    System.out.println(feedback);
                    oSocket.socketWrite(feedback);
                    System.out.println("Best Candidate:");
                    System.out.println(BestCandidate);
                    break;
                }
                System.out.println("candidate"+(i+1)+" score");
                String feedback = oC+":["+score+"]\n";
                feedback = feedback + "give candidate"+(i+2)+":\n";
                System.out.println(feedback);
                oSocket.socketWrite(feedback);
                starttime= System.currentTimeMillis()/1000;
            }
        
	}

        
        private static Double getScores(List<Double> oC){
            Double score = 0.0;
            DecimalFormat fourDecimalFormat = new DecimalFormat("#.####");
            for(int i=0;i<noOfArrtributes;i++){
                score = score + ((oC.get(i))*(person.weights.get(i)));
            }
            return Double.valueOf(fourDecimalFormat.format(score));
        } 
        
        private static List<Double> parseCandidate(String Candidate){
            String[] list = Candidate.substring(1, Candidate.length()-1).split(",");
            assert(list.length==noOfArrtributes) : "Number of values passed for candidate is not equal to N but " + list.length+"\nCandidate "+Candidate;
            
            DecimalFormat fourDecimalFormat = new DecimalFormat("#.####");
            List<Double> oC = new ArrayList<Double>();
            
            for(int i=0;i<noOfArrtributes;i++){
                assert((Double.valueOf(fourDecimalFormat.format(Double.valueOf(list[i]))))>=0) : "Candidate value is less than 0!!!\nCandidate "+Candidate;
                assert((Double.valueOf(fourDecimalFormat.format(Double.valueOf(list[i]))))<=1) : "Candidate value is greater than 1!!!\nCandidate "+Candidate;
                oC.add(Double.valueOf(fourDecimalFormat.format(Double.valueOf(list[i]))));
            }
            return oC;
        }
        
       
        
	private static void generateRandomCandidates(int noOfArrtributes) {
		
		Random doubleGenerator = new Random();
		Double score;
		DecimalFormat twoDecimalFormat = new DecimalFormat("#.####");
		for(int i=0; i<20; i++){
                        candidates.add(new Candidate());
			score= 0d;
                        List<Double> oL = new ArrayList<Double>();
			for(int j=0; j<noOfArrtributes; j++){
                        Double doubleValue ;    
                        
                                while(true){
                                    doubleValue = doubleGenerator.nextDouble();
                               if(doubleValue==0.0){
                                    continue;    
                                }
                                    break;
                               }
                                doubleValue = Double.valueOf(twoDecimalFormat.format(doubleValue));
                                oL.add(doubleValue);
				score = score + doubleValue * person.getWeights().get(j);
			}
                        candidates.get(i).setValues(oL);
                        candidates.get(i).setScore(Double.valueOf(twoDecimalFormat.format(score)));
		}
	}
}
