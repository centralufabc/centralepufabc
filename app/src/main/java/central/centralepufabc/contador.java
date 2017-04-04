package central.centralepufabc;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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

public class contador extends AppCompatActivity {
    TextView fraseini,dia,hora,minuto,segundo;
    Calendar calendar;
    int hoje,min,d;
    private ListView lista;
    private ArrayList<Gif> arrayAreas=null;
    private Gif_adapater adapter=null;
    SQLiteDatabase bd;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contador);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        calendar = Calendar.getInstance(TimeZone.getTimeZone("Brazil/East"));

        hoje=calendar.get(Calendar.DAY_OF_YEAR);
        fraseini=(TextView) findViewById(R.id.txt_frase_ini);
        hora=(TextView) findViewById(R.id.txt_horas);
        minuto=(TextView) findViewById(R.id.txt_minutos);
        segundo=(TextView) findViewById(R.id.txt_segundos);
        dia=(TextView) findViewById(R.id.txt_dias);

        bd = openOrCreateDatabase("CentralEPUFABCDB", Context.MODE_PRIVATE, null);

        lista=(ListView) findViewById(R.id.lista_gifs);
        arrayAreas=new ArrayList<Gif>();
        carregar_lista();

        final Handler atualizador = new Handler();
        atualizador.post(new Runnable() {
            @Override
            public void run() {
                calendar = Calendar.getInstance(TimeZone.getTimeZone("Brazil/East"));
                dia.setText(String.valueOf(309 - (calendar.get(Calendar.DAY_OF_YEAR))-1));
                segundo.setText(String.valueOf(59 - calendar.get(Calendar.SECOND)));
                minuto.setText(String.valueOf(59 - calendar.get(Calendar.MINUTE)));
                hora.setText(String.valueOf(23 - calendar.get(Calendar.HOUR_OF_DAY)));
                if(hoje<=308){
                    fraseini.setText("Tempo restante para o dia do Enem:");

                } else{
                    fraseini.setText("");
                }
                atualizador.postDelayed(this, 100);
            }
    });



}
    public void voltar(View view) {
        finish();
    }

    public void carregar_lista(){
        arrayAreas.add(new Gif("Nós sabemos que falar isso assim pra uma vestibulando é maldade, então pra compensar nós separamos uma maravilhosa lista com os gifs mais fofos da internet pra vocês verem e acalmarem o coração <3 (ao tocar no nome do gif, ele será aberto no seu navegador, é necessária conexão com a internet para esta ação)"));
        arrayAreas.add(new Gif("Pug brincalhão"));
        adapter=new Gif_adapater(this,arrayAreas);
        lista.setAdapter(adapter);
    }

    public void vai_gif(View view){
        TextView txt=(TextView) view;
        cursor=bd.rawQuery("SELECT url FROM gifs  WHERE nome='"+txt.getText().toString()+"'ORDER BY nome ASC", null);
        cursor.moveToFirst();
        Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
        myWebLink.setData(Uri.parse(cursor.getString(0)));
        startActivity(myWebLink);
    }
}
