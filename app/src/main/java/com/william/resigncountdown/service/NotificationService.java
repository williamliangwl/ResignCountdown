package com.william.resigncountdown.service;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.PowerManager;
import android.support.v7.app.NotificationCompat;

import com.william.resigncountdown.R;
import com.william.resigncountdown.activity.MainActivity;
import com.william.resigncountdown.helper.DateHelper;
import com.william.resigncountdown.model.DateDiff;

/**
 * Created by willi on 28-Apr-16.
 */
public class NotificationService extends BroadcastReceiver {

    public int notificationId = 1;
    public final static String NOTIFICATION = "notification";
    final public static String ONE_TIME = "onetime";

    Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.mContext = context;

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
        //Acquire the lock
        wl.acquire();

        Intent splashIntent = new Intent(context, MainActivity.class);
        splashIntent.putExtra(NOTIFICATION, true);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                100,
                splashIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        final DateDiff dateDiff = DateHelper.getDiff(MainActivity.getResignDate());
        String content = "";
        content += String.format("%02d", dateDiff.getWeeks()) + " Weeks ";
        content += String.format("%02d", dateDiff.getDays()) + " Days to go";


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);
        builder.setContentTitle("Resign Countdown");
        builder.setContentText(content);
        builder.setSmallIcon(android.support.v7.appcompat.R.drawable.abc_ic_star_black_16dp);
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.bluejack);
        builder.setLargeIcon(bm);
        builder.setContentIntent(pendingIntent);
        notificationManager.notify(notificationId++, builder.build());

        wl.release();
    }
}
