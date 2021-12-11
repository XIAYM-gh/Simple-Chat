package tcp.client;
import java.net.*;
import java.io.*;

  public class HeartBeat implements Runnable {

    private Socket s;
     
    public HeartBeat(Socket s) {
        this.s = s;
    }


    @Override public void run() {
        boolean flag=true;
        while(flag){
        try{
            Thread.sleep(200);
            s.sendUrgentData(0xFF);
        } catch (Exception e) {
            flag=false;
            main.print(Lang.get("connection.lost"),System.currentTimeMillis());
            System.exit(0);
        }
        }

    }
  }


