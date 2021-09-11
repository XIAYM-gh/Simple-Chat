package tcp.server;

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
				String help="\nhelp.list=Available Commands:\\nstop\\n - Stop the server normally\\ncreateLang\\n - Recreate or update language file\\nkick <ip>:<port>\\n - Kick someone.";
				String defa="server.startup=Simple Chat Server v1 is starting now..\ndebug.build=## This is a debug build,\\n## If you found a bug,please report it.\nuser.status.connect=Connect\nuser.status.disconnect=Disconnect\ntellall.server.stop=Server is shutting down...\nserver.start.port=Server started on port:\nserver.start.takes=Done in\nconfig.loading=Reading config..\nconfig.not.exist=Config file is not exists! Creating one..\nkick.usage=Usage: kick <ip>:<port>\\nExample: kick /127.0.0.1:23333\ndisconnect.by.server=Disconnected by server:\nserver.kicked=Kicked by server\nstatus.success=Success.\nserver.stop=Stopping server...\nkick.user.notfound=The user was not found!\nuser.join.chat=joined the chat.\nuser.leave.chat=left the chat.\njoin.welcome=Connected to server successfully.\nwelcome.users.online=Current online:\ncmd.used=used command\ncmd.online.getusers=There is %users% users online.\\nOnline users:"+help;
				FileOutputStream fos = new FileOutputStream(global_lang);
				fos.write(defa.getBytes());
				fos.flush();
				fos.close();
			}
			File zh_lang = new File("lang/zh.properties");
			if(!zh_lang.exists()){
				zh_lang.createNewFile();
				String help="\nhelp.list=可用的命令列表:\\nstop\\n - 正常停止服务器\\ncreateLang\\n - 重新创建或更新语言文件\\nkick <ip>:<端口>\\n - 踢出某个用户";
				String defa="server.startup=Simple Chat服务器(v1)正在启动..\ndebug.build=## 服务端处于开发阶段,\\n## 如果发现漏洞请回报.\nuser.status.connect=加入\nuser.status.disconnect=退出\ntellall.server.stop=服务器正在关闭..\nserver.start.port=服务器端口: \nserver.start.takes=服务器启动完成，耗时\nconfig.loading=正在加载配置..\nconfig.not.exist=配置文件不存在! 正在创建..\nkick.usage=使用方式: kick <ip>:<端口>\\n示例: kick /127.0.0.1:23333\ndisconnect.by.server=从服务器断开连接:\nserver.kicked=被服务器踢出.\nstatus.success=成功.\nserver.stop=服务器正在关闭...\nkick.user.notfound=无法找到该用户!\nuser.join.chat=加入了聊天.\nuser.leave.chat=退出了聊天\njoin.welcome=成功加入到服务器.\nwelcome.users.online=目前人数:\ncmd.used=使用了命令\ncmd.online.getusers=目前有 %users% 个用户在线.\\n用户列表:"+help;
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
