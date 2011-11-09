package datingclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
/**
 *
 * @author Buce
 */
public class socketConnection {
    private Socket myClient = null;
    BufferedReader input = null;
    PrintStream output = null;

    public socketConnection(String sMachineName,int iPort) throws IOException {
      
            myClient = new Socket(sMachineName,iPort);
            System.out.println("Connected to "+sMachineName+ " at port "+iPort);
      
       output = new PrintStream(myClient.getOutputStream());
       input = new BufferedReader(new InputStreamReader(myClient.getInputStream()));
        
    }
    
    public String socketRead(){
        try {
            return input.readLine().trim();
        } catch (IOException ex) {
            System.out.println("Socket Read Problem");
        }
        return null;
    }
    
    public void socketWrite(String out){
        try {
            output.write(out.getBytes());
        } catch (IOException ex) {
            System.out.println("Socket Write Problem");
        }
    }
    
    
    
}
