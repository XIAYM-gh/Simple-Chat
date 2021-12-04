package tcp.client;
import java.io.BufferedReader;
import java.io.IOException;
import org.json.*;

  public class ReThread implements Runnable {

    private BufferedReader br;

    public ReThread(BufferedReader br) {
        this.br = br;
    }

    @Override public void run() {
        try {
            String line = null;
            while ((line = br.readLine()) != null) {
                JSONObject jo = new JSONObject(line);
				if(jo.getString("data_type").equals("kick")){
					System.out.println("\r"+Lang.get("server.disconnect")+jo.getString("data_msg"));
                    System.out.print("> ");
				}else if(jo.getString("data_type").equals("info")){
                    System.out.println("\r"+jo.getString("data_msg"));
                    System.out.print("> ");
                }else if(jo.getString("data_type").equals("chat")){
                    System.out.println("\r<"+jo.getString("data_user")+"> "+jo.getString("data_msg"));
                    System.out.print("> ");
				}

            }

        } catch (IOException e) {
            //e.printStackTrace();
        }

    }
  }


