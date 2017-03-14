package central.centralepufabc;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class alunos extends AppCompatActivity {
    private ListView lista;
    private ArrayList<Aluno> arrayAreas=null;
    private Aluno_adapter adapter=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alunos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lista=(ListView) findViewById(R.id.lista_alunos);
        arrayAreas=new ArrayList<Aluno>();
        carregar_lista();
    }

    private void carregar_lista(){
        arrayAreas.add(new Aluno("Djanary Falkenstein. Engenharia de (?), UFABC","Todos os ex-alunos abaixo estão dispostos a tirar dúvidas sobre vestibular, universidade, curso e afins. Para entrar em contato, basta tocar no nome do ex-aluno desejado.",R.drawable.dj));
        arrayAreas.add(new Aluno("Apesar das excelentes aulas de desenvolvimento textual que tive na EP, não consigo desenvolver uma frase descente pra colocar aqui","Djanary Falkenstein. Engenharia de (?), UFABC",R.drawable.dj));
        arrayAreas.add(new Aluno("A EPUFABC mudou minha vida. Graças a ela eu estou na universidade atualmente","Kleverson Nascimento. Ciência da computação, UFABC",R.drawable.kleverson_nascimento));




        adapter=new Aluno_adapter(this,arrayAreas);
        lista.setAdapter(adapter);

    }

    public void voltar(View view) {
        finish();
    }

}
