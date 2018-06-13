package in.vijayan.weatherappcreditmantri.dashboard;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import in.vijayan.weatherappcreditmantri.R;

public class WeatherDetailsActivity extends AppCompatActivity implements View.OnClickListener {


    TextView tv_today_date_details, tv_today_weather_status_details, tv_today_celsius_details,
            tv_today_humidity_details, tv_today_pressure_details, tv_today_wind_details,tv_celsius_details,tv_fahrenheit_details;
    ImageView img_today_status_details, img_share;

    Toolbar toolbar;
    boolean chkTemp = true;

    String strFahrenheit, strCelsius;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_details);

        tv_today_date_details = findViewById(R.id.tv_today_date_details);
        tv_today_weather_status_details = findViewById(R.id.tv_today_weather_status_details);
        tv_today_celsius_details = findViewById(R.id.tv_today_celsius_details);
        tv_today_humidity_details = findViewById(R.id.tv_today_humidity_details);
        tv_today_pressure_details = findViewById(R.id.tv_today_pressure_details);
        tv_today_wind_details = findViewById(R.id.tv_today_wind_details);

        tv_celsius_details = findViewById(R.id.tv_celsius_details);
        tv_fahrenheit_details = findViewById(R.id.tv_fahrenheit_details);
        tv_celsius_details.setOnClickListener(this);
        tv_fahrenheit_details.setOnClickListener(this);

        img_today_status_details = findViewById(R.id.img_today_status_details);
        img_share = findViewById(R.id.img_share);
        img_share.setOnClickListener(this);

        Intent intent = getIntent();
        String date = intent.getStringExtra("date");
        String weather_status = intent.getStringExtra("weather_status");
        String celsius = intent.getStringExtra("celsius");
        String humidity = intent.getStringExtra("humidity");
        String pressure = intent.getStringExtra("pressure");
        String wind = intent.getStringExtra("wind");

        Integer dateValue = Integer.parseInt(date);

        String myDate = new SimpleDateFormat("dd, MMM yyyy ").format(new Date(dateValue * 1000L));
        tv_today_date_details.setText(myDate);

        tv_today_weather_status_details.setText(weather_status);

        if (tv_today_weather_status_details.getText().toString().equalsIgnoreCase("rain")) {
            img_today_status_details.setBackgroundResource(R.drawable.art_rain);
        } else {
            img_today_status_details.setBackgroundResource(R.drawable.ic_common_icon);
        }

        String result = celsius;

        double valueCelsius = Double.parseDouble(result);

        int intCelsius = (int) valueCelsius;
        strCelsius = String.valueOf(intCelsius);

        double valueFahrenheit = valueCelsius*9/5+32;
        int intFahrenheit = (int) valueFahrenheit;
        strFahrenheit = String.valueOf(intFahrenheit);

        if(chkTemp) {
            tv_today_celsius_details.setText(strCelsius);
        }else{
            tv_today_celsius_details.setText(strFahrenheit);
        }

        tv_today_humidity_details.setText(humidity + "%");
        tv_today_pressure_details.setText(pressure + " hPa");
        tv_today_wind_details.setText(wind + " Km/h NW");

        setBackToolBar();
    }

    public void setBackToolBar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar_weather_details);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.img_share:

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Date  :  " + tv_today_date_details.getText().toString() + "\n\n"
                                + "Celsius  :  " + tv_today_celsius_details.getText().toString() + "\n\n"
                                + "Weather  :  " + tv_today_weather_status_details.getText().toString() + "\n\n"
                                + "Humidity  :  " + tv_today_humidity_details.getText().toString() + "\n\n"
                                + "Pressure  :  " + tv_today_pressure_details.getText().toString() + "\n\n"
                                + "Wind  :  " + tv_today_wind_details.getText().toString());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);

                break;

            case R.id.tv_celsius_details:
                chkTemp = true;
                tv_celsius_details.setTextColor(Color.parseColor("#FFFFFF"));
                tv_fahrenheit_details.setTextColor(Color.parseColor("#000000"));
                tv_today_celsius_details.setText(strCelsius);
                break;

            case R.id.tv_fahrenheit_details:
                chkTemp = false;
                tv_celsius_details.setTextColor(Color.parseColor("#000000"));
                tv_fahrenheit_details.setTextColor(Color.parseColor("#FFFFFF"));
                tv_today_celsius_details.setText(strFahrenheit);
                break;
        }
    }
}
