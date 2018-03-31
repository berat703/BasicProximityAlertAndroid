package com.example.developer1.lokasyonreklamotv;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by Developer1 on 22.02.2018.
 */

public class AlertOnProximityReceiver extends BroadcastReceiver {
    private TextView loc;
    private Messenger messenger;
    public static final Integer STATUS = new Object().hashCode();
    CharSequence text = "You are in....:";
    int duration = Toast.LENGTH_SHORT;

    public AlertOnProximityReceiver(Messenger messenger) {
        this.messenger = messenger;
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {

        String key = LocationManager.KEY_PROXIMITY_ENTERING;
        Boolean getting_closer = intent.getBooleanExtra(key, false);
        final String locationName = intent.getExtras().getString("name");
        Message location = Message.obtain();
        location.what = MainActivity.UPDATE_LOCATION;
        location.obj = locationName;
        Message status = Message.obtain();
        status.what = AlertOnProximityReceiver.STATUS;
        if (getting_closer) {
            status.obj = "Girildi";
        } else {
            status.obj = "Çıkıldı";
        }
        try {
            messenger.send(location);
            messenger.send(status);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void sendNotification(Context mcontext, String msg) {

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(mcontext)
                        .setSmallIcon(R.drawable.tr)
                        .setContentTitle("Notification Title")
                        .setContentText(msg);


        NotificationManager mNotificationManager =

                (NotificationManager) mcontext.getSystemService(Context.NOTIFICATION_SERVICE);


        mNotificationManager.notify(1, mBuilder.build());
    }


}

