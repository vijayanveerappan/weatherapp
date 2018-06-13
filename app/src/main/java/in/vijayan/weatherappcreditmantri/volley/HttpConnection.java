package in.vijayan.weatherappcreditmantri.volley;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;


public class HttpConnection {

    private RequestQueue mRequestQueue;
    private String TAG = HttpConnection.class.getSimpleName();
    @SuppressLint("StaticFieldLeak")
    private static HttpConnection mInstance;
    private Context mCtx;

    private HttpConnection(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized HttpConnection getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new HttpConnection(context);
        }
        return mInstance;
    }

    public void objectRequest(String url, DefaultRetryPolicy retryPolicy, final VolleyCallBack callBack) {

        Log.v("TAG", "URL ***********" + url);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.v("TAG", "RESPONSE ***********" + response);
                callBack.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.v("TAG", "ERROR ***********" + volleyError);
                callBack.onFailure(volleyError);

            }
        });

        request.setRetryPolicy(retryPolicy);
        addToRequestQueue(request);
    }

    public interface VolleyCallBack {
        void onSuccess(JSONObject object);

        void onFailure(VolleyError error);
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }

        return mRequestQueue;
    }

    private <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

}
