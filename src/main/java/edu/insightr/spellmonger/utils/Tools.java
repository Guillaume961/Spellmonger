package edu.insightr.spellmonger.utils;

import edu.insightr.spellmonger.model.Joueur;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;


public class Tools {

    private Tools() {
    }

    public static void updateJsonFile(String Login, boolean isWinner) {
        String filepath = System.getProperty("user.dir") + "/src/main/resources/score.json";
        Map<String, Joueur> m = Tools.readFileToMap(filepath);
        Joueur p = m.get(Login);
        if (p != null) {
            double pourcentage;
            double tmp = Double.parseDouble(p.getPercentageScore()) / 100 * (Double.parseDouble(p.getNbPlay()));
            p.setNbPlay((Double.parseDouble(p.getNbPlay()) + 1) + "");
            if (isWinner)
                pourcentage = (tmp + 1) * 100 / Double.parseDouble(p.getNbPlay());
            else
                pourcentage = tmp * 100 / Double.parseDouble(p.getNbPlay());
            p.setPercentageScore(pourcentage + "");
        } else {
            if (isWinner)
                m.put(Login, new Joueur(Login, 1, 100));
            else
                m.put(Login, new Joueur(Login, 1, 0));
        }
        createFilewithMap(m, filepath);
    }


    public static void createFilewithMap(Map<String, Joueur> data, String filepath) {
        try {
            JSONObject jsonObject = new JSONObject();
            JSONArray scoreList = new JSONArray();

            for (String s : data.keySet()) {
                JSONObject p = new JSONObject();
                p.put("Login", s);
                p.put("NbPlay", data.get(s).getNbPlay());
                p.put("PercentageScore", data.get(s).getPercentageScore());
                scoreList.add(p);
            }

            jsonObject.put("scores", scoreList);
            FileWriter fs = new FileWriter(filepath);
            jsonObject.writeJSONString(fs);
            fs.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Joueur> readFileToMap(String filepath) {

        JSONParser parser = new JSONParser();
        Map<String, Joueur> data = new HashMap<>();
        try {

            Object obj = parser.parse(new FileReader(filepath));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray scoreList = (JSONArray) jsonObject.get("scores");

            for (JSONObject it : (Iterable<JSONObject>) scoreList) {
                String Login = (String) it.get("Login");
                double NbPlay = Double.parseDouble((String) it.get("NbPlay"));
                double p = Double.parseDouble((String) it.get("PercentageScore"));
                data.put(Login, new Joueur(Login, NbPlay, p));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Tools.sortByComparator(data, false);
    }

    // trie croissant --> true , false sinon
    private static Map<String, Joueur> sortByComparator(Map<String, Joueur> unsortMap, final boolean order) {

        List<Map.Entry<String, Joueur>> list = new LinkedList<Map.Entry<String, Joueur>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Map.Entry<String, Joueur>>() {
            public int compare(Map.Entry<String, Joueur> o1,
                               Map.Entry<String, Joueur> o2) {
                if (order) {
                    return o1.getValue().compareTo(o2.getValue());
                } else {
                    return o2.getValue().compareTo(o1.getValue());

                }
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<String, Joueur> sortedMap = new LinkedHashMap<String, Joueur>();
        for (Map.Entry<String, Joueur> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }


}
