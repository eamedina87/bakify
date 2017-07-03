package ec.medinamobile.bakify.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import ec.medinamobile.bakify.main.ui.MainActivity;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Supertel on 30/6/17.
 */

public class NetworkUtils {

    public static final String JSON_RECIPES_SERVER_URL = "http://go.udacity.com/android-baking-app-json";

    public static String getRecipesFromServer(String Url){
        String result = null;
        try {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .build()
                    ;
            Request request = new Request.Builder()
                    .url(Url)
                    .build();
            Response response =  client.newCall(request).execute();
            result = response.body().string();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        return activeNetwork!=null && activeNetwork.isAvailable() && activeNetwork.isConnected();
    }
}
