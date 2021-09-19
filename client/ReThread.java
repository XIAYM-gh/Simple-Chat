package tcp.client;
import java.io.BufferedReader;
import java.io.IOException;

  public class ReThread implements Runnable {

    private BufferedReader br;

    public ReThread(BufferedReader br) {
        this.br = br;
    }

    @Override public void run() {
        try {
            String line = null;
            while ((line = br.readLine()) != null) {
				if(line.startsWith("disconnect ")){
					System.out.println(Lang.get("server.disconnect")+line.substring(11));
				}else{
                System.out.println("\r"+line);
                System.out.print("> ");
				}

            }

        } catch (IOException e) {
            //e.printStackTrace();
        }

    }
  }


