package central.centralepufabc;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by Kleverson Nascimento on 15/03/2017.
 */

public class CriaAlarme extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent it=new Intent("Alarme");
        PendingIntent p=PendingIntent.getBroadcast(context,0,it,0);

        Calendar c=Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());


        AlarmManager alarme=(AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarme.setRepeating(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),60000,p);
    }
}
