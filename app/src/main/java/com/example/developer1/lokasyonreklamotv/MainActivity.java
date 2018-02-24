package com.example.developer1.lokasyonreklamotv;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.nfc.Tag;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationListener{
    private LocationManager locationManager;
    Context ctx;
    private TextView lat;
    private TextView lon;
    private static int requestCode=0;
    public final String PROX_ALERT_INTENT="com.example.developer1.lokasyonreklamotv.LOCATION_CHANGED";
    String[][] locations = new String[][]{
            {"Teknokent","40.741039","30.338614"},
            {"Kampüs Durak","40.74031","30.32901"},
            {"Bilg Müh Fakülte","40.74189","30.32579"},
            {"Fen Edebiyat","40.74507","30.32869"},
            {"Yemekhane","40.74402","30.33151"},
            {"Enstitü","40.74281","30.33377"},
            {"Besyo","40.74321","30.33863"},
            {"Havana","40.74527","30.34346"},
            {"Çakıroğlu Camii","40.74591","30.34693"},
            {"Hakmar","40.74982","30.35246"},
            {"Tapu","40.75289","30.3528"},
            {"Tapu 2","40.75669","30.35607"},
            {"Köprü","40.7592","30.3568"},
            {"Zorlu","40.76144","30.3571"},
            {"A101 durağı","40.76162","30.35881"},
            {"Mavi Durak","40.76204","30.36167"},
            {"Karakol","40.76569","30.36093"},
            {"Serdivan Belediye","40.77169","30.36322"},
            {"Sapak Camii","40.77257","30.36645"},
            {"İbrahimağa Camii","40.77301","30.36936"},
            {"Sakmar","40.74982","30.35246"},
            {"Winhouse","40.77262","30.37266"},
            {"Fuar mobilya","40.77282","30.37508"},
            {"Yapı Kredi","40.77318","30.37702"},
            {"Çilek Mobilya","40.77337","30.37854"},
            {"Hakkı Demir Camii","40.7738","30.38027"},
            {"Enza Home","40.7744","30.38187"},
            {"Bellona","40.77476","30.38294"},
            {"Köfteci İsmail","40.77512","30.38418"},
            {"Kentpark","40.77553","30.38701"},
            {"İmamhatip","40.77604","30.38903"},
            {"Çark","40.7766","30.39168"}

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lon = findViewById(R.id.textView4);
        lat = findViewById(R.id.textView2);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MainActivity.this, "First enable LOCATION ACCESS in settings.", Toast.LENGTH_LONG).show();
            return;
        }
        for(int i=0;i<locations.length;i++){
            addProximityAlert(Double.parseDouble(locations[i][1]),Double.parseDouble(locations[i][2]),20,locations[i][0]);
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);
        AlertOnProximityReceiver a = new AlertOnProximityReceiver();
    }
    @Override
    public void onLocationChanged(Location location) {
        String longitude = String.valueOf(location.getLongitude());
        String latitude = String.valueOf(location.getLatitude());
        lat.setText(latitude);
        lon.setText(longitude);
    }
    @Override
    public void onProviderDisabled(String provider) {

        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
        Toast.makeText(getBaseContext(), "Gps is turned off!! ",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderEnabled(String provider) {

        Toast.makeText(getBaseContext(), "Gps is turned on!! ",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }
    private void addProximityAlert(double latitude, double longitude, float radius, String poiName) {

        Intent intent = new Intent(PROX_ALERT_INTENT);
        intent.putExtra("name",poiName);
        intent.putExtra("id",requestCode);
        PendingIntent proximityIntent = PendingIntent.getBroadcast(MainActivity.this, requestCode , intent, PendingIntent.FLAG_CANCEL_CURRENT);
        locationManager.addProximityAlert(
                latitude,
                longitude,
                radius,
                -1,
                proximityIntent
        );
        requestCode++;

    }}
