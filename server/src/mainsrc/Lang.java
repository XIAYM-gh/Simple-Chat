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
                String defa="server.startup=Simple Chat Server v1 is starting now..\n"+
                "debug.build=## This is a debug build,\\n## If you found a bug,please report it.\n"+
                "user.status.connect=Connect\n"+
                "user.status.disconnect=Disconnect\n"+
                "tellall.server.stop=Server is shutting down...\n"+
                "server.start.port=Server started on port:\n"+
                "server.start.takes=Done in\n"+
                "config.loading=Now reading the configuration file....\n"+
                "config.not.exist=The configuration file is not exists! Creating one..\n"+
                "kick.usage=Usage: kick <ip>:<port>\\nExample: kick /127.0.0.1:23333\n"+
                "disconnect.by.server=Disconnected by server:\n"+
                "server.kicked=Kicked by server\n"+
                "status.success=Success.\n"+
                "server.stop=Stopping server...\n"+
                "kick.user.notfound=The user was not found!\n"+
                "user.join.chat=joined the chat.\n"+
                "user.leave.chat=left the chat."+
                "\njoin.welcome=Connected to server successfully.\n"+
                "welcome.users.online=Current online:\n"+
                "cmd.used=used command\n"+
                "cmd.online.getusers=There is %users% users online.\\nOnline users:\n"+
                "new.userdata=Creating new data file for user %user%...\n"+
                "plugin.loading=Loading plugins...\n"+
                "plugin.err.1=Cannot find \"config.ini\" in the plugin!\n"+
                "subplugin.loading=Loading:\n"+
                "plugins.list=Plugins:"+
                help;
                FileOutputStream fos = new FileOutputStream(global_lang);
                fos.write(defa.getBytes());
                fos.flush();
                fos.close();
            }
            File zh_lang = new File("lang/zh.properties");
            if(!zh_lang.exists()){
                zh_lang.createNewFile();
                String help="\nhelp.list=可用的命令列表:\\nstop\\n - 正常停止服务器\\ncreateLang\\n - 重新创建或更新语言文件\\nkick <ip>:<端口>\\n - 踢出某个用户";
                String defa="server.startup=Simple Chat服务器(v1)正在启动..\n"+
                "debug.build=## 服务端处于开发阶段,\\n## 如果发现漏洞请回报.\n"+
                "user.status.connect=加入\n"+
                "user.status.disconnect=退出\n"+
                "tellall.server.stop=服务器正在关闭..\n"+
                "server.start.port=服务器端口: \n"+
                "server.start.takes=服务器启动完成，耗时\n"+
                "config.loading=正在加载配置..\n"+
                "config.not.exist=配置文件不存在! 正在创建..\n"+
                "kick.usage=使用方式: kick <ip>:<端口>\\n示例: kick /127.0.0.1:23333\n"+
                "disconnect.by.server=从服务器断开连接:\n"+
                "server.kicked=被服务器踢出.\n"+
                "status.success=成功.\n"+
                "server.stop=服务器正在关闭...\n"+
                "kick.user.notfound=无法找到该用户!\n"+
                "user.join.chat=加入了聊天.\n"+
                "user.leave.chat=退出了聊天\n"+
                "join.welcome=成功加入到服务器.\n"+
                "welcome.users.online=目前人数:\n"+
                "cmd.used=使用了命令\n"+
                "cmd.online.getusers=目前有 %users% 个用户在线.\\n用户列表:\n"+
                "new.userdata=正在为用户 %user% 创建新的配置文件..\n"+
                "plugin.loading=正在加载插件..\n"+
                "plugin.err.1=无法在插件中找到\"config.ini\"!\n"+
                "subplugin.loading=正在加载:\n"+
                "plugins.list=插件列表:"+
                help;
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
