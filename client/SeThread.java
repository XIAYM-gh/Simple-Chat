package tcp.client;
import java.net.*;
import java.io.*;
import java.util.*;
import org.jline.reader.*;
import org.jline.terminal.*;

  public class SeThread implements Runnable {

    private BufferedWriter bw;     
	private Socket s;
      
    public SeThread(BufferedWriter bw,Socket s) {
        this.bw = bw;
		this.s = s;
    }

    @Override public void run() {
        try {
            //Console cons = System.console();
			//BufferedReader buf = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(cons.readLine().getBytes("UTF-8")),"UTF-8"));
		Terminal terminal = TerminalBuilder.builder().system(true).build();
		LineReader lineReader = LineReaderBuilder.builder().terminal(terminal).build();
		while(true){
            String line = lineReader.readLine("");
                if ("stop".equals(line)) {
                bw.close();
				s.close();
                    break;
                }

				if(line.equals("createLang")){
					File dir = new File("lang/");
					File[] files = dir.listFiles();
					for(File file : files){
						file.delete();
					}
					Lang.prepare();
				}else{

				if(!line.equals("")){
                bw.write("CHAT "+line);
                bw.newLine();
                bw.flush();
				}

				}
		}
        } catch (UserInterruptException ue) {
			try{
            s.close();
			}catch(Exception e111514){}
        } catch (EndOfFileException eofe) {
			try{
			s.close();
			}catch(Exception e1919810){}
		} catch (Exception e) {
			e.printStackTrace();
		}

    }
  }


