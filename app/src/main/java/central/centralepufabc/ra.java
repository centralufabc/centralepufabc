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
import android.widget.EditText;

public class ra extends AppCompatActivity {

    SQLiteDatabase bd;
    Cursor cursor;
    EditText ra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ra);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bd = openOrCreateDatabase("CentralEPUFABCDB", Context.MODE_PRIVATE, null);

        ra=(EditText) findViewById(R.id.editText);

        cursor = bd.rawQuery("SELECT numero FROM ra", null);
        cursor.moveToLast();
        ra.setText(cursor.getString(0));

    }

    public void voltar(View view) {
        Intent it=new Intent(this, MainActivity.class);
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
        Intent it=new Intent(this, MainActivity.class);
        startActivity(it);
        finish();
    }

    public void salvar_ra(View view){
        bd.execSQL("INSERT INTO ra VALUES('"+ra.getText().toString()+"');");
        Intent it=new Intent(this, MainActivity.class);
        startActivity(it);
        finish();
    }

}
