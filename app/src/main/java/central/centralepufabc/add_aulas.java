package central.centralepufabc;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.delay;

public class add_aulas extends AppCompatActivity {
    Spinner t;
    SQLiteDatabase bd;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_aulas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        t=(Spinner) findViewById(R.id.spinner_turma_add);

        bd = openOrCreateDatabase("CentralEPUFABCDB", Context.MODE_PRIVATE, null);
        final List<String> turmas = new ArrayList<String>();
        final ArrayAdapter lista_turmas = new ArrayAdapter(this, R.layout.spinner_items, turmas);
        turmas.add("Selecione uma turma");
        turmas.add("Turma 1 - Tarde - SA");
        turmas.add("Turma 2 - Tarde - SA");
        turmas.add("Turma 1 - Noite - SA");
        turmas.add("Turma 2 - Noite - SA");
        turmas.add("Turma 1 - Tarde - SBC");
        turmas.add("Turma 2 - Tarde - SBC");
        t.setAdapter(lista_turmas);

    }

    public void adicionar(View view){
        if(!t.getSelectedItem().toString().equals("Selecione uma turma")) {
            cursor = bd.rawQuery("SELECT nome_materia,campus,dia,nome_prof,sala,bloco,frequencia,hora_inicio,hora_fim FROM todas_turmas WHERE turma='"+t.getSelectedItem().toString()+"'ORDER BY nome_materia ASC", null);
            cursor.moveToFirst();
            while(!cursor.isLast()){
                bd.execSQL("INSERT INTO aulas VALUES('"+cursor.getString(0)+"','"+cursor.getString(1)+"','"+cursor.getString(2)+"','"+cursor.getString(3)+"','"+cursor.getString(4)+"','"+cursor.getString(5)+"','"+cursor.getString(6)+"','"+cursor.getInt(7)+"','"+cursor.getString(8)+"');");
                cursor.moveToNext();
            }
            cursor.moveToLast();
            bd.execSQL("INSERT INTO aulas VALUES('"+cursor.getString(0)+"','"+cursor.getString(1)+"','"+cursor.getString(2)+"','"+cursor.getString(3)+"','"+cursor.getString(4)+"','"+cursor.getString(5)+"','"+cursor.getString(6)+"','"+cursor.getInt(7)+"','"+cursor.getString(8)+"');");


            Intent it = new Intent(this, aulas.class);
            startActivity(it);
            finish();
        } else{
            showMessage("Erro", "Selecione uma matéria para excluir");
            t.requestFocus();
        }
    }

    public void showMessage(String Title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(Title);
        builder.setMessage(Message);
        builder.show();

    }

    public void voltar(View view) {
        Intent it=new Intent(this, aulas.class);
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
        Intent it=new Intent(this, aulas.class);
        startActivity(it);
        finish();
    }
}
