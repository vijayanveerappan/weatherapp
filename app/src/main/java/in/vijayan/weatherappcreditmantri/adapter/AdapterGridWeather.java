package in.vijayan.weatherappcreditmantri.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Date;

import in.vijayan.weatherappcreditmantri.R;
import in.vijayan.weatherappcreditmantri.dashboard.WeatherDetailsActivity;

public class AdapterGridWeather extends BaseAdapter {
    private Context mContext;
    JSONArray jsonArray = new JSONArray();

    public AdapterGridWeather(Context c, JSONArray jsonArrays) {
        mContext = c;
        jsonArray = jsonArrays;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
//        return web.length;
        return jsonArray.length();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;

        TextView tv_date_gv, tv_celsius_gv, tv_weather_status_gv;
        ImageView img_status_gv;
        LinearLayout ll_root_gv;

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.custom_day_status_grid_view, null);

            tv_date_gv = grid.findViewById(R.id.tv_date_gv);
            tv_celsius_gv = grid.findViewById(R.id.tv_celsius_gv);
            tv_weather_status_gv = grid.findViewById(R.id.tv_weather_status_gv);

            img_status_gv = grid.findViewById(R.id.img_status_gv);

            ll_root_gv = grid.findViewById(R.id.ll_root_gv);


            Integer dateValue = null;
            try {
                dateValue = Integer.parseInt(jsonArray.getJSONObject(position).getString("dt"));

                String myDate = new SimpleDateFormat("dd, MMM").format(new Date(dateValue * 1000L));
                tv_date_gv.setText(myDate);

                tv_weather_status_gv.setText(jsonArray.getJSONObject(position).getJSONArray("weather").getJSONObject(0).getString("main"));

                if (tv_weather_status_gv.getText().toString().equalsIgnoreCase("rain")) {
                    img_status_gv.setBackgroundResource(R.drawable.art_rain);
                } else {
                    img_status_gv.setBackgroundResource(R.drawable.ic_common_icon);
                }

                String result = jsonArray.getJSONObject(position).getJSONObject("temp").getString("day");
                double value = Double.parseDouble(result);
                int i = (int) value;
                tv_celsius_gv.setText(String.valueOf(i));


                ll_root_gv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        try {
                            mContext.startActivity(new Intent(mContext, WeatherDetailsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    .putExtra("date",jsonArray.getJSONObject(position).getString("dt"))
                                    .putExtra("weather_status",jsonArray.getJSONObject(position).getJSONArray("weather").getJSONObject(0).getString("main"))
                                    .putExtra("celsius",jsonArray.getJSONObject(position).getJSONObject("temp").getString("day"))
                                    .putExtra("humidity",jsonArray.getJSONObject(position).getString("humidity"))
                                    .putExtra("pressure",jsonArray.getJSONObject(position).getString("pressure"))
                                    .putExtra("wind",jsonArray.getJSONObject(position).getString("speed")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });


            } catch (JSONException e) {
                e.printStackTrace();
            }


        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}