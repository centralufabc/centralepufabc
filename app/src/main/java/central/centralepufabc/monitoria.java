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
import android.widget.ListView;

import java.util.ArrayList;

public class monitoria extends AppCompatActivity {
    private ListView lista;
    private ArrayList<Monitoria_obj> arrayAreas=null;
    private Monitoria_adapter adapter=null;
    SQLiteDatabase bd;
    Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoria);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bd = openOrCreateDatabase("CentralEPUFABCDB", Context.MODE_PRIVATE, null);
        lista=(ListView) findViewById(R.id.list_monitoria);
        arrayAreas=new ArrayList<Monitoria_obj>();
        carregar_lista();

    }

    public void carregar_lista(){
        cursor=bd.rawQuery("SELECT nome,detalhes FROM monitoria order by nome", null);
        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            while(!cursor.isLast()){
                arrayAreas.add(new Monitoria_obj(cursor.getString(0),cursor.getString(1)));
                cursor.moveToNext();
            }
            cursor.moveToLast();
            arrayAreas.add(new Monitoria_obj(cursor.getString(0),cursor.getString(1)));
        }

        adapter=new Monitoria_adapter(this,arrayAreas);
        lista.setAdapter(adapter);
    }

    public void adicionar_monitoria(View view){
        Intent intent = new Intent(this, add_monitoria.class);
        startActivity(intent);
        finish();
    }

    public void voltar(View view){
        finish();
    }

    public void editar_mon(View view){
        Intent it=new Intent(this, edita_monitoria.class);
        startActivity(it);
        finish();
    }

    public void excluir_mon(View view){
        Intent it=new Intent(this, excluir_monitoria.class);
        startActivity(it);
        finish();
    }
}
