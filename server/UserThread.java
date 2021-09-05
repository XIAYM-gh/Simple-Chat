package tcp.server;
/**
 * 服务器多线程实现，用来提高效率
  */
   
   import java.io.*;
   import java.net.Socket;
   import java.text.SimpleDateFormat;
   import java.util.ArrayList;
   import java.util.Collections;
   import java.util.Date;
   import java.util.List;

   public class UserThread implements Runnable {
	   boolean flagg=true;
	   public static boolean nerr=false;
       private Socket s;
       private List<Socket> li;

       public UserThread(Socket s, List<Socket> li) {
           this.s = s;
           this.li = li;
       }

       @Override
       public void run() {
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
							BufferedWriter zdwk = new BufferedWriter(new OutputStreamWriter(_s.getOutputStream(),"UTF-8"));
							zdwk.write(unck+" "+Lang.get("user.leave.chat"));
							zdwk.newLine();
							zdwk.flush();
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

               //收到消息之后发送给全部客户端
               BufferedReader br = new BufferedReader(new InputStreamReader(
                       s.getInputStream(),"UTF-8"));

               String line = null;
               while ((line = br.readLine()) != null && flag == true) {
				if(line.startsWith("GET") || line.startsWith("POST") || line.startsWith("HTTP")){
				String Content="<b><i>Warning:</i> This is not a web server, please use simple chat client to connect.</b>";
				String headers="HTTP/1.1 200 OK"+"\n"+"Server: SimpleChat server"+"\n"+"Date: 1919-8-10 11:45:14"+"\n"+"Content-type:text/html;charset=UTF-8"+"\n"+"Content-Length: "+Content.length()+"\n\n";
				bw.write(headers);
				bw.write(Content);
				bw.flush();
				break;
				}else{
				if(line.equals("SIMPLE_CHAT_CHECK_NEED_PASSWORD")){
						bw.write("true");
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
					bw.write(msgx);
					bw.newLine();
					bw.flush();
				}else{


				if(line.startsWith("NICK ")){
					if(line.length() <=7 || line.length() >=18){
						nerr=true;
						//Kicked print
						bw.write("Disconnected by server:\nYour Nick is too short or too long!");
						bw.newLine();
						bw.flush();
						s.close();
					}else{
						String[] NickSplit=line.split("NICK ");
						String unick=NickSplit[1];
						if(!main.nickisset(unick) && !main.hasnick(s)){
						main.addtouser(uall+"nick",unick);
						main.print(Lang.get("user.status.connect")+": "+unick+" ("+s.getInetAddress()+":"+s.getPort()+")");
						bw.write(Lang.get("join.welcome")+"\n"+Lang.get("welcome.users.online")+li.size());
						bw.newLine();
						bw.write("Welcome, "+unick+"!");
						bw.newLine();
						bw.flush();
						for (Socket _s : li){
							BufferedWriter zdw = new BufferedWriter(new OutputStreamWriter(_s.getOutputStream(),"UTF-8"));
							zdw.write(unick+" "+Lang.get("user.join.chat"));
							zdw.newLine();
							zdw.flush();
						}
						}else{
							nerr=true;
							//main.print("*Kicked by server: Your nick is already exists!");
							bw.write("Disconnected by server:\nYour nick is already exists!");
							bw.newLine();
							bw.flush();
							s.close();
						}
					}
				}else{
				if(line.startsWith("CHAT ")&&!line.equals("CHAT ")){
				   line=line.split("CHAT ")[1];
                   if(!li.isEmpty()) {
                       for (Socket _s : li){
							   String u1all=_s.getInetAddress()+":"+_s.getPort();
							   String u2all=u1all+"nick";
							   if(main.getuser(u2all) == "" || main.getuser(u2all) == null){
							   BufferedWriter dbw = new BufferedWriter(new OutputStreamWriter(_s.getOutputStream(),"UTF-8"));
							   nerr=true;
							   main.print("*Kicked by server: You don't have a nick!");
							   dbw.write("Disconnected by server:\nYou don't have a nick!");
							   dbw.newLine();
							   dbw.flush();
							   _s.close();
							   }
                               BufferedWriter _bw = new BufferedWriter(new OutputStreamWriter(
                                       _s.getOutputStream(),"UTF-8"));
                               lox="<"+main.getuser(s.getInetAddress()+":"+s.getPort()+"nick")+ ">" + " " + line;
							   _bw.write(lox);
                               _bw.newLine();
                               _bw.flush();

                       }
					   String u3all=s.getInetAddress()+":"+s.getPort();
					   main.print(u3all+" "+lox);
                   }
				}else{
					s.close();
				}}}}}}
          	} catch (Exception e) {
			   String err=e.toString();
			   if(!err.contains("Socket closed") && !err.contains("Connection reset")){
               System.out.println("** Error: "+e.toString()+"\n** Auto Closed Connection.");
			   flag=false;
			   //main.kill(s);
			   }
           }
		   }
}
