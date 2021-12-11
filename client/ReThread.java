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
                long time = jo.getLong("data_time");
				if(jo.getString("data_type").equals("kick")){
					main.print(""+Lang.get("server.disconnect")+jo.getString("data_msg"),time);
				}else if(jo.getString("data_type").equals("info")){
                    main.print(""+jo.getString("data_msg"),time);
                }else if(jo.getString("data_type").equals("chat")){
                    main.print("<"+jo.getString("data_user")+"> "+jo.getString("data_msg"),time);
				}

            }

        } catch (IOException e) {
            //e.printStackTrace();
        }

    }
  }


