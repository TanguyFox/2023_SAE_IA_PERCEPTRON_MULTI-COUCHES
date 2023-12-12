package defis.defi_1.tool;

import defis.defi_1.structure.Ville;
import defis.defi_1.structure.Position;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Loader {

    public static Position loadPosition(String nom_ville){

        StringBuilder jsonResponse = null;
        try {
            URL url = new URL("https://api-adresse.data.gouv.fr/search/?q=" + URLEncoder.encode(nom_ville, StandardCharsets.UTF_8) + "&limit=1");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            jsonResponse = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonResponse.append(line);
            }
            reader.close();
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject(jsonResponse.toString());

//        System.out.println(jsonObject.toString());

        JSONArray jsonArray = jsonObject.getJSONArray("features")
                .getJSONObject(0)
                .getJSONObject("geometry")
                .getJSONArray("coordinates");

        double longitude = Math.toRadians(jsonArray.getDouble(0));
        double latitude = Math.toRadians(jsonArray.getDouble(1));
        return new Position(longitude, latitude);
    }

    public static HashMap<String, Ville> loadArrays(String path) {

        PriorityQueue<Ville> villes = new PriorityQueue<>((o1, o2) -> Integer.compare(o2.getPopulation(), o1.getPopulation()));

        try{
            JSONTokener tokener = new JSONTokener(new FileReader(path));
            JSONArray jsonArray = new JSONArray(tokener);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String nom_ville = jsonObject.getString("nom");
                int population = jsonObject.optInt("population", 0);

//                System.out.println(nom_ville + " " + population);

                Ville ville = new Ville(nom_ville);
                ville.setPopulation(population);
                villes.add(ville);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        HashMap<String, Ville> res = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            Ville n = villes.poll();
            assert n != null;

//            System.out.println(i + ": " + n.getNom_ville());
            n.setPosition(loadPosition(n.getNom_ville().replace(" ", "+")));

            if(i < 10){
                n.setCoefficientClass(1);
            }else{
                n.setCoefficientClass(2);
            }
            res.put(n.getNom_ville(), n);
        }

        return res;
    }

}
