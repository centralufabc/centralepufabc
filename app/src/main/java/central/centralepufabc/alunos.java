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
        arrayAreas.add(new Aluno("Nome do aluno. Engenharia de (?), UFABC","Todos os ex-alunos abaixo estão dispostos a tirar dúvidas sobre vestibular, universidade, curso e afins. Para entrar em contato, basta tocar no nome do ex-aluno desejado.",R.drawable.dj));
        arrayAreas.add(new Aluno("Este espaço é destinado para que você possa contar como foi sua experiência no cursinho, como está sendo na universidade, o que a EP representa pra você, enfim, qualquer coisa que você queira dizer, desde um textão até um simples 'A EPUFABC é top!'","Nome do(a) aluno(a). Curso que faz, Universidade que estuda",R.drawable.padrao));
        arrayAreas.add(new Aluno("Este espaço é destinado para que você possa contar como foi sua experiência no cursinho, como está sendo na universidade, o que a EP representa pra você, enfim, qualquer coisa que você queira dizer, desde um textão até um simples 'A EPUFABC é top!'","Nome do(a) aluno(a). Curso que faz, Universidade que estuda",R.drawable.padrao));
        arrayAreas.add(new Aluno("Este espaço é destinado para que você possa contar como foi sua experiência no cursinho, como está sendo na universidade, o que a EP representa pra você, enfim, qualquer coisa que você queira dizer, desde um textão até um simples 'A EPUFABC é top!'","Nome do(a) aluno(a). Curso que faz, Universidade que estuda",R.drawable.padrao));
        arrayAreas.add(new Aluno("Este espaço é destinado para que você possa contar como foi sua experiência no cursinho, como está sendo na universidade, o que a EP representa pra você, enfim, qualquer coisa que você queira dizer, desde um textão até um simples 'A EPUFABC é top!'","Nome do(a) aluno(a). Curso que faz, Universidade que estuda",R.drawable.padrao));



        adapter=new Aluno_adapter(this,arrayAreas);
        lista.setAdapter(adapter);

    }

    public void voltar(View view) {
        finish();
    }

}
