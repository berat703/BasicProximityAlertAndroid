package com.example.developer1.lokasyonreklamotv;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Developer1 on 22.02.2018.
 */

public class AlertOnProximityReceiver extends BroadcastReceiver {
    CharSequence text = "You are in....:";
    int duration = Toast.LENGTH_SHORT;
    Toast toast;
    @Override
    public void onReceive(final Context context, final Intent intent){
        Boolean getting_closer = intent.getBooleanExtra(LocationManager.KEY_PROXIMITY_ENTERING,false);
        String locationName = intent.getExtras().getString("name");
        if(getting_closer){

                toast = Toast.makeText(context,text+locationName,duration);
                toast.show();
            sendNotification(context,"Entered the area..:"+locationName);
         }else{
            toast = Toast.makeText(context,"Exited area..:"+locationName,duration);
            toast.show();
            sendNotification(context,"Exited area..:"+locationName);

        }
        }


    public void sendNotification(Context mcontext,String msg){

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(mcontext)
                        .setSmallIcon(R.drawable.tr)
                        .setContentTitle("Notification Title")
                        .setContentText(msg);


        NotificationManager mNotificationManager =

                (NotificationManager) mcontext.getSystemService(Context.NOTIFICATION_SERVICE);


        mNotificationManager.notify(001, mBuilder.build());
    }

    }

