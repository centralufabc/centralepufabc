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
        arrayAreas.add(new Gif("Nós sabemos que falar isso assim pra um(a) vestibulando(a) é maldade, então pra compensar nós separamos uma maravilhosa lista com os gifs mais fofos da internet, assim vocês veem e acalmam o coração <3 (ao tocar no nome do gif, ele será aberto no seu navegador, é necessária conexão com a internet para esta ação)",""));
        arrayAreas.add(new Gif("Pug brincalhão","https://img.buzzfeed.com/buzzfeed-static/static/enhanced/webdr06/2013/5/31/10/anigif_enhanced-buzz-3662-1370010446-11.gif?downsize=715:*&output-format=auto&output-quality=auto"));
        arrayAreas.add(new Gif("O chacoalha!Chacoalha!","https://img.buzzfeed.com/buzzfeed-static/static/enhanced/webdr06/2013/5/30/18/anigif_enhanced-buzz-10922-1369953122-8.gif?downsize=715:*&output-format=auto&output-quality=auto"));
        arrayAreas.add(new Gif("O coelho atarefado","https://img.buzzfeed.com/buzzfeed-static/static/enhanced/webdr01/2013/5/30/18/anigif_enhanced-buzz-28498-1369952815-11.gif?downsize=715:*&output-format=auto&output-quality=auto"));
        arrayAreas.add(new Gif("Gato maleável","https://img.buzzfeed.com/buzzfeed-static/static/enhanced/webdr02/2013/5/30/16/anigif_enhanced-buzz-13587-1369944215-19.gif?downsize=715:*&output-format=auto&output-quality=auto"));
        arrayAreas.add(new Gif("Cachorro nadador","https://img.buzzfeed.com/buzzfeed-static/static/enhanced/webdr03/2013/5/30/18/anigif_enhanced-buzz-32505-1369952618-7.gif?downsize=715:*&output-format=auto&output-quality=auto"));
        arrayAreas.add(new Gif("Periquito se cobrindo","https://img.buzzfeed.com/buzzfeed-static/static/enhanced/webdr05/2013/5/30/14/anigif_enhanced-buzz-22562-1369939909-8.gif?downsize=715:*&output-format=auto&output-quality=auto"));
        arrayAreas.add(new Gif("Não vai dar não","https://img.buzzfeed.com/buzzfeed-static/static/enhanced/webdr05/2013/5/30/17/anigif_enhanced-buzz-12716-1369949373-1.gif?downsize=715:*&output-format=auto&output-quality=auto"));
        arrayAreas.add(new Gif("Tartaruga tentando voar","https://img.buzzfeed.com/buzzfeed-static/static/enhanced/webdr03/2013/5/30/18/anigif_enhanced-buzz-32668-1369953262-29.gif?downsize=715:*&output-format=auto&output-quality=auto"));
        arrayAreas.add(new Gif("Surpresa!","https://img.buzzfeed.com/buzzfeed-static/static/enhanced/webdr06/2013/5/30/14/anigif_enhanced-buzz-32228-1369939787-22.gif?downsize=715:*&output-format=auto&output-quality=auto"));
        arrayAreas.add(new Gif("Cachorro se refrescando","https://img.buzzfeed.com/buzzfeed-static/static/enhanced/webdr05/2013/5/30/14/anigif_enhanced-buzz-29323-1369940299-3.gif?downsize=715:*&output-format=auto&output-quality=auto"));
        arrayAreas.add(new Gif("Oi","https://img.buzzfeed.com/buzzfeed-static/static/enhanced/webdr06/2013/5/31/10/anigif_enhanced-buzz-3734-1370010471-16.gif?downsize=715:*&output-format=auto&output-quality=auto"));
        arrayAreas.add(new Gif("Pensativa","https://img.buzzfeed.com/buzzfeed-static/static/enhanced/webdr02/2013/5/30/18/anigif_enhanced-buzz-29370-1369953821-18.gif?downsize=715:*&output-format=auto&output-quality=auto"));
        arrayAreas.add(new Gif("Auto-suficiente","https://img.buzzfeed.com/buzzfeed-static/static/enhanced/webdr03/2013/5/30/18/anigif_enhanced-buzz-898-1369953089-15.gif?downsize=715:*&output-format=auto&output-quality=auto"));
        arrayAreas.add(new Gif("Coçadinha marota","https://img.buzzfeed.com/buzzfeed-static/static/enhanced/webdr05/2013/5/30/18/anigif_enhanced-buzz-12654-1369952800-14.gif?downsize=715:*&output-format=auto&output-quality=auto"));
        arrayAreas.add(new Gif("EmbriaGATO","https://img.buzzfeed.com/buzzfeed-static/static/enhanced/webdr01/2013/5/30/14/anigif_enhanced-buzz-2385-1369939649-17.gif?downsize=715:*&output-format=auto&output-quality=auto"));
        arrayAreas.add(new Gif("Sono tranquilo","https://img.buzzfeed.com/buzzfeed-static/static/enhanced/webdr03/2013/5/30/17/anigif_enhanced-buzz-1832-1369951156-8.gif?downsize=715:*&output-format=auto&output-quality=auto"));
        arrayAreas.add(new Gif("Cansado","https://img.buzzfeed.com/buzzfeed-static/static/enhanced/webdr05/2013/5/30/16/anigif_enhanced-buzz-29323-1369944638-21.gif?downsize=715:*&output-format=auto&output-quality=auto"));
        arrayAreas.add(new Gif("DOGleiro","https://img.buzzfeed.com/buzzfeed-static/static/enhanced/webdr02/2013/5/30/18/anigif_enhanced-buzz-3389-1369954359-12.gif?downsize=715:*&output-format=auto&output-quality=auto"));
        arrayAreas.add(new Gif("Hoje não","https://i1.wp.com/biosom.com.br/blog/wp-content/uploads/2015/08/gifs-engraçados-com-animais-de-estimação.gif?resize=350%2C253&ssl=1"));
        arrayAreas.add(new Gif("DJ","https://i2.wp.com/biosom.com.br/blog/wp-content/uploads/2015/08/animais-de-estimação2.gif?resize=480%2C360"));
        arrayAreas.add(new Gif("Até mais!","https://i2.wp.com/biosom.com.br/blog/wp-content/uploads/2015/08/animais-de-estimação3.gif?resize=360%2C203"));
        arrayAreas.add(new Gif("Malhando","https://i2.wp.com/biosom.com.br/blog/wp-content/uploads/2015/08/animais-de-estimação6.gif?resize=366%2C259"));
        arrayAreas.add(new Gif("Olha aqui como faz","https://i2.wp.com/biosom.com.br/blog/wp-content/uploads/2015/08/animais-de-estimação7.gif?resize=400%2C229"));
        arrayAreas.add(new Gif("Me deixa ver TV em paz","https://i2.wp.com/biosom.com.br/blog/wp-content/uploads/2015/08/animais-de-estimação-tv.gif?resize=320%2C240"));

        adapter=new Gif_adapater(this,arrayAreas);
        lista.setAdapter(adapter);
    }

    public void vai_gif(View view){
        TextView txt=(TextView) view;
        Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
        myWebLink.setData(Uri.parse(txt.getTag().toString()));
        startActivity(myWebLink);
    }
}
