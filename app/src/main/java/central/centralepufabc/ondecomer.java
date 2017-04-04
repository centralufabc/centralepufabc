package central.centralepufabc;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.TimeZone;

public class ondecomer extends AppCompatActivity {
    TextView naoru;
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ondecomer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        naoru=(TextView) findViewById(R.id.txt_o_ru);
        final Handler atualizador = new Handler();

        atualizador.post(new Runnable() {
            @Override
            public void run() {
               if(i==0){
                   naoru.setTextColor(Color.rgb(13,89,59));
                   i++;
               } else if(i==1){
                   naoru.setTextColor(Color.rgb(0,0,255));
                   i++;
               } else if (i==2){
                   naoru.setTextColor(Color.rgb(255,0,0));
                   i++;
               } else{
                   naoru.setTextColor(Color.rgb(0,0,0));
                   i=0;
               }
                atualizador.postDelayed(this, 2000);
            }
        });
    }

    public void voltar(View view){
        finish();
    }

    public void abre_ru(View view){
        boolean conectado;
        ConnectivityManager conectivtyManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected()) {
            conectado = true;
        } else {
            conectado = false;
        }
        if(conectado==true){
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse("http://proap.ufabc.edu.br/images/PDF/Cardapio.pdf"));
            startActivity(intent);}
        else{
            Toast.makeText(this,"É necessaria conexão com a internet",Toast.LENGTH_SHORT).show();
        }
        }

}
