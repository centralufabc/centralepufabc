package central.centralepufabc;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class Calendario extends AppCompatActivity {
    private ListView lista;
    private ArrayList<Dia_importante> arrayAreas=null;
    private Dia_importante_adapter adapter=null;
    SQLiteDatabase bd;
    Cursor cursor;
    Calendar calendar;
    int dia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bd = openOrCreateDatabase("CentralEPUFABCDB", Context.MODE_PRIVATE, null);
        calendar = Calendar.getInstance(TimeZone.getTimeZone("Brazil/East"));

        dia = calendar.get(Calendar.DAY_OF_YEAR)-1;
        cursor = bd.rawQuery("SELECT dia_mes,mes,desc_dia,status FROM dias  WHERE dia>='"+dia+"'ORDER BY dia ASC",null);
        cursor.moveToFirst();

        lista=(ListView) findViewById(R.id.lista_c);
        arrayAreas=new ArrayList<Dia_importante>();
        carregar_lista();

    }

    private void carregar_lista(){
        arrayAreas.add(new Dia_importante(cursor.getString(0),cursor.getString(1),"Você receberá notificações das datas com fundo em verde, caso não queira ser notificado basta tocar sobre a data, assim o fundo ficará branco e você não será notificado.",cursor.getString(3)));
        while(cursor.getPosition()<cursor.getCount()){
            arrayAreas.add(new Dia_importante(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3)));
            cursor.moveToNext();
        }
        adapter=new Dia_importante_adapter(this,arrayAreas);
        lista.setAdapter(adapter);
    }

    public void marca_dia(View view){
        String x;
        TextView txt=(TextView) view;
        cursor = bd.rawQuery("SELECT dia_mes,mes,desc_dia,status,dia,msg FROM dias WHERE desc_dia='"+txt.getText().toString()+"' ORDER BY dia ASC",null);
        cursor.moveToFirst();
        if(!cursor.getString(3).equals("notificado")) {
            if (cursor.getString(3).equals("notificar")) {
                x = "nao";
            } else {
                x = "notificar";
            }
            bd.execSQL("INSERT INTO dias VALUES('" + cursor.getString(2) + "','" + cursor.getString(4) + "','" + cursor.getString(0) + "','" + cursor.getString(1) + "','" + x + "','" + cursor.getString(5) + "');");
            bd.execSQL("DELETE FROM dias WHERE (desc_dia='" + txt.getText().toString() + "' AND status='" + cursor.getString(3) + "')");
            cursor = bd.rawQuery("SELECT dia_mes,mes,desc_dia,status FROM dias  WHERE dia>='" + dia + "'ORDER BY dia ASC", null);
            arrayAreas.clear();
            cursor.moveToFirst();
            carregar_lista();
        }
    }

    public void voltar(View view) {
        finish();
    }

}
