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
		String nick="ERROR";
		System.out.println("L O G I N ------");
		String nickc = cons.readLine("输入昵称: ");
		if(nickc != null && nickc != "" && nickc != " "){
			nick=nickc;
		}else{
			nick="ERROR";
			System.out.println("您的昵称非法!");
			System.exit(-1);
		}

		String servip="127.0.0.1:12345";
		String ipadd="127.0.0.1";
		int port=12345;
		//System.out.println("请输入服务器ip:");
		servip=cons.readLine("服务器ip: ");
			 if(!servip.contains(":")){
					 System.out.println("请带端口号!");
					 System.exit(-1);
			 }else{
					 String[] ipaddress=servip.split(":");
					 ipadd=ipaddress[0];
					 port=Integer.parseInt(ipaddress[1]);
			 }

		Socket s=null;

        //创建Socket对象，指定ip和端口
		try{
        s = new Socket(ipadd,port);
		}catch(Exception e){
			System.out.println("无法连接到服务器:");
			System.out.println(e.toString());
			System.exit(-2);
		}

		System.out.println("Simple Chat 客户端已启动, 输入 \"stop\" 关闭客户端");

        //包装输入输出流，用作参数传递
		try{
        BufferedReader br = new BufferedReader(new InputStreamReader(
                s.getInputStream(),"UTF-8"));
        bw = new BufferedWriter(new OutputStreamWriter(
                s.getOutputStream(),"UTF-8"));

		System.out.println("开始查询服务器验证，如果长时间卡在这一步请重启客户端!");
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
				System.out.println("服务器需要身份验证，请输入您的密码来注册/登录");
				char[] upc = cons.readPassword("密码: ");
				String upa = String.valueOf(upc);
				if(upa.length() > 3){
					bw.write("NICK "+nick);
					bw.newLine();
					bw.flush();
					bw.write("PASS "+upa);
					bw.newLine();
					bw.flush();
				}else{
					System.out.println("错误: 您的密码长度过短");
					System.exit(-4);
				}
		}

        SeThread st = new SeThread(bw,s);    //发送线程类
        ReThread rt = new ReThread(br);    //接收线程类
		HeartBeat hb = new HeartBeat(s);


        //启动发送接收线程
        new Thread(new SeThread(bw,s)).start();
        new Thread(new ReThread(br)).start();
		new Thread(new HeartBeat(s)).start();
        }catch(Exception e){
		}
    }

}
