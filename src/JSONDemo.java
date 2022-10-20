import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class JSONDemo {
    public static void main(String [] args) throws JSONException {
        String accessToken = "";
        OkHttpClient client = new OkHttpClient();

        String term = "Taco";                      // term
        String location = "Irvine, CA";            // location
        String price = "1";                        // price        1 = $, 2 = $$, 3 = $$$, 4 = $$$$


        Request request = new Request.Builder()
                .url("https://api.yelp.com/v3/businesses/search?term=" + term + "&location=" + location + "&sort_by=rating&price="+price+"")
                .get()
                .addHeader("authorization", "Bearer "+accessToken)
                .build();

        try {
            Response response = client.newCall(request).execute();

            JSONObject jsonObject = new JSONObject(response.body().string().trim());       // parser

            System.out.println("jsonObject:");
            //debug to see the keys in the jsonObject: total, region, businesses
            System.out.println("total: " + jsonObject.get("total"));
            System.out.println("region: " + jsonObject.get("region"));
            System.out.println("businesses: " + jsonObject.get("businesses"));
            System.out.println();

            System.out.println("total:");
            //total is just a value that can be cast - no brackets around the prev printout
            System.out.println((int)(jsonObject.get("total")));
            System.out.println();


            System.out.println("region:");
            //region is an object - note the prev printout was in curly braces:
            //need to get the object stored to the key region
            JSONObject region = jsonObject.getJSONObject("region");
            System.out.println(region);
            //get the object stored to center
            JSONObject center = region.getJSONObject("center");
            System.out.println(center);
            //get latitude double value
            System.out.println("latitude:");
            System.out.println(center.getDouble("latitude"));
            System.out.println();


            System.out.println("businesses:");
            //businesses is an array of objects - note the printout was contained in square brackets
            JSONArray bArray = (JSONArray)jsonObject.get("businesses");
            System.out.println("length of bArray: " + bArray.length());
            System.out.println();

            for (int i = 0; i < bArray.length(); i++) {
                //each element of businesses is a JSONObject
                System.out.println("obj " + i + " in bArray");
                JSONObject obj = bArray.getJSONObject(i);
                System.out.println("JSONObject: " + obj);

                //isolate fields of each object by key:
                System.out.println("name: " + obj.getString("name"));
                System.out.println("distance: " + obj.getDouble("distance"));
                System.out.println("transactions: " + obj.getJSONArray("transactions"));
                //transactions is an array
                for (int j = 0; j < obj.getJSONArray("transactions").length(); j++) {
                    System.out.println("transaction " + j + ": " + obj.getJSONArray("transactions").get(j));
                }
                System.out.println();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
