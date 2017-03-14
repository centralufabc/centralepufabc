package central.centralepufabc;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class cronograma extends AppCompatActivity {

    private ListView lista;
    private ArrayList<Areas> arrayAreas=null;
    private Areas_adapter adapter=null;
    SQLiteDatabase bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cronograma);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bd = openOrCreateDatabase("CentralEPUFABCDB", Context.MODE_PRIVATE, null);

        lista=(ListView) findViewById(R.id.lista);
        arrayAreas=new ArrayList<Areas>();
        carregar_lista();


    }

    private void carregar_lista(){
        arrayAreas.add(new Areas(R.drawable.logo_humanas,"Ciências humanas"));
        arrayAreas.add(new Areas(R.drawable.logo_natureza,"Ciências da natureza"));
        arrayAreas.add(new Areas(R.drawable.logo_linguagens,"Linguagens"));
        arrayAreas.add(new Areas(R.drawable.logo_matematica,"Matemática"));
        arrayAreas.add(new Areas(R.drawable.logo_redacao,"Redação"));

        adapter=new Areas_adapter(this,arrayAreas);
        lista.setAdapter(adapter);

    }



    public void area_selecionada_texto(View view){
        TextView txt=(TextView) view;
        bd.execSQL("DELETE FROM ultima_area WHERE area !='"+txt.getText()+"'");
        bd.execSQL("INSERT INTO ultima_area VALUES('"+txt.getText()+"');");
        Intent intent = new Intent(this, lista_frentes.class);
        startActivity(intent);
    }


    public void voltar(View view) {
        finish();
    }


}
