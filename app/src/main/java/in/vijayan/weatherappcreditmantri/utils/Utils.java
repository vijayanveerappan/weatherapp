package in.vijayan.weatherappcreditmantri.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class Utils {

        @SuppressLint("StaticFieldLeak")
        private static Utils instance;
        @SuppressLint("StaticFieldLeak")
        private static Context context;

    public static Utils shareUtils(Context ctx) {
        // TODO Auto-generated constructor stub
        if (instance == null) {
            instance = new Utils();
        }
        context = ctx;
        return instance;
    }

    public boolean IsNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

}
