package ec.medinamobile.bakify.utils;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Supertel on 30/6/17.
 */

public class NetworkUtils {

    public static String getRecipesFromServer(String Url){

        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(Url)
                    .build();
            Response response =  client.newCall(request).execute();
            return response.body().string();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
