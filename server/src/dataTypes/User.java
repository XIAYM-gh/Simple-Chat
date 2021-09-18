package tcp.server.dataTypes;
import java.net.*;
import java.io.*;
import java.util.*;
import tcp.server.*;

public class User{
        private Socket s=null;
        private String userNick="";
        private String userIP="";
        private BufferedWriter bw = null;

        /* Usage: User user = new User(SOCKET); */
        /* Author: XIAYM */

        public User(Socket s){
                try{
                this.s=s;
                this.userNick=main.getnick(s);
                this.userIP=s.getInetAddress()+":"+s.getPort();
                this.bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream(),"UTF-8"));
                }catch(Exception e){
                    e.printStackTrace();
                }
        }

        /* Kick the user */
        public void kick(String reason){
                try{
                bw.write("disconnect "+reason);
                bw.newLine();
                bw.flush();
                main.kill(s);
                }catch(Exception e){
                    e.printStackTrace();
                }
        }

        /* Get the user's ip and port */
        public String getIP(){
                return userIP;
        }

        /* Get the user's nickname */
        public String getNick(){
                return userNick;
        }

        /* Get the socket */
        public Socket getSocket(){
                return s;
        }

        /* Send a message to the user */
        public void sendMessage(String message){
                try{
                bw.write(message);
                bw.newLine();
                bw.flush();
                }catch(Exception e){
                    e.printStackTrace();
                }
        }

}
