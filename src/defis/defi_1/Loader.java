package defis.defi_1;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Loader {

    public static JSONObject loadURL(String path){

        StringBuilder jsonResponse = null;
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            jsonResponse = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonResponse.append(line);
            }
            reader.close();
//            System.out.println(jsonResponse);
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JSONObject(jsonResponse.toString());
    }

    public static void loadArrays(String path) throws FileNotFoundException {
        JSONTokener tokener = new JSONTokener(new FileReader(path));
        JSONArray jsonArray = new JSONArray(tokener);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            System.out.println(jsonObject);
        }
    }

}
