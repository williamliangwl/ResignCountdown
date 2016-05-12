package com.william.resigncountdown.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.william.resigncountdown.R;
import com.william.resigncountdown.helper.DateHelper;
import com.william.resigncountdown.model.DateDiff;
import com.william.resigncountdown.service.NotificationService;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private TextView weekText, dayText, hourText, minuteText, secondText;
    private Date resignDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weekText = (TextView) findViewById(R.id.weekText);
        dayText = (TextView) findViewById(R.id.dayText);
        hourText = (TextView) findViewById(R.id.hourText);
        minuteText = (TextView) findViewById(R.id.minuteText);
        secondText = (TextView) findViewById(R.id.secondText);

        resignDate = getResignDate();

        th.start();

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent myIntent = new Intent(MainActivity.this, NotificationService.class);
        PendingIntent  pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent, 0);
        Calendar calendar = Calendar.getInstance();
        calendar.getTimeInMillis();
        Calendar calendar1 = (Calendar) calendar.clone();
        calendar1.set(Calendar.HOUR, 9);
        calendar1.set(Calendar.MINUTE, 0);
        calendar1.set(Calendar.SECOND, 0);
        if(calendar.getTimeInMillis() > calendar1.getTimeInMillis()){
            calendar1.set(Calendar.DATE, calendar.get(Calendar.DATE)+1);
        }
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    public static Date getResignDate() {
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.MONTH) > 7 && calendar.get(Calendar.DATE) != 31) {
            calendar.add(Calendar.YEAR, 1);
            calendar.set(Calendar.MONTH, 1);
            if (calendar.get(Calendar.YEAR) % 4 == 0)
                calendar.set(Calendar.DATE, 29);
            else
                calendar.set(Calendar.DATE, 28);
        } else {
            calendar.set(Calendar.YEAR, 2016);
            calendar.set(Calendar.MONTH, 7);
            calendar.set(Calendar.DATE, 31);
        }
        calendar.set(Calendar.HOUR_OF_DAY, 17);
        return calendar.getTime();
    }

    Thread th = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                while (true) {
                    final DateDiff dateDiff = DateHelper.getDiff(resignDate);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            weekText.setText(String.format("%02d", dateDiff.getWeeks()));
                            dayText.setText(String.format("%02d", dateDiff.getDays()));
                            hourText.setText(String.format("%02d", dateDiff.getHours()));
                            minuteText.setText(String.format("%02d", dateDiff.getMinutes()));
                            secondText.setText(String.format("%02d", dateDiff.getSeconds()));
                        }
                    });
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });
}
