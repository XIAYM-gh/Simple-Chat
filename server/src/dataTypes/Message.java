package tcp.server.dataTypes;
import java.net.*;
import java.io.*;
import java.util.*;
import tcp.server.*;

public class Message{
        private User sender=null;
        private String message="";

        /* Usage: Message message = new Message(USER, Message String); */
        /* Author: XIAYM */

        public Message(User u, String messageContent){
                this.sender=u;
                this.message=messageContent;
        }

        /* Get the sender */
        public User getSender(){
                return sender;
        }

        /* Get the message */
        public String getMessage(){
                return message;
        }

}
