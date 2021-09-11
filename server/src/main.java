package tcp.server;
import java.net.*;
import java.util.*;
import java.io.*;
import java.text.*;
import org.json.JSONObject;

public class main {

	public static List<Socket> li = Collections.synchronizedList(new ArrayList<Socket>());
	public static JSONObject json = new JSONObject();
	public static Thread uth = null;
	public static int serverPort=0;
	public static String passProtect=null;

    public static void main(String[] args) throws IOException{
		long pstart=System.currentTimeMillis();
		Lang.prepare();
		//print("Simple Chat Server v1 is starting now..");
		print(Lang.get("server.startup"));
		print(Lang.get("debug.build"));
		//print("## This is a debug build.");
		//print("## If you found a bug, please report it.");
		ConsoleInput ci = new ConsoleInput();
		new Thread(ci).start();
		File userdatadir = new File("data/");
		if(!userdatadir.exists()){
			userdatadir.mkdir();
		}
		File configfile = new File("server.properties");
		if(!configfile.exists()){
			print(Lang.get("config.not.exist"));
			configfile.createNewFile();
			String defa="server_port=12345\npassword_protect=true";
			FileOutputStream fos = new FileOutputStream(configfile);
			fos.write(defa.getBytes());
			fos.flush();
			fos.close();
		}
		print(Lang.get("config.loading"));
		try{
			BufferedReader ina = new BufferedReader(new InputStreamReader(new BufferedInputStream(new FileInputStream(new File("server.properties"))),"UTF-8"));
			Properties prop = new Properties();
			prop.load(ina);
			serverPort=Integer.parseInt(prop.getProperty("server_port","12345"));
			passProtect=prop.getProperty("password_protect","true");
		}catch(Exception e){
			e.printStackTrace();
		}
		long pend=System.currentTimeMillis();
		long takes=pend-pstart;
		print(Lang.get("server.start.port")+serverPort);
		print(Lang.get("server.start.takes")+" "+takes+"ms");

        while(true){
               try (ServerSocket ss = new ServerSocket(serverPort)) {
                   Socket s = ss.accept();
                   li.add(s);

                   uth=new Thread(new UserThread(s,li));
				   uth.start();

               }catch (Exception e){
                   e.printStackTrace();
				   System.exit(-1);
               }

           }

       }
	   public static void print(String str){
			  String[] strsp=str.split("\n");
			  for(String str_:strsp){
				  Date dNow = new Date(System.currentTimeMillis());
				  SimpleDateFormat flast = new SimpleDateFormat("HH:mm:ss");
				  System.out.println("["+flast.format(dNow)+"] "+str_);
			  }
	   }
	   public static void addtouser(String user,String addvalue){
			  json.put(user,addvalue);
	   }
	   public static void deluser(String user){
			  json.put(user,"");
	   }
	   public static String getnick(Socket s){
			   return getuser(s.getInetAddress()+":"+s.getPort()+"nick");
	   }
	   public static String getuser(String user){
			  String rt;
			  try{
					  rt=json.getString(user);
			  }catch(Exception e){
					  rt="";
			  }
			  return rt;
	   }
	   public static boolean nickisset(String nick){
			  for(Object itm:json.keySet()){
					  String a=json.getString(itm.toString());
					  if (a.equals(nick)){
							 return true;
					  }
			  }
	   return false;
	   }
	   public static boolean hasnick(Socket _s){
			   String uall=_s.getInetAddress()+":"+_s.getPort();
			   if(nickisset(uall+"nick")){
					  return true;
			   }
			   return false;
	   }
	   public static void kill(Socket s){
	   try{
	   if(getuser(s.getInetAddress()+":"+s.getPort()+"nick").length() > 0){
	   print(Lang.get("user.status.disconnect")+": "+getnick(s)+" ("+s.getInetAddress()+":"+s.getPort()+")");
	   }
	   deluser(s.getInetAddress()+":"+s.getPort()+"nick");
	   deluser(s.getInetAddress()+":"+s.getPort()+"logged");
	   if(li.size() == 1){
			  li.removeAll(li);
	   }else{
			  li.remove(s);
	   }
	   }catch(Exception e){
			  e.printStackTrace();
			  }
	   }
	   public static void tellAll(String msg){
			try{
	   		for (Socket _s : li){
				BufferedWriter tbw = new BufferedWriter(new OutputStreamWriter(_s.getOutputStream(),"UTF-8"));
				tbw.write(msg);
				tbw.newLine();
				tbw.flush();
			}
			}catch(Exception e){}
	   }
	   public static void stopServer(){
			tellAll(Lang.get("tellall.server.stop"));
			for (Socket _s : li){
				try{
				_s.close();
				}catch(Exception e){}
			}
	   }
	   public static Socket getUserSocket(String ual){
			Socket back=null;
			for (Socket _s : li){
				String uall=_s.getInetAddress()+":"+_s.getPort();
				if(uall.equals(ual)){
					back=_s;
				}
			}
			return back;
	   }
	   public static boolean getIfPassProtect(){
			boolean ifp=false;
			if(passProtect.equals("true")){
				ifp=true;
			}
			return ifp;
	   }
}

