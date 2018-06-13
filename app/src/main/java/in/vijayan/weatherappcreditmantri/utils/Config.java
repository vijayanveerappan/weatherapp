package in.vijayan.weatherappcreditmantri.utils;


import com.android.volley.DefaultRetryPolicy;

public class Config {

    public static DefaultRetryPolicy retryPolicy = new DefaultRetryPolicy(80000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

}
