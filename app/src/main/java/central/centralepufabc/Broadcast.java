package central.centralepufabc;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by Kleverson Nascimento on 15/03/2017.
 */

public class Broadcast extends BroadcastReceiver {
    Calendar calendar;
    SQLiteDatabase bd;
    Cursor cursor;
    @Override
    public void onReceive(Context context, Intent intent) {
        calendar = Calendar.getInstance(TimeZone.getTimeZone("Brazil/East"));
        bd = context.openOrCreateDatabase("CentralEPUFABCDB", Context.MODE_PRIVATE, null);
        int dia = calendar.get(Calendar.DAY_OF_YEAR);
        cursor = bd.rawQuery("SELECT dia_mes,mes,desc_dia,status,dia,msg FROM dias WHERE (dia='"+dia+"' AND status='notificar') ORDER BY dia ASC",null);
        if(cursor.getCount()!=0) {
            cursor.moveToFirst();

            Intent viewIntent = new Intent(context, Calendario.class);
            PendingIntent viewPendingIntent =
                    PendingIntent.getActivity(context, 0, viewIntent, 0);

            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.fretado)
                            .setContentTitle("Central EPUFABC")
                            .setContentText(cursor.getString(2))
                            .setContentIntent(viewPendingIntent)
                            .setAutoCancel(true)
                            .setVibrate(new long[] { 500, 500})
                            .setSound(Settings.System.DEFAULT_NOTIFICATION_URI);


            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

            int notificationId = 001;

            notificationManager.notify(notificationId, notificationBuilder.build());
            bd.execSQL("INSERT INTO dias VALUES('"+cursor.getString(2)+"','"+cursor.getString(4)+"','"+cursor.getString(0)+"','"+cursor.getString(1)+"','notificado','"+cursor.getString(5)+"');");
            bd.execSQL("DELETE FROM dias WHERE (desc_dia='" +cursor.getString(2) + "' AND status='notificar')");
        }
    }
}
