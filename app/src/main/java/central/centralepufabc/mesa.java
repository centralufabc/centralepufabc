package central.centralepufabc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class mesa extends AppCompatActivity {
    private ListView lista;
    private ArrayList<Estudo> arrayEstudo=null;
    private Estudo_adapter adapter=null;
    SQLiteDatabase bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bd = openOrCreateDatabase("CentralEPUFABCDB", Context.MODE_PRIVATE, null);

        lista=(ListView) findViewById(R.id.lis_estudo);
        arrayEstudo=new ArrayList<Estudo>();
        carregar_lista();

    }

    private void carregar_lista(){
        arrayEstudo.add(new Estudo("Frações","É a representação da parte de um todo (de um ou mais inteiros), assim, podemos considerá-la como sendo mais uma representação de quantidade, ou seja, uma representação numérica, com ela podemos efetuar todas as operações como: adição, subtração, multiplicação, divisão, potenciação, radiciação."));
        arrayEstudo.add(new Estudo("Artigo de opinião","O artigo de opinião é um tipo de texto dissertativo-argumentativo. Nele, o autor tem a finalidade de apresentar determinado tema e seu ponto de vista, e por isso, recebe esse nome.\n" +
                "Possui as características de um texto jornalístico e tem como principal objetivo informar e persuadir o leitor sobre um assunto. "));


        adapter=new Estudo_adapter(this,arrayEstudo);
        lista.setAdapter(adapter);

    }

    public void voltar(View view) {
        finish();
    }

}
