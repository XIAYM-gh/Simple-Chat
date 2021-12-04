package tcp.server;
   
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.*;
import tcp.server.dataTypes.*;
import tcp.server.plugin.*;
import org.json.*;

public class UserThread implements Runnable {
      boolean flagg=true;
      public static boolean nerr=false;
       private Socket s;
       private List<Socket> li;
       private ArrayList<SimpleChatPlugin> plugins=null;
       private User user;

       public UserThread(Socket s, List<Socket> li) {
           this.s = s;
           this.li = li;
           this.user= new User(s);
       }

      @Override
       public void run() {
            if(main.getIfPassProtect() == false){
                main.addtouser(s.getInetAddress()+":"+s.getPort()+"logged","true");
            }
            Thread t = new Thread(()->{
                    while(flagg){
                        try{
                            Thread.sleep(200);
                            s.sendUrgentData(0xFF);
                        }catch(Exception e){
                            flagg=false;
                            try{
                            String unck=main.getuser(s.getInetAddress()+":"+s.getPort()+"nick");
                            if(nerr == false && main.hasnick(s)){
                            for (Socket _s : li){
                            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(_s.getOutputStream(),"UTF-8"));
                            main.bc(unck+" "+Lang.get("user.leave.chat"),"info",bw);
                            }
                            }
                            }catch(Exception eee){}
                            main.kill(s);
                        }
                    }
                    });
            t.start();
            boolean flag=true;
            String lox="";
           try {
               BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                       s.getOutputStream(),"UTF-8"));

               BufferedReader br = new BufferedReader(new InputStreamReader(
                       s.getInputStream(),"UTF-8"));

               String line = null;
               while ((line = br.readLine()) != null && flag == true) {
                if(line.startsWith("GET") || line.startsWith("POST") || line.startsWith("HTTP")){
                String Content="<b><i>Warning:</i> This is not a web server, please use simple chat client to connect.</b>";
                String headers="HTTP/1.1 200 OK"+"\n"+"Content-type:text/html;charset=UTF-8"+"\n"+"Content-Length: "+Content.getBytes().length+"\n\n";
                bw.write(headers);
                bw.write(Content);
                bw.flush();
                break;
                }else{
                if(line.equals("SIMPLE_CHAT_CHECK_NEED_PASSWORD")){
                        if(main.getIfPassProtect() == true){
                        bw.write("true");
                        }else{
                        bw.write("false");
                        }
                        bw.newLine();
                        bw.flush();
                }else{
                String uip=s.getInetAddress().toString();
                int uport=s.getPort();
                String uall=uip+":"+uport;
                //main.print(uall+" "+Lang.get("cmd.used")+": "+line);

                if(line.equals("CHAT online")){
                        //String msgx="There is "+li.size()+" users online.\nOnline users: ";
                        String msgx=Lang.get("cmd.online.getusers").replaceAll("%users%",""+li.size());
                    for(Socket si:li){
                        String getunick=main.getuser(si.getInetAddress()+":"+si.getPort()+"nick");
                        msgx=msgx+getunick+" ";
                    }
                    main.bc(msgx, "info", bw);
                }else{
                        
                if(line.startsWith("PASS ")){
                    if(!main.hasnick(s)){
                        File ud = new File("data/"+main.getnick(s)+".properties");
                        if(!ud.exists()){
                        main.print(Lang.get("new.userdata").replaceAll("%user%",main.getnick(s)));
                        String datatext="password="+SHAUtil.SHA256(line.substring(5));
                        FileOutputStream fos = new FileOutputStream(ud);
                        fos.write(datatext.getBytes());
                        fos.flush();
                        fos.close();
                        main.addtouser(s.getInetAddress()+":"+s.getPort()+"logged","true");
                        }else{
                        String pass=SHAUtil.SHA256(line.substring(5));
                        BufferedReader ina = new BufferedReader(new InputStreamReader(new BufferedInputStream(new FileInputStream(new File("data/"+main.getnick(s)+".properties"))),"UTF-8"));
                        Properties prop=new Properties();
                        prop.load(ina);
                        String config_pass=prop.getProperty("password");
                        if(pass.equals(config_pass)){
                            main.addtouser(s.getInetAddress()+":"+s.getPort()+"logged","true");
                        }else{
                            main.bc("Password is not correct.", "kick", bw);
                            main.killsocket(s);
                        }}
                    }else{
                        main.bc("You don't already have a nick!", "kick", bw);
                        main.killsocket(s);
                }
                }else{
                if(line.startsWith("NICK ")){
                    if(line.length() <=7 || line.length() >=18){
                        nerr=true;
                        main.bc("Your nick is too short or too long!", "kick", bw);
                        main.killsocket(s);
                    }else{
                        String[] NickSplit=line.split("NICK ");
                        String unick=NickSplit[1];
                        if(!main.nickisset(unick) && !main.hasnick(s)){
                        main.addtouser(uall+"nick",unick.replaceAll(" ","_"));
                        main.print(Lang.get("user.status.connect")+": "+unick+" ("+s.getInetAddress()+":"+s.getPort()+")");
                        main.bc(Lang.get("join.welcome")+"\n"+Lang.get("welcome.users.online")+li.size(),"info",bw);
                        for (Socket _s : li){
                            BufferedWriter zdw = new BufferedWriter(new OutputStreamWriter(_s.getOutputStream(),"UTF-8"));
                            main.bc(unick+" "+Lang.get("user.join.chat"),"info",zdw);
                        }
                        }else{
                            nerr=true;
                            main.bc("This user is exists!","kick",bw);
                            main.killsocket(s);
                        }
                    }
                }else{
                if(line.startsWith("CHAT ")&&!line.equals("CHAT ")){
                  line=line.substring(5);
                   if(!li.isEmpty() && !line.startsWith("/")) {
                       for (Socket _s : li){
                              String u1all=_s.getInetAddress()+":"+_s.getPort();
                              String u2all=u1all+"nick";
                              String u3all=u1all+"logged";
                              if(main.getuser(u2all) == "" || main.getuser(u2all) == null){
                              BufferedWriter dbw = new BufferedWriter(new OutputStreamWriter(_s.getOutputStream(),"UTF-8"));
                              nerr=true;
                              main.bc("You don't have a nick!","kick",dbw);
                              main.killsocket(_s);
                              }
                              if(!main.getuser(u3all).equals("true") || main.getuser(u3all) == null){
                              BufferedWriter dbw = new BufferedWriter(new OutputStreamWriter(_s.getOutputStream(),"UTF-8"));
                              main.bc("You're not logged in!","kick",dbw);
                              main.killsocket(s);
                              }
                               BufferedWriter _bw = new BufferedWriter(new OutputStreamWriter(_s.getOutputStream(),"UTF-8"));
                               lox=main.getuser(s.getInetAddress()+":"+s.getPort()+"nick");
                              main.bcu(lox,line,"chat",_bw);

                      }
                      plugins=main.getPluginArrayList();
                      Message msg = new Message(user,line);
                      for(SimpleChatPlugin p:plugins){
                              try{
                                    p.onMessage(msg);
                              }catch(Exception er){
                                    er.printStackTrace();
                              }
                      }
                      String u3all=s.getInetAddress()+":"+s.getPort();
                      main.print(u3all+" "+lox+": "+line);
                   }
                }else{
                    main.killsocket(s);
                }}}}}}}

              } catch (Exception e) {
              String err=e.toString();
              if(!err.contains("Socket closed") && !err.contains("Connection reset") && !err.contains("Socket is closed")){
               main.print("** Error: "+e.toString()+" (Auto Closed Connection.)");
              flag=false;
              //main.kill(s);
              }
           }
          } 
}
