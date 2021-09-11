package tcp.client;
import java.net.*;
import java.io.*;
import java.util.Scanner;
import tcp.client.*;
import java.nio.charset.*;

public class main {

	public static boolean flag = false;
	public static BufferedWriter bw=null;
    public static void main(String[] args) {

		Console cons = System.console();

		//System.out.println(Charset.defaultCharset().name());
		//↑ It's only for debugging
		Lang.prepare();

		String nick="ERROR";
		System.out.println("Simple Chat Client - v1");
		String nickc = cons.readLine(Lang.get("startup.nick.enter"));
		if(nickc != null && nickc != "" && nickc.length() >= 3){
			nick=nickc;
		}else{
			nick="ERROR";
			System.out.println(Lang.get("nick.invalid"));
			System.exit(-1);
		}

		String servip="127.0.0.1:12345";
		String ipadd="127.0.0.1";
		int port=12345;
		servip=cons.readLine(Lang.get("startup.ip.enter"));
			 if(!servip.contains(":")){
					 ipadd=servip;
					 port=12345;
			 }else{
					 String[] ipaddress=servip.split(":");
					 ipadd=ipaddress[0];
					 port=Integer.parseInt(ipaddress[1]);
			 }

		Socket s=null;

		try{
        s = new Socket(ipadd,port);
		}catch(Exception e){
			System.out.println(Lang.get("connect.failed"));
			System.out.println(e.toString());
			System.exit(-2);
		}

		try{
        BufferedReader br = new BufferedReader(new InputStreamReader(
                s.getInputStream(),"UTF-8"));
        bw = new BufferedWriter(new OutputStreamWriter(
                s.getOutputStream(),"UTF-8"));

		//System.out.println("开始查询服务器验证，如果长时间卡在这一步请重启客户端!");
		print(Lang.get("process.check_verify"));
		bw.write("SIMPLE_CHAT_CHECK_NEED_PASSWORD");
		bw.newLine();
		bw.flush();
		String ababab=null;
		String status_pass=null;
		while ((ababab = br.readLine()) != null) {
				status_pass=ababab;
				break;
		}
		if(status_pass.equals("true")){
				//System.out.println("服务器需要身份验证，请输入您的密码来注册/登录");
				print(Lang.get("verify.password_required"));
				char[] upc = cons.readPassword(Lang.get("startup.password.enter"));
				String upa = String.valueOf(upc);
				if(upa.length() > 3){
					bw.write("NICK "+nick);
					bw.newLine();
					bw.flush();
					bw.write("PASS "+upa);
					bw.newLine();
					bw.flush();
				}else{
					System.out.println(Lang.get("password.too_short"));
					System.exit(-4);
				}
		}else{
				System.out.println(Lang.get("verify.not_required"));
				bw.write("NICK "+nick);
				bw.newLine();
				bw.flush();
		}

		System.out.print("> ");

        SeThread st = new SeThread(bw,s);
        ReThread rt = new ReThread(br);
		HeartBeat hb = new HeartBeat(s);

        new Thread(new SeThread(bw,s)).start();
        new Thread(new ReThread(br)).start();
		new Thread(new HeartBeat(s)).start();
        }catch(Exception e){
		}
    }

	public static void print(String str){
		System.out.println(str);
	}
}
