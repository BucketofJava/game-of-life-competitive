

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MultiplayerHandler {
    public ServerSocket serverSocket;
    public Socket connectionSocket;
    public InputStream serverInput;
    public OutputStream serverOutput;
    public MultiplayerHandler(String host){
        try{
             serverSocket=new ServerSocket(6969);
             connectionSocket=serverSocket.accept();
             serverInput=connectionSocket.getInputStream();
             serverOutput=connectionSocket.getOutputStream();

        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Cell> getReceivedCells(){
return null;
    }
}
