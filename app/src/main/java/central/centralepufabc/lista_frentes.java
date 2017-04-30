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

        listDataHeader.add("Ciências da natureza");
        listDataHeader.add("Ciências humanas");
        listDataHeader.add("Linguagens");
        listDataHeader.add("Matemática");
        listDataHeader.add("Redação");



        List<String> um=new ArrayList<>();
        List<String> dois=new ArrayList<>();
        List<String> tres=new ArrayList<>();
        List<String> quatro=new ArrayList<>();
        List<String> cinco=new ArrayList<>();


        um.add("Física - Mecânica");
        um.add("Física - Termologia");
        um.add("Física - Eletromagnetismo");
        listHash.put(listDataHeader.get(0),um);

        dois.add("História - História do Brasil");
        dois.add("História - História geral");
        dois.add("Geografia - Geofísica");
        dois.add("Geografia - Geopolítica");
        dois.add("Sociologia");
        dois.add("Filosofia");
        listHash.put(listDataHeader.get(1),dois);
        tres.add("História da arte");
        tres.add("Inglês");
        tres.add("Literatura");
        listHash.put(listDataHeader.get(2),tres);
        quatro.add("Álgebra I");
        quatro.add("Álgebra II");
        quatro.add("Geometria I");
        quatro.add("Geometria II");
        quatro.add("Aula de exercícios");
        listHash.put(listDataHeader.get(3),quatro);
        cinco.add("Redação");
        listHash.put(listDataHeader.get(4),cinco);


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
