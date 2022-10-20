import java.io.IOException;
import okhttp3.*;
import okhttp3.Request.Builder;
import org.json.*;

public class Main {

    public static void main(String[] args) throws JSONException {

        String accessToken = "";
            
        // GET /businesses/search
        OkHttpClient client2 = new OkHttpClient();

        String term = "Taco";                       // term
        String location = "Irvine, CA";            // location
        String price = "1";                         // price        1 = $, 2 = $$, 3 = $$$, 4 = $$$$


        Request request2 = new Builder()
                .url("https://api.yelp.com/v3/businesses/search?term=" + term + "&location=" + location + "&sort_by=rating&price="+price+"")
                .get()
                .addHeader("authorization", "Bearer"+" "+accessToken)
                .build();

        try {
            Response response2 = client2.newCall(request2).execute();

            JSONObject jsonObject = new JSONObject(response2.body().string().trim());       // parser
            JSONArray myResponse = (JSONArray)jsonObject.get("businesses");


            System.out.println(myResponse.getJSONObject(0).getString("name"));


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
