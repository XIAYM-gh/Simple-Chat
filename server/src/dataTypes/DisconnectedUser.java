package tcp.server.dataTypes;
import java.net.*;
import java.io.*;
import java.util.*;
import tcp.server.*;

public class DisconnectedUser{
        private String userNick="";
        private String userIP="";

        /* Usage:DisconnectedUser user = new DisconnectedUser(NICK,IP); */
        /* Author: XIAYM */

        public DisconnectedUser(String unick, String uip){
                this.userNick=unick;
                this.userIP=uip;
        }


        /* Get the user's ip and port */
        public String getIP(){
                return userIP;
        }

        /* Get the user's nickname */
        public String getNick(){
                return userNick;
        }

}
