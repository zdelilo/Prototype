import java.io.*;
import java.net.*;


class ClientHandler extends Thread{

    BufferedReader brIn;
    PrintWriter pwOut;
    MyVoteServer server;
    Socket socket;
    
    ClientHandler(Socket sock, MyVoteServer serv){
        try{
            brIn = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            pwOut = new PrintWriter(sock.getOutputStream(),true);
            server = serv;
            socket = sock;
        }catch(Exception e){
            System.out.println("Error initiating ClientHandler");
        }
    }
}