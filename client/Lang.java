package tcp.client;

import java.io.*;
import java.util.*;
import java.nio.*;

public class Lang{
		public static Locale locale = Locale.getDefault();
		public static String langcode = locale.getLanguage();
		public static void prepare(){
			try{
			File langfolder = new File("lang/");
			if(!langfolder.exists()){
				langfolder.mkdir();
			}
			File global_lang = new File("lang/default.properties");
			if(!global_lang.exists()){
				global_lang.createNewFile();
				String defa="startup.nick.enter=Enter your nick:\n"+
				"nick.invalid=Your nick is invalid!\n"+
				"startup.ip.enter=Server IP Address:\n"+
				"connect.failed=Can't connect to the server.\n"+
				"process.check_verify=Now querying the verification status of the server..\n"+
				"verify.password_required=The server requires password to sign in/up\n"+
				"startup.password.enter=Password:\n"+
				"password.too_short=Your password is too short!\n"+
				"verify.not_required=The server doesn't require verify, now joining..\n"+
				"connection.lost=Connection lost..";
				FileOutputStream fos = new FileOutputStream(global_lang);
				fos.write(defa.getBytes());
				fos.flush();
				fos.close();
			}
			File zh_lang = new File("lang/zh.properties");
			if(!zh_lang.exists()){
				zh_lang.createNewFile();
				String defa="startup.nick.enter=输入昵称:\n"+
				"nick.invalid=您的昵称非法!\n"+
				"startup.ip.enter=服务器ip:\n"+
				"connect.failed=无法连接到服务器.\n"+
				"process.check_verify=正在查询服务器验证状态...\n"+
				"verify.password_required=服务器需要身份验证，请输入密码来登录/注册\n"+
				"startup.password.enter=密码:\n"+
				"password.too_short=您的密码长度太短!\n"+
				"verify.not_required=服务器不需要验证，正在加入..\n"+
				"connection.lost=连接已丢失..";
				FileOutputStream fos = new FileOutputStream(zh_lang);
				fos.write(defa.getBytes());
				fos.flush();
				fos.close();
			}
			}catch(Exception e){
				main.print("Language Error: "+e.toString());
				System.exit(-3);
			}
		}

		public static String get(String key){
			String returns=null;
			try{
				String lolol=null;
				File testfile = new File("lang/"+langcode+".properties");
				if(testfile.exists()){
					lolol=langcode;
				}else{
					lolol="default";
				}
				BufferedReader ina = new BufferedReader(new InputStreamReader(new BufferedInputStream(new FileInputStream(new File("lang/"+lolol+".properties"))),"UTF-8"));
				Properties prop = new Properties();
				prop.load(ina);
				returns=prop.getProperty(key,"\""+key+"\" was not found in "+lolol+".properties, please run command \"createLang\" to update your language.");
			}catch(Exception e){}
			return returns;
		}
}
