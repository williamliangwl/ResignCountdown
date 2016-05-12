package com.william.resigncountdown.activity;

import android.Manifest;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.william.resigncountdown.R;
import com.william.resigncountdown.helper.DateHelper;
import com.william.resigncountdown.model.DateDiff;
import com.william.resigncountdown.service.BootReceiver;

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

        setResignDate();

        setBootReceiver();

        th.start();
    }

    private void setResignDate () {
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
        resignDate = calendar.getTime();
    }

    private void setBootReceiver() {
        ComponentName receiver = new ComponentName(this, BootReceiver.class);
        PackageManager pm = this.getPackageManager();
        pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
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
