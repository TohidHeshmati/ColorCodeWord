import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Word {

    String sourceLanguage;
    String destinationLanguage;
    String pos;
    String gender;
    String input;
    String meaning;

    public Word(String inputLanguage, String answerLanguage, String input) {
        this.sourceLanguage = inputLanguage;
        this.destinationLanguage = answerLanguage;
        this.input = input;
    }

    public String[] getResponse() throws IOException {


        String url = "https://linguee-api.herokuapp.com/api?q="+input+"&src="+sourceLanguage+"&dst="+destinationLanguage;
        URL urlObject = new URL(url);

        HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = br.readLine()) != null) {
            response.append(inputLine);
        }
        br.close();

        String st = response.toString();
        JSONObject jsonObject = new JSONObject(st);
        JSONArray exactMatches = jsonObject.optJSONArray("exact_matches");

        pos = exactMatches.getJSONObject(0).getJSONObject("word_type").getString("pos");
        gender = exactMatches.getJSONObject(0).getJSONObject("word_type").getString("gender");
        meaning = exactMatches.getJSONObject(0).getJSONArray("translations").getJSONObject(0).getString("text");
        System.out.println("meaning: " + meaning);
        System.out.println("pos: " + pos);
        System.out.println("gender: " + gender);

        String[] result = new String[]{pos, gender, meaning};

        return result;

    }
}
