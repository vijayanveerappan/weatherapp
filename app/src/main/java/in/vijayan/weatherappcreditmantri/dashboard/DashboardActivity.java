package in.vijayan.weatherappcreditmantri.dashboard;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import in.vijayan.weatherappcreditmantri.R;
import in.vijayan.weatherappcreditmantri.adapter.AdapterGridWeather;
import in.vijayan.weatherappcreditmantri.utils.Config;
import in.vijayan.weatherappcreditmantri.utils.Urls;
import in.vijayan.weatherappcreditmantri.utils.Utils;
import in.vijayan.weatherappcreditmantri.volley.HttpConnection;

/**
 * Created by Hp on 12-06-2018.
 */

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {

    String strCities;
    Spinner spin_city;
    GridView grid_day_status;
    AdapterGridWeather adapter;


    TextView tv_today_date, tv_today_weather_status, tv_today_weather_celsius,tv_celsius,tv_fahrenheit;
    ImageView img_today_weather_status;

    JSONArray jsonArray = new JSONArray();

    ArrayAdapter<String> adapterCity;

    ArrayList lstCity = new ArrayList();

    boolean chkTemp = true;

    String strFahrenheit, strCelsius;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        spin_city = findViewById(R.id.spin_city);
        grid_day_status = findViewById(R.id.grid_day_status);

        tv_today_date = findViewById(R.id.tv_today_date);
        tv_today_weather_status = findViewById(R.id.tv_today_weather_status);
        tv_today_weather_celsius = findViewById(R.id.tv_today_weather_celsius);

        tv_celsius = findViewById(R.id.tv_celsius);
        tv_fahrenheit = findViewById(R.id.tv_fahrenheit);
        tv_celsius.setOnClickListener(this);
        tv_fahrenheit.setOnClickListener(this);

        img_today_weather_status = findViewById(R.id.img_today_weather_status);

        adapter = new AdapterGridWeather(getApplicationContext(), jsonArray);
        grid_day_status.setAdapter(adapter);

        lstCity.add("Chennai");
        lstCity.add("Bangalore");
        lstCity.add("Mumbai");
        lstCity.add("Delhi");

        adapterCity = new ArrayAdapter<String>(this, R.layout.spinner_item, lstCity);
        adapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_city.setAdapter(adapterCity);

        spin_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                strCities = spin_city.getSelectedItem().toString();

                if (Utils.shareUtils(getApplicationContext()).IsNetworkAvailable(getApplicationContext())) {
                    getWeatherReport();
                } else {
                    Toast.makeText(DashboardActivity.this, "" + getResources().getString(R.string.chk_internet), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        grid_day_status.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(DashboardActivity.this, "You Clicked at " + position, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(DashboardActivity.this, WeatherDetailsActivity.class));
            }
        });

    }


    private void getWeatherReport() {

        HttpConnection.getInstance(getApplicationContext()).objectRequest(Urls.BASE_URL + strCities + Urls.APPENDS, Config.retryPolicy, new HttpConnection.VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject response) {

                try {

                    Integer dateValue = Integer.parseInt(response.getJSONArray("list").getJSONObject(0).getString("dt"));

                    String myDate = new SimpleDateFormat("dd, MMM yyyy ").format(new Date(dateValue * 1000L));
                    tv_today_date.setText("Today " + myDate);

                    tv_today_weather_status.setText(response.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("main"));

                    if (tv_today_weather_status.getText().toString().equalsIgnoreCase("rain")) {
                        img_today_weather_status.setBackgroundResource(R.drawable.art_rain);
                    } else {
                        img_today_weather_status.setBackgroundResource(R.drawable.ic_common_icon);
                    }

                    String result = response.getJSONArray("list").getJSONObject(0).getJSONObject("temp").getString("day");
                    double valueCelsius = Double.parseDouble(result);

                    int intCelsius = (int) valueCelsius;
                    strCelsius = String.valueOf(intCelsius);

                    double valueFahrenheit = valueCelsius*9/5+32;
                    int intFahrenheit = (int) valueFahrenheit;
                    strFahrenheit = String.valueOf(intFahrenheit);

                    if(chkTemp) {
                        tv_today_weather_celsius.setText(strCelsius);
                    }else{
                        tv_today_weather_celsius.setText(strFahrenheit);
                    }

                    jsonArray = response.getJSONArray("list");

                    adapter = new AdapterGridWeather(getApplicationContext(), jsonArray);
                    grid_day_status.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(VolleyError error) {

            }
        });

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.tv_celsius:
                chkTemp = true;
                tv_celsius.setTextColor(Color.parseColor("#FFFFFF"));
                tv_fahrenheit.setTextColor(Color.parseColor("#000000"));
                tv_today_weather_celsius.setText(strCelsius);
                break;

            case R.id.tv_fahrenheit:
                chkTemp = false;
                tv_celsius.setTextColor(Color.parseColor("#000000"));
                tv_fahrenheit.setTextColor(Color.parseColor("#FFFFFF"));
                tv_today_weather_celsius.setText(strFahrenheit);
                break;
        }
    }

}
