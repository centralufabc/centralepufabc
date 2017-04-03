package central.centralepufabc;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class excluir_monitoria extends AppCompatActivity {
    Spinner monitorias;
    SQLiteDatabase bd;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excluir_monitoria);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        monitorias=(Spinner) findViewById(R.id.spinner_exclui);

        bd = openOrCreateDatabase("CentralEPUFABCDB", Context.MODE_PRIVATE, null);
        final List<String> materias = new ArrayList<String>();
        final ArrayAdapter lista_materias = new ArrayAdapter(this, R.layout.spinner_items, materias);

        cursor = bd.rawQuery("SELECT nome FROM monitoria ORDER BY nome ASC", null);

        materias.add("Selecione uma matéria");

        if(cursor.getCount()>0) {
            cursor.moveToFirst();
            while (!cursor.isLast()) {
                if (!materias.contains(cursor.getString(0))) {
                    materias.add(cursor.getString(0));
                }
                cursor.moveToNext();
            }
            if (!materias.contains(cursor.getString(0))) {
                materias.add(cursor.getString(0));
            }
        }
        monitorias.setAdapter(lista_materias);


    }

    public void excluir(View view){
        if(monitorias.getSelectedItem().toString().equals("Selecione uma matéria")){
            Toast.makeText(this,"Selecione uma matéria",Toast.LENGTH_LONG).show();
        } else{
            bd.execSQL("DELETE FROM monitoria WHERE nome='"+monitorias.getSelectedItem().toString()+"'");
            Intent it=new Intent(this, monitoria.class);
            startActivity(it);
            finish();
        }
    }

    public void voltar(View view){
        Intent it=new Intent(this, monitoria.class);
        startActivity(it);
        finish();
    }

}
