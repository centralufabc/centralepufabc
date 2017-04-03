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
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class edita_monitoria extends AppCompatActivity {
    Spinner monitorias;
    SQLiteDatabase bd;
    Cursor cursor;
    EditText nome, detalhes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edita_monitoria);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        nome=(EditText) findViewById(R.id.et_nome_monitoria_edita);
        detalhes=(EditText) findViewById(R.id.et_detalhes_monitoria_edita);
        nome.setVisibility(View.GONE);
        detalhes.setVisibility(View.GONE);

        monitorias=(Spinner) findViewById(R.id.spinner_edita);

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

        monitorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(materias.get(i).equals("Selecione uma matéria")){
                    nome.setVisibility(View.GONE);
                    detalhes.setVisibility(View.GONE);
                } else{
                    nome.setVisibility(View.VISIBLE);
                    detalhes.setVisibility(View.VISIBLE);
                    cursor=bd.rawQuery("SELECT nome,detalhes FROM monitoria  WHERE nome='"+materias.get(i).toString()+"'ORDER BY nome ASC", null);
                    cursor.moveToFirst();
                    nome.setText(cursor.getString(0));
                    detalhes.setText(cursor.getString(1));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void voltar(View view){
        Intent it=new Intent(this, monitoria.class);
        startActivity(it);
        finish();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) < 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Log.d("CDA", "onKeyDown Called");
            onBackPressed();
        }

        return super.onKeyDown(keyCode, event);
    }

    public void onBackPressed() {
        Intent it=new Intent(this, monitoria.class);
        startActivity(it);
        finish();
    }

    public void editar(View view){
        if(nome.getText().toString().equals("") || detalhes.getText().toString().equals("") || nome.getVisibility()==View.GONE || detalhes.getVisibility()==View.GONE){
            if(nome.getText().toString().equals("")) {
                nome.requestFocus();
                Toast.makeText(this,"Insira o nome da matéria",Toast.LENGTH_SHORT).show();
            } else if(detalhes.getText().toString().equals("")){
                detalhes.requestFocus();
                Toast.makeText(this,"Insira detalhes da matéria",Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(this,"Selecione uma matéria valida",Toast.LENGTH_SHORT).show();
            }
        } else{
            bd.execSQL("DELETE FROM monitoria WHERE nome='"+monitorias.getSelectedItem().toString()+"'");
            bd.execSQL("INSERT INTO monitoria VALUES('"+nome.getText().toString()+"','"+detalhes.getText().toString()+"');");
            Toast.makeText(this,"Editado com sucesso", Toast.LENGTH_SHORT);
            Intent intent = new Intent(this, monitoria.class);
            startActivity(intent);
            finish();
        }
    }

}
