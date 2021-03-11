import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Ticket {
    static String[] origins = new String[20];
    static String[] destinations = new String[20];
    static String[] depart_date = new String[20];
    static String[] return_date = new String[20];
    static int[] value = new int[20];
    static String[] gate = new String[20];
    static Database database = new Database();

    public static String[] getTicket(String message, String message2) throws IOException{

        URL url = new URL("http://api.travelpayouts.com/v2/prices/latest?currency=kzt&origin=" + message + "&destination=" + message2 + "&period_type=year&page=1&limit=10&show_to_affiliates=true&sorting=price&token=a1b6599a76759e3aa62b6431f596ac70");

        Scanner in = new Scanner((InputStream) url.getContent());
        String result = "";
        while (in.hasNext()) {
            result += in.nextLine();//
        }

        JSONObject obj = new JSONObject(result);
        JSONArray arr = obj.getJSONArray("data");
        for (int i = 0; i < arr.length(); i++) {
            origins[i] = arr.getJSONObject(i).getString("origin");
            destinations[i] = arr.getJSONObject(i).getString("destination");
            depart_date[i] = arr.getJSONObject(i).getString("depart_date");
            return_date[i] = arr.getJSONObject(i).getString("return_date");
            value[i] = arr.getJSONObject(i).getInt("value");
            gate[i] = arr.getJSONObject(i).getString("gate");
        }
        return null;
    }

    public static String[] getDestinations() {
        return destinations;
    }

    public static String[] getOrigins() {
        return origins;
    }

    public static String[] getDepart_date() {
        return depart_date;
    }

    public static String[] getReturn_date() {
        return return_date;
    }

    public static int[] getValue() {
        return value;
    }

    public static String[] getGate() {
        return gate;
    }
}
