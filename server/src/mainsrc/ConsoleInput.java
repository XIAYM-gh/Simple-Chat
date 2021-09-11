package tcp.server;
  import java.net.*;
  import java.io.*;
  import java.io.BufferedReader;
  import java.io.BufferedWriter;
  import java.io.IOException;
  import java.io.InputStreamReader;
  import java.text.SimpleDateFormat;
  import java.util.Date;
  import org.jline.reader.*;
  import org.jline.terminal.*;

  public class ConsoleInput implements Runnable {

    public ConsoleInput() {
    }

    @Override
    public void run() {
        try {
            //BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
			Terminal terminal = TerminalBuilder.builder().system(true).build();
			LineReader lineReader = LineReaderBuilder.builder().terminal(terminal).build();

            while (true) {
				String line = lineReader.readLine("");
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
				main.print(Lang.get("server.stop"));
                main.stopServer();
				Thread.sleep(1000);
				System.out.print("\r");
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
        } catch (UserInterruptException ue) {

			// Close server
			try{
			main.print(Lang.get("server.stop"));
			main.stopServer();
			Thread.sleep(1000);
			System.out.print("\r");
			System.exit(0);
			}catch(Exception e114514){}

		} catch (EndOfFileException eofe) {

			// Close Server
			try{
			main.print(Lang.get("server.stop"));
			main.stopServer();
			Thread.sleep(1000);
			System.out.print("\r");
			System.exit(0);
			}catch(Exception e1919810){}

		} catch (Exception e){

			e.printStackTrace();

		}

    }
  }


