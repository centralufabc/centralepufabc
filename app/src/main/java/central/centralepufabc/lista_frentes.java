package central.centralepufabc;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class lista_frentes extends AppCompatActivity {

    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHash;
    SQLiteDatabase bd;
    Cursor cursor,c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_frentes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView=(ExpandableListView) findViewById(R.id.lista1);
        initData();
        listAdapter=new ListAdapter(this,listDataHeader,listHash);
        listView.setAdapter(listAdapter);



    }

    private void initData() {
        listDataHeader=new ArrayList<>();
        listHash=new HashMap<>();
        listDataHeader.add("Atendimento geral");
        listDataHeader.add("Ciências da natureza");
        listDataHeader.add("Ciências humanas");
        listDataHeader.add("Linguagens");
        listDataHeader.add("Matemática");
        listDataHeader.add("Redação");


        List<String> zero=new ArrayList<>();
        List<String> um=new ArrayList<>();
        List<String> dois=new ArrayList<>();
        List<String> tres=new ArrayList<>();
        List<String> quatro=new ArrayList<>();
        List<String> cinco=new ArrayList<>();

        zero.add("Atendimento geral-3wizpa");
        listHash.put(listDataHeader.get(0),zero);

        um.add("Biologia, Santo André, Vespertino-d3ic8i");
        um.add("Biologia, Santo André, Noturno-bjfcuw");
        um.add("Biologia, São Bernardo, Vespertino-y5rxwd");

        um.add("Física, Santo André, Vespertino-dpikks");
        um.add("Física, Santo André, Noturno-w8mpgf");
        um.add("Física, São Bernardo, Vespertino-2ayk5i");

        um.add("Química, Santo André, Vespertino-psm6nw");
        um.add("Química, Santo André, Noturno-48qixq");
        um.add("Química, São Bernardo, Vespertino-tzq2df");


        listHash.put(listDataHeader.get(1),um);

        dois.add("Filosofia, Santo André, Vespertino-yhfyh3");
        dois.add("Filosofia, Santo André, Noturno-a58kcx");
        dois.add("Filosofia, São Bernardo, Vespertino-jwxk3p");

        dois.add("Geografia, Santo André, Vespertino-ga27uj");
        dois.add("Geografia, Santo André, Noturno-c9iqhb");
        dois.add("Geografia, São Bernardo, Vespertino-2im6zr");

        dois.add("História, Santo André, Vespertino-rvx2ns");
        dois.add("História, Santo André, Noturno-evfhux");
        dois.add("História, São Bernardo, Vespertino-895xye");

        dois.add("História da Arte, Santo André, Vespertino-pn2ynq");
        dois.add("História da Arte, Santo André, Noturno-nu9r4g");
        dois.add("História da Arte, São Bernardo, Vespertino-eqeqkg");


        dois.add("Sociologia, Santo André, Vespertino-7ijg9s");
        dois.add("Sociologia, Santo André, Noturno-t3fws6");
        dois.add("Sociologia, São Bernardo, Vespertino-5sifuj");





        listHash.put(listDataHeader.get(2),dois);

        tres.add("Gramática, Santo André, Vespertino-bns6bh");
        tres.add("Gramática, Santo André, Noturno-6gte4b");
        tres.add("Gramática, São Bernardo, Vespertino-bnv2iz");
        tres.add("Inglês, Santo André, Vespertino-3jw33z");
        tres.add("Inglês, Santo André, Noturno-8fwrsb");
        tres.add("Inglês, São Bernardo, Vespertino-a4t76d");
        tres.add("Literatura, Santo André, Vespertino-hxz6bt");
        tres.add("Literatura, Santo André, Noturno-73ju3q");
        tres.add("Literatura, São Bernardo, Vespertino-3xb9x7");
        listHash.put(listDataHeader.get(3),tres);
        quatro.add("Matemática, Santo André, Vespertino-5tzrhr");
        quatro.add("Matemática, Santo André, Noturno-2ra7gx");
        quatro.add("Matemática, São Bernardo, Vespertino-7mif8i");
        listHash.put(listDataHeader.get(4),quatro);
        cinco.add("Redação, Santo Andre, Vespertino-8zr8wb");
        cinco.add("Redação, Santo Andre, Noturno-7xqczt");
        cinco.add("Redação, São Bernardo, Vespertino-5px7fv");
        listHash.put(listDataHeader.get(5),cinco);


    }

    public void abrir_edmodo(View view){
        PackageManager pm = getPackageManager();
        try {
            Intent intent = pm.getLaunchIntentForPackage("com.fusionprojects.edmodo.apk");
            startActivity(intent);

        } catch (Exception ex){
            Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
            myWebLink.setData(Uri.parse("http://edmodo.com"));
            startActivity(myWebLink);

        }
    }


    public void voltar(View view) {
        finish();
    }

}
