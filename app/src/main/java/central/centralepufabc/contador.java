package central.centralepufabc;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;
import java.util.TimeZone;

public class contador extends AppCompatActivity {
    TextView fraseini,dia,hora,minuto,segundo,f1,f2,f3,f4,trin;
    Calendar calendar;
    int hoje,min,d;

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
        f1=(TextView) findViewById(R.id.txt_f1);
        f2=(TextView) findViewById(R.id.txt_f2);
        f3=(TextView) findViewById(R.id.txt_f3);
        f4=(TextView) findViewById(R.id.txt_f4);
        trin=(TextView) findViewById(R.id.textView34);

        final Handler atualizador = new Handler();
        atualizador.post(new Runnable() {
            @Override
            public void run() {
                calendar = Calendar.getInstance(TimeZone.getTimeZone("Brazil/East"));
                dia.setText(String.valueOf(309 - (calendar.get(Calendar.DAY_OF_YEAR))-1));
                segundo.setText(String.valueOf(59 - calendar.get(Calendar.SECOND)));
                minuto.setText(String.valueOf(59 - calendar.get(Calendar.MINUTE)));
                hora.setText(String.valueOf(24 - calendar.get(Calendar.HOUR_OF_DAY)));
                if(hoje<=308){
                    fraseini.setText("Tempo restante para o enem:");
                    trin.setText("");
                    f1.setText("Falta tempo");
                    f2.setText("Falta tempo");
                    f3.setText("Falta tempo");
                    f4.setText("Falta tempo");
                } else{
                    fraseini.setText("");
                    trin.setText("");
                    f1.setText("É enem");
                    f1.setText("É enem");
                    f1.setText("É enem");
                    f1.setText("É enem");
                }
                atualizador.postDelayed(this, 100);
            }
    });



}
    public void voltar(View view) {
        finish();
    }
}
