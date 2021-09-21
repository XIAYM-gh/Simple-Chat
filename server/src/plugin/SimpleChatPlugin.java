package tcp.server.plugin;
import java.io.*;
import java.net.*;
import java.util.*;
import tcp.server.*;
import tcp.server.dataTypes.*;

abstract class JavaCore{
        /* On plugin enable event */
        public void onEnable(){}
        /* On plugin disable event */
        public void onDisable(){}
        /* On user send message event */

        /* Message:
         * message.getSender() - return a User object
         * message.getMessage() - return the message content(String)
        */
        public void onMessage(Message message){}
        /* On user join event */

        /*
         * User:
         * user.kick(String reason) - kick the user with the reason
         * user.getIP() - returns the user's ip (e.g. /127.0.0.1:12346)
         * user.sendMessage(String msg) - tell the message to the user
         * user.getSocket() - returns a socket object of the user
         * user.getNick() - returns the nickname of the user(String)
        */
        public void onUserConnect(User user){}
        /* On user leave event */

        /*
         * DisconnectedUser:
         * user.getIP() - returns the IP Address of the user
         * user.getNick() - returns the nick name of the user
        */
        public void onUserDisconnect(DisconnectedUser user){}
}

public class SimpleChatPlugin extends JavaCore{
        private String name;
        private String version;
        private String author;
        private ClassLoader classLoader;

        public void setName(String name){
                this.name=name;
        }

        public String getName(){
                return this.name;
        }

        public void setVersion(String ver){
                this.version=ver;
        }
        public String getVersion(){
                return this.version;
        }

        public void setAuthor(String author){
                this.author=author;
        }

        public String getAuthor(){
                return this.author;
        }

        public void setClassLoader(ClassLoader classLoader){
                StackTraceElement[] stack = Thread.currentThread().getStackTrace();
                for(StackTraceElement ste : stack){
                        String className=ste.getClassName();
                        switch(className){
                                case "java.lang.Thread":
                                case "tcp.server.main":
                                case "tcp.server.plugin.SimpleChatPlugin":
                                return;
                        }
                }
                this.classLoader = classLoader;
        }

        public ClassLoader getClassLoader(){
                return classLoader;
        }

        //For Plugin:
        public void info(String text){
                main.print("["+name+"] "+text);
        }
}
