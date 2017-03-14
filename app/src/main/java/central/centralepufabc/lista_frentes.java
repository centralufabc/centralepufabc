package central.centralepufabc;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
        bd = openOrCreateDatabase("CentralEPUFABCDB", Context.MODE_PRIVATE, null);
        cursor=bd.rawQuery("SELECT area from ultima_area",null);
        cursor.moveToFirst();
        listView=(ExpandableListView) findViewById(R.id.lista1);
        initData();
        listAdapter=new ListAdapter(this,listDataHeader,listHash);
        listView.setAdapter(listAdapter);



    }

    private void initData() {
        listDataHeader=new ArrayList<>();
        listHash=new HashMap<>();

        cursor=bd.rawQuery("SELECT frente from frentes where area='"+cursor.getString(0)+"'",null);
        cursor.moveToFirst();
        while((cursor.getPosition()+1)<=cursor.getCount()) {
            listDataHeader.add(cursor.getString(0));
            cursor.moveToNext();
        }


        List<String> um=new ArrayList<>();
        List<String> dois=new ArrayList<>();
        List<String> tres=new ArrayList<>();
        List<String> quatro=new ArrayList<>();
        List<String> cinco=new ArrayList<>();
        List<String> seis=new ArrayList<>();

        cursor.moveToFirst();
        if(cursor.getCount()>=1){
            c=bd.rawQuery("SELECT assunto from item where frente='"+cursor.getString(0)+"'",null);
            c.moveToFirst();
            while((c.getPosition()+1)<=c.getCount()){
                um.add(c.getString(0));
                c.moveToNext();
            }
            listHash.put(listDataHeader.get(0),um);
        }

        cursor.moveToNext();
        if(cursor.getCount()>=2){
            c=bd.rawQuery("SELECT assunto from item where frente='"+cursor.getString(0)+"'",null);
            c.moveToFirst();
            while((c.getPosition()+1)<=c.getCount()){
                dois.add(c.getString(0));
                c.moveToNext();
            }
            listHash.put(listDataHeader.get(1),dois);
        }

        cursor.moveToNext();
        if(cursor.getCount()>=3){
            c=bd.rawQuery("SELECT assunto from item where frente='"+cursor.getString(0)+"'",null);
            c.moveToFirst();
            while((c.getPosition()+1)<=c.getCount()){
                tres.add(c.getString(0));
                c.moveToNext();
            }
            listHash.put(listDataHeader.get(2),tres);
        }

        cursor.moveToNext();
        if(cursor.getCount()>=4){
            c=bd.rawQuery("SELECT assunto from item where frente='"+cursor.getString(0)+"'",null);
            c.moveToFirst();
            while((c.getPosition()+1)<=c.getCount()){
                quatro.add(c.getString(0));
                c.moveToNext();
            }
            listHash.put(listDataHeader.get(3),quatro);
        }

        cursor.moveToNext();
        if(cursor.getCount()>=5){
            c=bd.rawQuery("SELECT assunto from item where frente='"+cursor.getString(0)+"'",null);
            c.moveToFirst();
            while((c.getPosition()+1)<=c.getCount()){
                cinco.add(c.getString(0));
                c.moveToNext();
            }
            listHash.put(listDataHeader.get(4),cinco);
        }

        cursor.moveToNext();
        if(cursor.getCount()>=6){
            c=bd.rawQuery("SELECT assunto from item where frente='"+cursor.getString(0)+"'",null);
            c.moveToFirst();
            while((c.getPosition()+1)<=c.getCount()){
                seis.add(c.getString(0));
                c.moveToNext();
            }
            listHash.put(listDataHeader.get(5), seis);
        }




    }

    public void voltar(View view) {
        finish();
    }

}
