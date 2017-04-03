package central.centralepufabc;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class add_monitoria extends AppCompatActivity {
    EditText nome, detalhes;
    SQLiteDatabase bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_monitoria);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bd = openOrCreateDatabase("CentralEPUFABCDB", Context.MODE_PRIVATE, null);

        nome=(EditText) findViewById(R.id.et_nome_monitoria);
        detalhes=(EditText) findViewById(R.id.et_detalhes_monitoria);
    }

    public void salvar (View view){
        if(!(nome.getText().toString().equals("") || detalhes.getText().toString().equals(""))){
            bd.execSQL("INSERT INTO monitoria VALUES('"+nome.getText().toString()+"','"+detalhes.getText().toString()+"');");
            Intent intent = new Intent(this, monitoria.class);
            startActivity(intent);
            finish();
        } else{
            if(nome.getText().toString().equals("")){
                Toast.makeText(this,"Insira o nome da matéria",Toast.LENGTH_SHORT).show();
                nome.requestFocus();
            } else{
                Toast.makeText(this,"Insira os detalhes",Toast.LENGTH_SHORT).show();
                detalhes.requestFocus();
            }
        }
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

}
