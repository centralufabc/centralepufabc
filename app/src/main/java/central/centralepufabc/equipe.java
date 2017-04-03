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


        arrayAreas.add(new Funcionário("Anna Carla","Coordenadora e Professora de Redação",R.drawable.annacarla));

        arrayAreas.add(new Funcionário("Bruno Costa","Professor de Química",R.drawable.brunocosta));
        arrayAreas.add(new Funcionário("Bruno Gumieri","Professor de Química",R.drawable.padrao));

        arrayAreas.add(new Funcionário("Diego Almeida-Silva","Professor de Biologia",R.drawable.diegoalmeida));
        arrayAreas.add(new Funcionário("Danilo Zajac","Físico-Química (FRENTE III)",R.drawable.danilozajac));
        arrayAreas.add(new Funcionário("Gabiie Alarcon","Gramática - Redação",R.drawable.gabbiealarcon));
        arrayAreas.add(new Funcionário("Gi Toledo","Professora de redação",R.drawable.padrao));
        arrayAreas.add(new Funcionário("Guilherme Lourenção","Professor de Redação",R.drawable.guilhermelourencao));

        arrayAreas.add(new Funcionário("Guilherme Morais","English Teacher",R.drawable.guilhermemorais));

        arrayAreas.add(new Funcionário("Isabela Ribeiro","Professora de Química",R.drawable.isabelaribeiro));
        arrayAreas.add(new Funcionário("Jadis Henrique","Professor de Química",R.drawable.padrao));

        arrayAreas.add(new Funcionário("João Pedro","Profº Literatura - SBC",R.drawable.joaopedro));
        arrayAreas.add(new Funcionário("Kelvyn Páterson","Professor de Física",R.drawable.kelvynbrito));

        arrayAreas.add(new Funcionário("lehloul","Professora de História da Arte",R.drawable.lehloul));
        arrayAreas.add(new Funcionário("Luisa Martins","Professora de matemática",R.drawable.luisamartins));
        arrayAreas.add(new Funcionário("Marcelo","Redação",R.drawable.padrao));
        arrayAreas.add(new Funcionário("Nathália Binder","Professora de Biologia - Frente I",R.drawable.padrao));

        arrayAreas.add(new Funcionário("Nathalia Torres","Professora de História da Arte",R.drawable.nathaliatorres));

        arrayAreas.add(new Funcionário("Rodrigo","Professor de Física",R.drawable.rodrigo));

        arrayAreas.add(new Funcionário("Tainara Ramim","Professora de Literatura",R.drawable.padrao));
        arrayAreas.add(new Funcionário("Thales Liferson","Prof. Coord. de matemática",R.drawable.thales));
        arrayAreas.add(new Funcionário("Vinicius Coelho","Professor de Biologia",R.drawable.padrao));






        adapter=new FuncionarioAdapter(this,arrayAreas);
        lista.setAdapter(adapter);

    }

    public void voltar(View view ){finish();}

}
