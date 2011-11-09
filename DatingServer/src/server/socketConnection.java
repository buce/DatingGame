/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Buce
 */
public class socketConnection {
    private ServerSocket myServer = null;
    private Socket myClient = null;
    DataInputStream input = null;
    PrintStream output = null;
    
    public socketConnection(String sMachineName,int iPort) throws IOException {
      try {
    myServer = new ServerSocket(iPort);
    } catch (IOException e) {
         System.out.println("Could not listen on port: "+iPort);
        System.exit(-1);
    }
    try {
    myClient = myServer.accept();
    } catch (IOException e) {
         System.out.println("Accept failed: "+iPort);
    }    
  

       output = new PrintStream(myClient.getOutputStream());
       input = new DataInputStream(myClient.getInputStream());
        
    }
    
    public String socketRead(){
        try {
            String inputLine=" ";
            char ch;
            while(true){
            ch = (char)input.read();
            if(ch==']'){
                inputLine = inputLine + ch;
                break;
            }else{
                inputLine = inputLine + ch;
            }
            }
            
            return inputLine.trim();
            
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
