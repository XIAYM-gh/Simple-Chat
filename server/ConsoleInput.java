package tcp.server;
  import java.net.*;
  import java.io.*;
  import java.io.BufferedReader;
  import java.io.BufferedWriter;
  import java.io.IOException;
  import java.io.InputStreamReader;
  import java.text.SimpleDateFormat;
  import java.util.Date;

  public class ConsoleInput implements Runnable {     //实现Runnable接口
  //带参构造，传入输出流对象
    public ConsoleInput() {
    }

    @Override
    public void run() {
        try {
            BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));

            String line = null;      //初始化line
            while ((line = buf.readLine()) != null) {
				if (line.equals("help")){
				//main.print("Available Commands:\nstop\n - Stop the server normally.");
				main.print(Lang.get("help.list"));
				}else{
				if(line.equals("createLang")){
					main.print("Resetting language files...");
					File dir = new File("lang/");
					File[] listFiles = dir.listFiles();
					for(File file : listFiles){
						file.delete();
					}
					Lang.prepare();
				}else{
				if(line.startsWith("kick")){
					if(line.equals("kick") && line.length()<=5){
						//main.print("Usage: kick <ip>:<port>\nExample: kick /127.0.0.1:23333");
						main.print(Lang.get("kick.usage"));
					}else{
						String uipp=line.substring(5);
						Socket usock=main.getUserSocket(uipp);
						if(usock!=null){
							try{
							BufferedWriter tk = new BufferedWriter(new OutputStreamWriter(usock.getOutputStream(),"UTF-8"));
							tk.write(Lang.get("disconnect.by.server")+"\n"+Lang.get("server.kicked"));
							tk.newLine();
							tk.flush();
							usock.close();
							}catch(Exception e){}
							main.print(Lang.get("status.success"));
						}else{
							main.print(Lang.get("kick.user.notfound"));
						}
					}
				}else{
                if ("stop".equals(line)) {
				buf.close();
				main.print(Lang.get("server.stop"));
                main.stopServer();
				Thread.sleep(1000);
				System.exit(0);
                }else{
					if(line.length() > 0){
					main.tellAll("[Server] "+line);
					main.print("[Server] "+line);
					}else{
					System.out.print("");
					}
				}
				}
				}
				}


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
  }


