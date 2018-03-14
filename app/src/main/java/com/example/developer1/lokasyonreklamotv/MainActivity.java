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
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements LocationListener{
    private LocationManager locationManager;
    Context ctx;
    private TextView lat;
    private TextView lon;
    private static int requestCode=1;
    public final String PROX_ALERT_INTENT="com.example.developer1.lokasyonreklamotv.action.LOCATION_CHANGED";
    HashMap<String,Double[]> locationList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationList= new HashMap<>();
        addLocations(locationList);
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
      //  initialiseReceiver();
        for (Map.Entry<String,Double[]> entry : locationList.entrySet()) {
            String key = entry.getKey();
            Double[] value = entry.getValue();
            addProximityAlert(value[0],value[1],200,key);
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);
    }

    private void addLocations(HashMap<String, Double[]> locationList) {
        locationList.put("Teknokent",new Double[]{40.741039,30.338614});
        locationList.put("Test",new Double[]{40.741039,30.338614});
        locationList.put("Kampüs Durak",new Double[]{40.741039,30.338614});
       locationList.put("Bilg Müh Fakülte",new Double[]{40.74189,30.32579});
       locationList.put("Fen Edebiyat",new Double[]{40.74507,30.32869});
       locationList.put("Yemekhane",new Double[]{40.74402,30.33151});
       locationList.put("Enstitü",new Double[]{40.74281,30.33377});
       locationList.put("Besyo",new Double[]{40.74321,30.33863});
       locationList.put("Havana",new Double[]{40.74527,30.34346});
       locationList.put("Çakıroğlu Camii",new Double[]{40.74591,30.34693});
       locationList.put("Hakmar",new Double[]{40.74982,30.35246});
       locationList.put("Tapu",new Double[]{40.75289,30.3528});
       locationList.put("Tapu 2",new Double[]{40.75669,30.35607});
       locationList.put("Köprü",new Double[]{40.7592,30.3568});
       locationList.put("Zorlu",new Double[]{40.76144,30.3571});
       locationList.put("A101 durağı",new Double[]{40.76162,30.35881});
       locationList.put("Mavi Durak",new Double[]{40.76204,30.36167});
       locationList.put("Karakol",new Double[]{40.76569,30.36093});
       locationList.put("Serdivan Belediye",new Double[]{40.77169,30.36322});
       locationList.put("Sapak Camii",new Double[]{40.77257,30.36645});
       locationList.put("İbrahimağa Camii",new Double[]{40.77301,30.36936});
       locationList.put("Sakmar",new Double[]{40.74982,30.35246});
       locationList.put("Winhouse",new Double[]{40.77262,30.37266});
       locationList.put("Fuar mobilya",new Double[]{40.77282,30.37508});
       locationList.put("Yapı Kredi",new Double[]{40.77318,30.37702});
       locationList.put("Çilek Mobilya",new Double[]{40.77337,30.37854});
       locationList.put("Hakkı Demir Camii",new Double[]{40.7738,30.38027});
       locationList.put("Enza Home",new Double[]{40.7744,30.38187});
       locationList.put("Bellona",new Double[]{40.77476,30.38294});
       locationList.put("Köfteci İsmail",new Double[]{40.77512,30.38418});
       locationList.put("Kentpark",new Double[]{40.77553,30.38701});
       locationList.put("İmamhatip",new Double[]{40.77604,30.38903});
       locationList.put("Çark",new Double[]{40.7766,30.39168});

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
//    private void addProximityAlert(double latitude, double longitude, int radius, String poiName) {
//
//        Intent intent = new Intent(PROX_ALERT_INTENT);
//        intent.putExtra("name",poiName);
//        intent.putExtra("id",requestCode);
//        PendingIntent proximityIntent = PendingIntent.getBroadcast(this, requestCode , intent, PendingIntent.FLAG_CANCEL_CURRENT);
//        locationManager.addProximityAlert(
//                latitude,
//                longitude,
//                (float)radius,
//                -1,
//                proximityIntent
//        );
//        requestCode++;
//    }

    private void addProximityAlert(double latitude, double longitude, int radius, String poiName) {
        String newIntentName = PROX_ALERT_INTENT+"."+requestCode;
        Intent intent = new Intent(newIntentName);
        intent.putExtra("name",poiName);
        intent.putExtra("id",requestCode);
        PendingIntent proximityIntent = PendingIntent.getBroadcast(this, requestCode , intent, PendingIntent.FLAG_CANCEL_CURRENT);
        locationManager.addProximityAlert(
                latitude,
                longitude,
                (float)radius,
                -1,
                proximityIntent
        );
        IntentFilter filter = new IntentFilter(newIntentName);
        registerReceiver(new AlertOnProximityReceiver(), filter);
        requestCode++;
    }
}
