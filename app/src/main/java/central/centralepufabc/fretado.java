package central.centralepufabc;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import android.widget.Toast;

import static central.centralepufabc.R.id.emtu;


public class fretado extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fretado);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView linha1 = (TextView) findViewById(R.id.linha1);
        TextView linha5 = (TextView) findViewById(R.id.linha5);
        TextView interunidades = (TextView) findViewById(R.id.interunidades);
        TextView expresso = (TextView) findViewById(R.id.expresso);
        TextView linha1e2 = (TextView) findViewById(R.id.linha_1e2);
        TextView metro = (TextView) findViewById(R.id.metro);
        TextView emtu=(TextView) findViewById(R.id.emtu);

        linha1.setOnClickListener(this);
        linha5.setOnClickListener(this);
        interunidades.setOnClickListener(this);
        expresso.setOnClickListener(this);
        linha1e2.setOnClickListener(this);
        metro.setOnClickListener(this);
        emtu.setOnClickListener(this);

    }

    public void onClick(View view) {
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

        switch (view.getId()){

            case R.id.linha5:
                intent.setData(Uri.parse("http://pu.ufabc.edu.br/images/transportes/linhas%2001%20e%2005-publicado.pdf"));
                break;
            case R.id.linha1:
                intent.setData(Uri.parse("http://pu.ufabc.edu.br/images/transportes/linhas%2001%20e%2005-publicado.pdf"));
                break;
            case R.id.interunidades:
                intent.setData(Uri.parse("http://pu.ufabc.edu.br/images/transportes/linhas%2002-03-04-publicado.pdf"));
                break;
            case R.id.expresso:
                intent.setData(Uri.parse("http://pu.ufabc.edu.br/images/transportes/linha%20expresso-publicado.pdf"));
                break;
            case R.id.linha_1e2:
                intent.setData(Uri.parse("http://pu.ufabc.edu.br/images/transportes/linhas%2001%20e%2002%20-%20sbado-publicado.pdf"));
                break;
            case R.id.metro:
                intent.setData(Uri.parse("http://www.metro.sp.gov.br/redes/mapa.pdf"));
                break;
            case emtu:
                intent.setData(Uri.parse("http://www.emtu.sp.gov.br/emtu/images/conteudo/corredor_abd_grande.jpg"));
        }
        startActivity(intent);}
        else{
            Toast.makeText(this,"É necessaria conexão com a internet",Toast.LENGTH_SHORT).show();
        }
    }




    public void voltar(View view) {
        finish();
    }

}
