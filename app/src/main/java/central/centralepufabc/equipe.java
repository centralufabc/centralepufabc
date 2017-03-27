package central.centralepufabc;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class equipe extends AppCompatActivity {
    private ListView lista;
    private ArrayList<Funcionário> arrayAreas=null;
    private FuncionarioAdapter adapter=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lista=(ListView) findViewById(R.id.list_func);
        arrayAreas=new ArrayList<Funcionário>();
        carregar_lista();
    }

    private void carregar_lista(){
        arrayAreas.add(new Funcionário("Walter White","Professor de química",R.drawable.walter));






        adapter=new FuncionarioAdapter(this,arrayAreas);
        lista.setAdapter(adapter);

    }

    public void voltar(View view ){finish();}

}
