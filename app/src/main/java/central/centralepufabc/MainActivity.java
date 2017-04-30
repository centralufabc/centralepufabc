package central.centralepufabc;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import static android.content.Context.ALARM_SERVICE;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView hora_refresh,txt_foi,unidade,linha,minuto,quinzenal,nome_aula,nome_prof,hora_aula,local_aula;
    ImageButton avancar,voltar,inverte,voltar_aula,avancar_aula;
    Spinner partida, chegada;
    SQLiteDatabase bd;
    Calendar calendar;
    Cursor cursor,gerencia_aula,fb;
    int hora_atual,minuto_atual,dia,linha_min,linha_max, foi,x,controle;
    LinearLayout tempoRestanteLayout;
    String nome_tabela,localp,locald,pre_linha,inv,adapter,dia_consulta;
    DatabaseReference sa_leste_sa,linha2_sa,linha5_se,sa_leste_se,inter_se,dias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //FRETADO
        hora_refresh=(TextView) findViewById(R.id.txt_atualizado);
        minuto=(TextView) findViewById(R.id.txt_min);
        tempoRestanteLayout = (LinearLayout) findViewById(R.id.tempo_restante_layout);
        linha=(TextView) findViewById(R.id.txt_linha);
        unidade=(TextView) findViewById(R.id.txt_unidade);
        avancar=(ImageButton) findViewById(R.id.bt_avancar);
        voltar=(ImageButton) findViewById(R.id.bt_voltar);
        txt_foi = (TextView) findViewById(R.id.txt_foi);
        inverte = (ImageButton) findViewById(R.id.inverte_local);
        //

        //AULAS
        quinzenal=(TextView) findViewById(R.id.txt_quinzenal);
        nome_aula=(TextView) findViewById(R.id.txt_Nome_aula);
        hora_aula=(TextView) findViewById(R.id.txt_horário);
        nome_prof=(TextView) findViewById(R.id.txt_nome_prof);
        local_aula=(TextView) findViewById(R.id.txt_sala);
        voltar_aula=(ImageButton) findViewById(R.id.bt_voltar_aula);
        avancar_aula=(ImageButton) findViewById(R.id.bt_avancar_aula);

        //broadcast
        Intent it=new Intent("Alarme");
        PendingIntent p=PendingIntent.getBroadcast(this,0,it,0);

        Calendar c=Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());

        AlarmManager alarme=(AlarmManager) getSystemService(ALARM_SERVICE);
        alarme.setRepeating(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),60000,p);
        //

        criarbancodedados();// nome auto-explicativo


        calendar = Calendar.getInstance(TimeZone.getTimeZone("Brazil/East"));//configurar calendário

        dia=calendar.get(Calendar.DAY_OF_WEEK);

        //FIREBASE
        sa_leste_sa= FirebaseDatabase.getInstance().getReference().getRoot().child("Fretados").child("Sábado-SA-Leste");
        sa_leste_sa.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String msg=dataSnapshot.getValue().toString();
                String array[] = new String[4];
                array = msg.split(",");
                //SA-LESTE/LESTE-SA/LINHA/INDICE
                fb = bd.rawQuery("SELECT * FROM fretados_sabado_1 WHERE indice=='"+Integer.parseInt(array[3])+"' ORDER BY indice ASC", null);
                if(fb.getCount()==0) {
                    bd.execSQL("INSERT INTO fretados_sabado_1 VALUES('" + Integer.parseInt(array[0]) + "','" + Integer.parseInt(array[1]) + "','" + Integer.parseInt(array[2]) + "','" + Integer.parseInt(array[3]) + "');");
                }


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String msg=dataSnapshot.getValue().toString();
                String array[] = new String[4];
                array = msg.split(",");
                //SA-LESTE/LESTE-SA/LINHA/INDICE

                fb = bd.rawQuery("SELECT * FROM fretados_sabado_1 WHERE indice=='"+Integer.parseInt(array[3])+"' ORDER BY indice ASC", null);
                if(fb.getCount()!=0) {
                    bd.execSQL("DELETE FROM fretados_sabado_1 WHERE indice='" +Integer.parseInt(array[3]) + "'");
                    bd.execSQL("INSERT INTO fretados_sabado_1 VALUES('" + Integer.parseInt(array[0]) + "','" + Integer.parseInt(array[1]) + "','" + Integer.parseInt(array[2]) + "','" + Integer.parseInt(array[3]) + "');");
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String msg=dataSnapshot.getValue().toString();
                String array[] = new String[4];
                array = msg.split(",");
                //SA-LESTE/LESTE-SA/LINHA/INDICE
                fb = bd.rawQuery("SELECT * FROM fretados_sabado_1 WHERE indice=='"+Integer.parseInt(array[3])+"' ORDER BY indice ASC", null);
                if(fb.getCount()!=0) {
                    bd.execSQL("DELETE FROM fretados_sabado_1 WHERE indice='" +Integer.parseInt(array[3]) + "'");
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        linha2_sa=FirebaseDatabase.getInstance().getReference().getRoot().child("Fretados").child("Sábado-Linha2");
        linha2_sa.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String msg=dataSnapshot.getValue().toString();
                String array[] = new String[6];
                array = msg.split(",");
                fb = bd.rawQuery("SELECT * FROM fretados_sabado_2 WHERE indice=='"+Integer.parseInt(array[5])+"' ORDER BY indice ASC", null);
                if(fb.getCount()==0) {
                    bd.execSQL("INSERT INTO fretados_sabado_2 VALUES('" + Integer.parseInt(array[0]) + "','" + Integer.parseInt(array[1]) + "','" + Integer.parseInt(array[2]) + "','" + Integer.parseInt(array[3]) + "','"+Integer.parseInt(array[4])+"','"+Integer.parseInt(array[5])+"');");
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String msg=dataSnapshot.getValue().toString();
                String array[] = new String[6];
                array = msg.split(",");
                fb = bd.rawQuery("SELECT * FROM fretados_sabado_2 WHERE indice=='"+Integer.parseInt(array[5])+"' ORDER BY indice ASC", null);
                if(fb.getCount()!=0) {
                    bd.execSQL("DELETE FROM fretados_sabado_2 WHERE indice='" +Integer.parseInt(array[5]) + "'");
                    bd.execSQL("INSERT INTO fretados_sabado_2 VALUES('" + Integer.parseInt(array[0]) + "','" + Integer.parseInt(array[1]) + "','" + Integer.parseInt(array[2]) + "','" + Integer.parseInt(array[3]) + "','"+Integer.parseInt(array[4])+"','"+Integer.parseInt(array[5])+"');");
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String msg=dataSnapshot.getValue().toString();
                String array[] = new String[6];
                array = msg.split(",");
                fb = bd.rawQuery("SELECT * FROM fretados_sabado_2 WHERE indice=='"+Integer.parseInt(array[5])+"' ORDER BY indice ASC", null);
                if(fb.getCount()!=0) {
                    bd.execSQL("DELETE FROM fretados_sabado_2 WHERE indice='" +Integer.parseInt(array[5]) + "'");
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        linha5_se=FirebaseDatabase.getInstance().getReference().getRoot().child("Fretados").child("Semanal-linha5");
        linha5_se.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String msg=dataSnapshot.getValue().toString();
                String array[] = new String[6];
                array = msg.split(",");
                fb = bd.rawQuery("SELECT * FROM fretados_semanal_5 WHERE indice=='"+Integer.parseInt(array[5])+"' ORDER BY indice ASC", null);
                if(fb.getCount()==0) {
                    bd.execSQL("INSERT INTO fretados_semanal_5 VALUES('" + Integer.parseInt(array[0]) + "','" + Integer.parseInt(array[1]) + "','" + Integer.parseInt(array[2]) + "','" + Integer.parseInt(array[3]) + "','"+Integer.parseInt(array[4])+"','"+Integer.parseInt(array[5])+"');");
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String msg=dataSnapshot.getValue().toString();
                String array[] = new String[6];
                array = msg.split(",");
                fb = bd.rawQuery("SELECT * FROM fretados_semanal_5 WHERE indice=='"+Integer.parseInt(array[5])+"' ORDER BY indice ASC", null);
                if(fb.getCount()!=0) {
                    bd.execSQL("DELETE FROM fretados_semanal_5 WHERE indice='" +Integer.parseInt(array[5]) + "'");
                    bd.execSQL("INSERT INTO fretados_semanal_5 VALUES('" + Integer.parseInt(array[0]) + "','" + Integer.parseInt(array[1]) + "','" + Integer.parseInt(array[2]) + "','" + Integer.parseInt(array[3]) + "','"+Integer.parseInt(array[4])+"','"+Integer.parseInt(array[5])+"');");
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String msg=dataSnapshot.getValue().toString();
                String array[] = new String[6];
                array = msg.split(",");
                fb = bd.rawQuery("SELECT * FROM fretados_semanal_5 WHERE indice=='"+Integer.parseInt(array[5])+"' ORDER BY indice ASC", null);
                if(fb.getCount()!=0) {
                    bd.execSQL("DELETE FROM fretados_semanal_5 WHERE indice='" +Integer.parseInt(array[5]) + "'");
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        sa_leste_se=FirebaseDatabase.getInstance().getReference().getRoot().child("Fretados").child("Semanal-leste-sa");
        sa_leste_se.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String msg=dataSnapshot.getValue().toString();
                String array[] = new String[4];
                array = msg.split(",");
                fb = bd.rawQuery("SELECT * FROM fretados_semanal_1 WHERE indice=='"+Integer.parseInt(array[3])+"' ORDER BY indice ASC", null);
                if(fb.getCount()==0) {
                    bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('" + Integer.parseInt(array[0]) + "','" + Integer.parseInt(array[1]) + "','" + Integer.parseInt(array[2]) + "','" + Integer.parseInt(array[3])+"');");
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String msg=dataSnapshot.getValue().toString();
                String array[] = new String[4];
                array = msg.split(",");
                fb = bd.rawQuery("SELECT * FROM fretados_semanal_1 WHERE indice=='"+Integer.parseInt(array[3])+"' ORDER BY indice ASC", null);
                if(fb.getCount()!=0) {
                    bd.execSQL("DELETE FROM fretados_semanal_1 WHERE indice='" +Integer.parseInt(array[3]) + "'");
                    bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('" + Integer.parseInt(array[0]) + "','" + Integer.parseInt(array[1]) + "','" + Integer.parseInt(array[2]) + "','" + Integer.parseInt(array[3])+"');");
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String msg=dataSnapshot.getValue().toString();
                String array[] = new String[4];
                array = msg.split(",");
                fb = bd.rawQuery("SELECT * FROM fretados_semanal_1 WHERE indice=='"+Integer.parseInt(array[3])+"' ORDER BY indice ASC", null);
                if(fb.getCount()!=0) {
                    bd.execSQL("DELETE FROM fretados_semanal_1 WHERE indice='" +Integer.parseInt(array[3]) + "'");
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        inter_se=FirebaseDatabase.getInstance().getReference().getRoot().child("Fretados").child("Semanal-2-3-4");
        inter_se.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String msg=dataSnapshot.getValue().toString();
                String array[] = new String[5];
                array = msg.split(",");
                fb = bd.rawQuery("SELECT * FROM fretados_semanal_2_3_4_exp WHERE indice=='"+Integer.parseInt(array[4])+"' ORDER BY indice ASC", null);
                if(fb.getCount()==0) {
                    bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('" + Integer.parseInt(array[0]) + "','" + Integer.parseInt(array[1]) + "','" + Integer.parseInt(array[2]) + "','" + Integer.parseInt(array[3])+"','" + Integer.parseInt(array[4])+"');");
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String msg=dataSnapshot.getValue().toString();
                String array[] = new String[5];
                array = msg.split(",");
                fb = bd.rawQuery("SELECT * FROM fretados_semanal_2_3_4_exp WHERE indice=='"+Integer.parseInt(array[4])+"' ORDER BY indice ASC", null);
                if(fb.getCount()!=0) {
                    bd.execSQL("DELETE FROM fretados_semanal_2_3_4_exp WHERE indice='" +Integer.parseInt(array[4]) + "'");
                    bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('" + Integer.parseInt(array[0]) + "','" + Integer.parseInt(array[1]) + "','" + Integer.parseInt(array[2]) + "','" + Integer.parseInt(array[3])+"','" + Integer.parseInt(array[4])+"');");
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String msg=dataSnapshot.getValue().toString();
                String array[] = new String[5];
                array = msg.split(",");
                fb = bd.rawQuery("SELECT * FROM fretados_semanal_2_3_4_exp WHERE indice=='"+Integer.parseInt(array[4])+"' ORDER BY indice ASC", null);
                if(fb.getCount()!=0) {
                    bd.execSQL("DELETE FROM fretados_semanal_2_3_4_exp WHERE indice='" +Integer.parseInt(array[4]) + "'");
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        dias=FirebaseDatabase.getInstance().getReference().getRoot().child("Dias importantes");
        dias.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String msg=dataSnapshot.getValue().toString();
                String array[] = new String[7];
                array = msg.split(",");
                fb = bd.rawQuery("SELECT * FROM dias WHERE indice=='"+Integer.parseInt(array[6])+"' ORDER BY indice ASC", null);
                if(fb.getCount()==0) {
                    bd.execSQL("INSERT INTO dias VALUES('" + array[0].toString() + "','" + Integer.parseInt(array[1]) + "','" + array[2].toString() + "','" + array[3].toString()+"','" + array[4].toString()+"','" + array[5].toString()+"','" + Integer.parseInt(array[6])+"');");
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String msg=dataSnapshot.getValue().toString();
                String array[] = new String[7];
                array = msg.split(",");
                fb = bd.rawQuery("SELECT * FROM dias WHERE indice=='"+Integer.parseInt(array[6])+"' ORDER BY indice ASC", null);
                if(fb.getCount()!=0) {
                    bd.execSQL("DELETE FROM dias WHERE indice='" +Integer.parseInt(array[6]) + "'");
                    bd.execSQL("INSERT INTO dias VALUES('" + array[0].toString() + "','" + Integer.parseInt(array[1]) + "','" + array[2].toString() + "','" + array[3].toString()+"','" + array[4].toString()+"','" + array[5].toString()+"','" + Integer.parseInt(array[6])+"');");
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String msg=dataSnapshot.getValue().toString();
                String array[] = new String[7];
                array = msg.split(",");
                fb = bd.rawQuery("SELECT * FROM dias WHERE indice=='"+Integer.parseInt(array[6])+"' ORDER BY indice ASC", null);
                if(fb.getCount()!=0) {
                    bd.execSQL("DELETE FROM dias WHERE indice='" +Integer.parseInt(array[6]) + "'");
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //
        /////////////////

        rar();
        preencher_aulinhas();


        inv="";
        cursor = bd.rawQuery("SELECT * FROM local_salvo ORDER BY indice ASC", null);
        cursor.moveToFirst();
        localp= cursor.getString(0);
        cursor.moveToNext();
        locald=cursor.getString(0);
        atualizarhorafretado(localp,locald);

        final List<String> destino_SA = new ArrayList<String>();
        if(locald.equals("Terminal leste") || locald.equals("São Bernardo") || locald.equals("Terminal São Bernardo")) {
            destino_SA.add(locald);
        }
        if(!locald.equals("Terminal leste")) {
            destino_SA.add("Terminal leste");
        }
        if(!locald.equals("São Bernardo")) {
            destino_SA.add("São Bernardo");
        }
        if(!locald.equals("Terminal São Bernardo")) {
            destino_SA.add("Terminal São Bernardo");
        }
        final ArrayAdapter adapter_SA = new ArrayAdapter(this, R.layout.spinner_items, destino_SA);


        final List<String> destino_TermLeste = new ArrayList<String>();
        if(locald.equals("Santo André") || locald.equals("São Bernardo") || locald.equals("Terminal São Bernardo")) {
            destino_TermLeste.add(locald);
        }
        if(!locald.equals("Santo André")) {
            destino_TermLeste.add("Santo André");
        }
        if(!locald.equals("São Bernardo")) {
            destino_TermLeste.add("São Bernardo");
        }
        if(!locald.equals("Terminal São Bernardo")) {
            destino_TermLeste.add("Terminal São Bernardo");
        }
        final ArrayAdapter adapter_TermLeste = new ArrayAdapter(this, R.layout.spinner_items, destino_TermLeste);

        final List<String> destino_SBC = new ArrayList<String>();
        if(!locald.equals("São Bernardo")) {
            destino_SBC.add(locald);
        }
        if(!locald.equals("Santo André")) {
            destino_SBC.add("Santo André");
        }
        if(!locald.equals("Terminal leste")) {
            destino_SBC.add("Terminal leste");
        }
        if(!locald.equals("Praça dos expedicionários")) {
            destino_SBC.add("Praça dos expedicionários");
        }
        if(!locald.equals("Terminal São Bernardo")) {
            destino_SBC.add("Terminal São Bernardo");
        }
        final ArrayAdapter adapter_SBC = new ArrayAdapter(this, R.layout.spinner_items, destino_SBC);

        final List<String> destino_TermSBC = new ArrayList<String>();
        if(!locald.equals("Terminal São Bernardo")) {
            destino_TermSBC.add(locald);
        }
        if(!locald.equals("São Bernardo")) {
            destino_TermSBC.add("São Bernardo");
        }
        if(!locald.equals("Praça dos expedicionários")) {
            destino_TermSBC.add("Praça dos expedicionários");
        }
        if(!locald.equals("Santo André")) {
            destino_TermSBC.add("Santo André");
        }
        if(!locald.equals("Terminal leste")) {
            destino_TermSBC.add("Terminal leste");
        }
        final ArrayAdapter adapter_TermSBC = new ArrayAdapter(this, R.layout.spinner_items, destino_TermSBC);

        final List<String> destino_Praça = new ArrayList<String>();
        if(locald.equals("São Bernardo") || locald.equals("Terminal São Bernardo")) {
            destino_Praça.add(locald);
        }
        if(!locald.equals("São Bernardo")) {
            destino_Praça.add("São Bernardo");
        }
        if(!locald.equals("Terminal São Bernardo")) {
            destino_Praça.add("Terminal São Bernardo");
        }
        final ArrayAdapter adapter_Praça = new ArrayAdapter(this, R.layout.spinner_items, destino_Praça);

        final List<String> locais_p = new ArrayList<String>();
        locais_p.add(localp);
        if(!localp.equals("Terminal leste")) {
            locais_p.add("Terminal leste");
        }
        if(!localp.equals("Santo André")) {
            locais_p.add("Santo André");
        }
        if(!localp.equals("São Bernardo")) {
            locais_p.add("São Bernardo");
        }
        if(!localp.equals("Terminal São Bernardo")) {
            locais_p.add("Terminal São Bernardo");
        }
        if(!localp.equals("Praça dos expedicionários")) {
            locais_p.add("Praça dos expedicionários");
        }

        partida = (Spinner) findViewById(R.id.spinner_partida);
        final ArrayAdapter adapter_p = new ArrayAdapter(this, R.layout.spinner_items, locais_p);
        partida.setAdapter(adapter_p);

        chegada = (Spinner) findViewById(R.id.spinner_chegada);

        if(localp.equals("Santo André")){
            chegada.setAdapter(adapter_SA);
            adapter="SA";
        } else{
            if(localp.equals("Terminal leste")){
                chegada.setAdapter(adapter_TermLeste);
                adapter="TermLeste";
            } else{
                if(localp.equals("São Bernardo")){
                    chegada.setAdapter(adapter_SBC);
                    adapter="SBC";
                } else {
                    if(localp.equals("Terminal São Bernardo")){
                        chegada.setAdapter(adapter_TermSBC);
                        adapter="TermSBC";
                    } else{
                        chegada.setAdapter(adapter_Praça);
                        adapter="Praca";
                    }
                }
            }
        }


        partida.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (locais_p.get(i).equals("Santo André")) {
                    chegada.setAdapter(adapter_SA);
                    adapter="SA";

                } else {
                    if (locais_p.get(i).equals("Terminal leste")) {
                        chegada.setAdapter(adapter_TermLeste);
                        adapter="TermLeste";
                    } else {
                        if (locais_p.get(i).equals("São Bernardo")) {
                            chegada.setAdapter(adapter_SBC);
                            adapter="SBC";
                        } else {
                            if (locais_p.get(i).equals("Terminal São Bernardo")) {
                                chegada.setAdapter(adapter_TermSBC);
                                adapter="TermSBC";
                            } else {
                                if (locais_p.get(i).equals("Praça dos expedicionários")) {
                                    chegada.setAdapter(adapter_Praça);
                                    adapter="Praca";
                                }
                            }
                        }
                    }
                }
                if(inv.equals("Vai")){
                    inverter();
                }
                localp=locais_p.get(i);
                cursor = bd.rawQuery("SELECT * FROM local_salvo ORDER BY indice ASC", null);
                cursor.moveToFirst();
                bd.execSQL("DELETE FROM local_salvo WHERE indice='1'");
                bd.execSQL("INSERT INTO local_salvo VALUES('"+localp+"','1');");
                atualizarhorafretado(localp,locald);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        chegada.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapter.equals("SA")) {
                    locald=destino_SA.get(i);
                } else {
                    if (adapter.equals("TermLeste")) {
                        locald=destino_TermLeste.get(i);
                    } else {
                        if (adapter.equals("SBC")) {
                            locald=destino_SBC.get(i);
                        } else {
                            if (adapter.equals("TermSBC")) {
                                locald=destino_TermSBC.get(i);
                            } else {
                                if (adapter.equals("Praca")) {
                                    locald=destino_Praça.get(i);
                                }
                            }
                        }
                    }
                }
                cursor = bd.rawQuery("SELECT * FROM local_salvo ORDER BY indice ASC", null);
                cursor.moveToFirst();
                cursor.moveToNext();
                bd.execSQL("DELETE FROM local_salvo WHERE indice='2'");
                bd.execSQL("INSERT INTO local_salvo VALUES('"+locald+"','2');");
                atualizarhorafretado(localp,locald);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        inverte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = 0;
                inv="Vai";
                boolean v = false, v2 = false;
                while (v == false) {
                    if (partida.getItemAtPosition(i).equals(locald)) {
                        partida.setSelection(i);
                        v=true;
                    } else {
                        i=i+1;
                    }
                }
                ////////////////////////////////////////////////////////////

            }

        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_fretado) {
            Intent intent = new Intent(this, fretado.class);
            startActivity(intent);
        } else if (id == R.id.nav_grade) {
            Intent intent = new Intent(this, aulas.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_calendario) {
            Intent intent = new Intent(this, Calendario.class);
            startActivity(intent);
        } else if (id == R.id.nav_ru) {
            Intent intent = new Intent(this, ondecomer.class);
            startActivity(intent);
        } else if (id == R.id.nav_contador) {
            Intent intent = new Intent(this, contador.class);
            startActivity(intent);
        } else if (id == R.id.nav_bugs) {
            Intent intent = new Intent(this, bugs.class);
            startActivity(intent);
        } else if (id == R.id.nav_sobre) {
            Intent intent = new Intent(this, sobre.class);
            startActivity(intent);
        } else if (id == R.id.nav_hi) {
            Intent intent = new Intent(this, tutorial.class);
            startActivity(intent);
        } else if (id == R.id.nav_cronograma) {
            Intent intent = new Intent(this, lista_frentes.class);
            startActivity(intent);
        } else if (id == R.id.nav_equipe) {
            Intent intent = new Intent(this, equipe.class);
            startActivity(intent);
        } else if (id == R.id.nav_quality) {
            Intent intent = new Intent(this, alunos.class);
            startActivity(intent);

        } else if (id == R.id.nav_calculadora) {
            Intent intent = new Intent(this, calculadora.class);
            startActivity(intent);
        } else if (id == R.id.nav_monitoria) {
            Intent intent = new Intent(this, monitoria.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void criarbancodedados() {
        bd = openOrCreateDatabase("CentralEPUFABCDB", Context.MODE_PRIVATE, null);
        bd.execSQL("CREATE TABLE IF NOT EXISTS controle (versao INTEGER);");


        cursor = bd.query("controle", null, null, null, null, null, null);
        //esse if serve pra ver se a tabela já foi preenchida, ou seja, só vai inserir os dados quando instala o app
        //por isso quando instala e abre pela primeira vez ele demora pra abrir, tá inserindo todos esses dados
        //não consegui pensar em um jeito de melhorar isso, já que a gente precisa inserir os dados na tabela (servidor local)

        if (cursor.getCount() == 0) {

            Intent intent = new Intent(this, tutorial.class);
            startActivity(intent);
            bd.execSQL("DROP TABLE IF EXISTS fretados_semanal_1;");

            bd.execSQL("DROP TABLE IF EXISTS fretados_semanal_2_3_4_exp;");

            bd.execSQL("DROP TABLE IF EXISTS fretados_semanal_5;");

            bd.execSQL("DROP TABLE IF EXISTS fretados_sabado_1;");

            bd.execSQL("DROP TABLE IF EXISTS fretados_sabado_2;");


            bd.execSQL("DROP TABLE IF EXISTS dias;");
            //LINHAS 1, 2 E EXPRESSO QUE VÃO DE SA PRA LESTE
            //LINHA 1
            bd.execSQL("CREATE TABLE IF NOT EXISTS fretados_semanal_1 (sa_leste INTEGER, leste_sa INTEGER, linha INTEGER,indice INTEGER);");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('0','0','1','0');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('0','0','3','1');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('10000','420','1','2');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('450','455','1','3');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('480','485','1','4');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('510','515','1','5');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('540','545','1','6');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('570','575','1','7');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('600','605','1','8');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('630','635','1','9');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('660','665','1','10');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('690','695','1','11');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('720','725','1','12');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('750','755','1','13');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('780','785','1','14');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('810','815','1','15');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('840','845','1','16');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('870','875','1','17');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('900','905','1','18');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('930','935','1','19');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('960','965','1','20');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('990','995','1','21');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1020','1025','1','22');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1050','1055','1','23');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1080','1085','1','24');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1110','1115','1','25');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1140','1145','1','26');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1170','1175','1','27');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1200','1205','1','28');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1230','1235','1','29');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1260','1265','1','30');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1290','1295','1','31');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1320','1325','1','32');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1350','1355','1','33');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1380','1385','1','34');");
            //LINHAS INTERUNIDADES (2, 3 E 4)

            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('405','10000','3','35');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('430','10000','4','36');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('445','10000','2','37');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('481','10000','3','38');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('508','10000','4','39');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('535','10000','2','40');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('565','10000','3','41');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('595','10000','4','42');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('625','10000','2','43');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('651','10000','3','44');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('678','10000','4','45');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('705','10000','2','46');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('731','10000','3','47');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('758','10000','4','48');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('785','10000','2','49');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('811','10000','3','50');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('838','10000','4','51');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('865','10000','2','52');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('891','10000','3','53');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('918','10000','4','54');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('945','10000','2','55');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('970','10000','3','56');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('995','10000','4','57');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1020','10000','2','58');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1045','10000','3','59');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1070','10000','4','60');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1100','10000','2','61');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1135','10000','3','62');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1160','10000','4','63');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1200','10000','2','64');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1225','1280','3','65');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1250','1305','4','66');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1290','1340','2','67');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1315','1365','3','68');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1340','1390','4','69');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1365','1415','2','70');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1385','10000','3','71');");
            //EXPRESSO
            //6 VAI DE SA PRA SBC
            //7 DO LESTE PRA SA

            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('685','10000','6','72');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('900','10000','6','73');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1030','10000','6','74');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1235','10000','6','75');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('10000','586','7','76');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('10000','862','7','77');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('10000','1006','7','78');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('10000','1147','7','79');");

            //LESTE PARA SBC E VICE-VERSA (INTERUNIDADES)
            bd.execSQL("CREATE TABLE IF NOT EXISTS fretados_semanal_2_3_4_exp (leste_sbc INTEGER, sbc_sa INTEGER, linha INTEGER,sbc_leste INTEGER,indice INTEGER);");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('0','0','3','0','0');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('410','439','3','10000','1');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('435','464','4','10000','2');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('450','492','2','10000','3');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('486','518','3','10000','4');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('513','545','4','10000','5');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('540','569','2','10000','6');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('570','599','3','10000','7');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('600','629','4','10000','8');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('630','659','2','10000','9');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('656','685','3','10000','10');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('683','712','4','10000','11');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('710','739','2','10000','12');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('736','765','3','10000','13');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('763','792','4','10000','14');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('790','819','2','10000','15');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('816','845','3','10000','16');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('843','872','4','10000','17');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('870','899','2','10000','18');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('896','925','3','10000','19');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('923','952','4','10000','20');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('950','977','2','10000','21');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('975','1002','3','10000','22');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('1000','1027','4','10000','23');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('1025','1059','2','10000','24');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('1050','1084','3','10000','25');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('1075','1109','4','10000','26');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('1105','1149','2','10000','27');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('1140','1174','3','10000','28');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('1165','1199','4','10000','29');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('1205','1234','2','10000','30');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('1230','1259','3','1259','31');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('1255','1284','4','1284','32');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('1295','1323','2','1323','33');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('1320','1347','3','1347','34');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('1345','1372','4','1372','35');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('1370','1397','2','1397','36');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('1390','10000','3','10000','37');");

            //EXPRESSO DA MASSA
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('440','10000','7','465','38');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('486','10000','7','515','39');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('536','565','6','565','40');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('690','10000','7','725','41');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('746','10000','7','771','42');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('802','831','6','831','43');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('905','10000','7','934','44');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('955','985','6','985','45');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('1035','10000','7','1065','46');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('1091','1121','6','1121','47');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('1240','10000','7','1266','48');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('1287','10000','7','1330','49');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('1351','10000','7','1385','50');");

            //LINHA 5, SÓ SBC
            bd.execSQL("CREATE TABLE IF NOT EXISTS fretados_semanal_5 (sbc_pca INTEGER, pca_terminal INTEGER, terminal_pca INTEGER, pca_sbc INTEGER, linha INTEGER,indice INTEGER);");
            bd.execSQL("INSERT INTO fretados_semanal_5 VALUES('0','0','0','0','5','0');");
            bd.execSQL("INSERT INTO fretados_semanal_5 VALUES('0','0','395','405','5','1');");
            bd.execSQL("INSERT INTO fretados_semanal_5 VALUES('415','420','430','440','5','2');");
            bd.execSQL("INSERT INTO fretados_semanal_5 VALUES('450','455','465','475','5','3');");
            bd.execSQL("INSERT INTO fretados_semanal_5 VALUES('537','542','552','557','5','4');");
            bd.execSQL("INSERT INTO fretados_semanal_5 VALUES('604','609','619','624','5','5');");
            bd.execSQL("INSERT INTO fretados_semanal_5 VALUES('671','676','686','691','5','6');");
            bd.execSQL("INSERT INTO fretados_semanal_5 VALUES('725','730','740','745','5','7');");
            bd.execSQL("INSERT INTO fretados_semanal_5 VALUES('790','795','805','810','5','8');");
            bd.execSQL("INSERT INTO fretados_semanal_5 VALUES('857','862','872','877','5','9');");
            bd.execSQL("INSERT INTO fretados_semanal_5 VALUES('927','932','942','947','5','10');");
            bd.execSQL("INSERT INTO fretados_semanal_5 VALUES('991','996','1006','1016','5','11');");
            bd.execSQL("INSERT INTO fretados_semanal_5 VALUES('1029','1034','1044','1059','5','12');");
            bd.execSQL("INSERT INTO fretados_semanal_5 VALUES('1069','1074','1084','1097','5','13');");
            bd.execSQL("INSERT INTO fretados_semanal_5 VALUES('1103','1108','1118','1133','5','14');");
            bd.execSQL("INSERT INTO fretados_semanal_5 VALUES('1165','1170','1180','1193','5','15');");
            bd.execSQL("INSERT INTO fretados_semanal_5 VALUES('1220','1225','1235','1245','5','16');");
            bd.execSQL("INSERT INTO fretados_semanal_5 VALUES('1284','1289','1299','1304','5','17');");
            bd.execSQL("INSERT INTO fretados_semanal_5 VALUES('1330','1335','1345','1350','5','18');");
            bd.execSQL("INSERT INTO fretados_semanal_5 VALUES('1363','1368','1378','1383','5','19');");
            bd.execSQL("INSERT INTO fretados_semanal_5 VALUES('1395','1400','1410','1415','5','20');");


            //SABADO. PRA Q IR SABADO PRA PORRA DA FACULDADE?! FICA A INDAGAÇÃO
            //DIA,SA_LESTE,LESTE_SA,LESTE_SBC,SBC_LESTE,SBC_PCA,PCA_TERMINAL,TERMINAL_PCA,PCA_SBC
            bd.execSQL("CREATE TABLE IF NOT EXISTS fretados_sabado_1 (sa_leste INTEGER, leste_sa, linha INTEGER, indice INTEGER);");
            bd.execSQL("INSERT INTO fretados_sabado_1 VALUES('0','0','1','0');");
            bd.execSQL("INSERT INTO fretados_sabado_1 VALUES('465','470','1','1');");
            bd.execSQL("INSERT INTO fretados_sabado_1 VALUES('585','590','1','2');");
            bd.execSQL("INSERT INTO fretados_sabado_1 VALUES('615','620','1','3');");
            bd.execSQL("INSERT INTO fretados_sabado_1 VALUES('730','735','1','4');");
            bd.execSQL("INSERT INTO fretados_sabado_1 VALUES('825','830','1','5');");
            bd.execSQL("INSERT INTO fretados_sabado_1 VALUES('970','975','1','6');");
            bd.execSQL("INSERT INTO fretados_sabado_1 VALUES('420','505','2','7');");
            bd.execSQL("INSERT INTO fretados_sabado_1 VALUES('530','640','2','8');");
            bd.execSQL("INSERT INTO fretados_sabado_1 VALUES('650','770','2','9');");
            bd.execSQL("INSERT INTO fretados_sabado_1 VALUES('785','900','2','10');");

            //SABADO. INTERUNIDADES. FODEU A TABELA TODA ESSES DESTINOS DIFERENTES!!!!!!!!a
            bd.execSQL("CREATE TABLE IF NOT EXISTS fretados_sabado_2 (leste_term INTEGER, term_sbc INTEGER, sbc_term INTEGER, term_leste INTEGER, linha INTEGER, indice INTEGER);");
            bd.execSQL("INSERT INTO fretados_sabado_2 VALUES('0','0','0','0','2','0');");
            bd.execSQL("INSERT INTO fretados_sabado_2 VALUES('425','445','470','485','2','1');");
            bd.execSQL("INSERT INTO fretados_sabado_2 VALUES('535','560','595','615','2','2');");
            bd.execSQL("INSERT INTO fretados_sabado_2 VALUES('655','680','725','745','2','3');");
            bd.execSQL("INSERT INTO fretados_sabado_2 VALUES('790','815','855','875','2','4');");



            bd.execSQL("CREATE TABLE IF NOT EXISTS local_salvo (local text not null, indice INTEGER);");
            bd.execSQL("INSERT INTO local_salvo VALUES('Terminal leste','1');");
            bd.execSQL("INSERT INTO local_salvo VALUES('Santo André','2');");

            bd.execSQL("CREATE TABLE IF NOT EXISTS dias (desc_dia text not null,dia INTEGER,dia_mes text,mes text,status text,msg text,indice INTEGER);");
            bd.execSQL("INSERT INTO dias VALUES('Divulgação do edital do Enem','100','10','abril','notificar','Central EPUFABC','0');");
            bd.execSQL("INSERT INTO dias VALUES('Ínicio das incrições do Enem','127','08','maio','notificar','Central EPUFABC','1');");
            bd.execSQL("INSERT INTO dias VALUES('Prazo final das incrições do Enem','138','19','maio','notificar','Central EPUFABC','2');");
            bd.execSQL("INSERT INTO dias VALUES('Primeiro dia do Enem','308','05','nov','notificar','Central EPUFABC','3');");
            bd.execSQL("INSERT INTO dias VALUES('Segundo dia do Enem','315','12','nov','notificar','Central EPUFABC','4');");

            bd.execSQL("INSERT INTO controle VALUES('0');");


            bd.execSQL("CREATE TABLE IF NOT EXISTS aulas (nome_materia text not null, campus text not null, dia text not null, nome_prof text not null, sala text not null, bloco text, frequencia text not null, hora_inicio INTEGER, hora_fim INTEGER);");
            cursor = bd.query("aulas", null, null, null, null, null, null);
            //esse if serve pra ver se a tabela já foi preenchida, ou seja, só vai inserir os dados quando instala o app
            //por isso quando instala e abre pela primeira vez ele demora pra abrir, tá inserindo todos esses dados
            //não consegui pensar em um jeito de melhorar isso, já que a gente precisa inserir os dados na tabela (servidor local)

            if (cursor.getCount() == 0) {
                //TURMA TARDE 1 SA
                bd.execSQL("CREATE TABLE IF NOT EXISTS todas_turmas (nome_materia text not null, campus text not null, dia text not null, nome_prof text not null, sala text not null, bloco text, frequencia text not null, hora_inicio INTEGER, hora_fim INTEGER, turma text);");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Geografia política','Santo André','Segunda-feira','Alan','101-0','Bloco A','Semanal','1330','1420','Turma 1 - Tarde - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('História geral','Santo André','Segunda-feira','Clóvis','101-0','Bloco A','Semanal','1420','1510','Turma 1 - Tarde - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Biologia III','Santo André','Segunda-feira','Carla','101-0','Bloco A','Semanal','1510','1600','Turma 1 - Tarde - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Filosofia','Santo André','Segunda-feira','Gustavo Rios','101-0','Bloco A','Semanal','1620','1710','Turma 1 - Tarde - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Química - FQ','Santo André','Segunda-feira','Danilo','101-0','Bloco A','Semanal','1710','1800','Turma 1 - Tarde - SA');");

                bd.execSQL("INSERT INTO todas_turmas VALUES('Física mecânica','Santo André','Terça-feira','Danyela Lenz','101-0','Bloco A','Semanal','1330','1420','Turma 1 - Tarde - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Redação','Santo André','Terça-feira','Giovanna','101-0','Bloco A','Semanal','1420','1510','Turma 1 - Tarde - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Redação','Santo André','Terça-feira','Giovanna','101-0','Bloco A','Semanal','1510','1600','Turma 1 - Tarde - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Geografia física','Santo André','Terça-feira','Gabriel Carneiro','101-0','Bloco A','Semanal','1620','1710','Turma 1 - Tarde - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Artes','Santo André','Terça-feira','Nathália Torres','101-0','Bloco A','Semanal','1710','1800','Turma 1 - Tarde - SA');");

                bd.execSQL("INSERT INTO todas_turmas VALUES('Eletromagnetismo','Santo André','Quarta-feira','Renan','101-0','Bloco A','Semanal','1330','1420','Turma 1 - Tarde - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Geometria II','Santo André','Quarta-feira','Gláucia','101-0','Bloco A','Semanal','1420','1510','Turma 1 - Tarde - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Literatura','Santo André','Quarta-feira','Tainara','101-0','Bloco A','Semanal','1510','1600','Turma 1 - Tarde - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Inglês/Sociologia','Santo André','Quarta-feira','Guilherme/Gabriel','101-0','Bloco A','Semanal','1620','1710','Turma 1 - Tarde - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('História do Brasil','Santo André','Quarta-feira','Petrus','101-0','Bloco A','Semanal','1710','1800','Turma 1 - Tarde - SA');");

                bd.execSQL("INSERT INTO todas_turmas VALUES('PEM','Santo André','Quinta-feira','Gabriel','101-0','Bloco A','Semanal','1330','1420','Turma 1 - Tarde - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Física térmica','Santo André','Quinta-feira','Rodrigo','101-0','Bloco A','Semanal','1420','1510','Turma 1 - Tarde - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Geometria I','Santo André','Quinta-feira','Gyslla','101-0','Bloco A','Semanal','1510','1600','Turma 1 - Tarde - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Química orgânica','Santo André','Quinta-feira','Bruno','101-0','Bloco A','Semanal','1620','1710','Turma 1 - Tarde - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Biologia I','Santo André','Quinta-feira','Nathália','101-0','Bloco A','Semanal','1710','1800','Turma 1 - Tarde - SA');");

                bd.execSQL("INSERT INTO todas_turmas VALUES('Redação','Santo André','Sexta-feira','Giovanna','101-0','Bloco A','Semanal','1330','1420','Turma 1 - Tarde - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Álgebra II','Santo André','Sexta-feira','Diego Medeiros','101-0','Bloco A','Semanal','1420','1510','Turma 1 - Tarde - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Química inorgânica','Santo André','Sexta-feira','Paulo','101-0','Bloco A','Semanal','1510','1600','Turma 1 - Tarde - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Álgebra I','Santo André','Sexta-feira','Poliana','101-0','Bloco A','Semanal','1620','1710','Turma 1 - Tarde - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Biologia II','Santo André','Sexta-feira','Ótavio','101-0','Bloco A','Semanal','1710','1800','Turma 1 - Tarde - SA');");


                //TURMA TARDE 2 SA
                bd.execSQL("INSERT INTO todas_turmas VALUES('História geral','Santo André','Segunda-feira','Clóvis','108-0','Bloco A','Semanal','1330','1420','Turma 2 - Tarde - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Química - FQ','Santo André','Segunda-feira','Danilo','108-0','Bloco A','Semanal','1420','1510','Turma 2 - Tarde - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Filosofia','Santo André','Segunda-feira','Gustavo Rios','108-0','Bloco A','Semanal','1510','1600','Turma 2 - Tarde - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Literatura','Santo André','Segunda-feira','Tainara','108-0','Bloco A','Semanal','1620','1710','Turma 2 - Tarde - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Biologia I','Santo André','Segunda-feira','Nathalia','108-0','Bloco A','Semanal','1710','1800','Turma 2 - Tarde - SA');");

                bd.execSQL("INSERT INTO todas_turmas VALUES('Biologia III','Santo André','Terça-feira','Carla','108-0','Bloco A','Semanal','1330','1420','Turma 2 - Tarde - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Física mecânica','Santo André','Terça-feira','Danyela Lenz','108-0','Bloco A','Semanal','1420','1510','Turma 2 - Tarde - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Geografia física','Santo André','Terça-feira','Gabriel Carneiro','108-0','Bloco A','Semanal','1510','1600','Turma 2 - Tarde - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Artes','Santo André','Terça-feira','Nathália Torres','108-0','Bloco A','Semanal','1620','1710','Turma 2 - Tarde - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('História do Brasil','Santo André','Terça-feira','Petrus','108-0','Bloco A','Semanal','1710','1800','Turma 2 - Tarde - SA');");

                bd.execSQL("INSERT INTO todas_turmas VALUES('Geometria II','Santo André','Quarta-feira','Glaúcia','108-0','Bloco A','Semanal','1330','1420','Turma 2 - Tarde - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Eletromagnetismo','Santo André','Quarta-feira','Renan','108-0','Bloco A','Semanal','1420','1510','Turma 2 - Tarde - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Geografia política','Santo André','Quarta-feira','Alan','108-0','Bloco A','Semanal','1510','1600','Turma 2 - Tarde - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Inglês/Sociologia','Santo André','Quarta-feira','Guilherme/Gabriel','108-0','Bloco A','Semanal','1620','1710','Turma 2 - Tarde - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Redação','Santo André','Quarta-feira','Marcelo','108-0','Bloco A','Semanal','1710','1800','Turma 2 - Tarde - SA');");

                bd.execSQL("INSERT INTO todas_turmas VALUES('Biologia II','Santo André','Quinta-feira','Otávio','108-0','Bloco A','Semanal','1330','1420','Turma 2 - Tarde - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('PEM','Santo André','Quinta-feira','Gabriel','108-0','Bloco A','Semanal','1420','1510','Turma 2 - Tarde - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Física térmica','Santo André','Quinta-feira','Rodrigo','108-0','Bloco A','Semanal','1510','1600','Turma 2 - Tarde - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Geometria I','Santo André','Quinta-feira','Gyslla','108-0','Bloco A','Semanal','1620','1710','Turma 2 - Tarde - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Química orgânica','Santo André','Quinta-feira','Bruno','108-0','Bloco A','Semanal','1710','1800','Turma 2 - Tarde - SA');");

                bd.execSQL("INSERT INTO todas_turmas VALUES('Álgebra II','Santo André','Sexta-feira','Diego Medeiros','108-0','Bloco A','Semanal','1330','1420','Turma 2 - Tarde - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Química inorgânica','Santo André','Sexta-feira','Paulo','108-0','Bloco A','Semanal','1420','1510','Turma 2 - Tarde - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Algebra I','Santo André','Sexta-feira','Poliana','108-0','Bloco A','Semanal','1510','1600','Turma 2 - Tarde - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Redação','Santo André','Sexta-feira','Marcelo','108-0','Bloco A','Semanal','1620','1710','Turma 2 - Tarde - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Redação','Santo André','Sexta-feira','Marcelo','108-0','Bloco A','Semanal','1710','1800','Turma 2 - Tarde - SA');");

                //TURMA NOITE 1 SA
                bd.execSQL("INSERT INTO todas_turmas VALUES('Biologia III','Santo André','Segunda-feira','Carla','105-0','Bloco A','Semanal','1845','1930','Turma 1 - Noite - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Redação','Santo André','Segunda-feira','Guilherme','105-0','Bloco A','Semanal','1930','2015','Turma 1 - Noite - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Redação','Santo André','Segunda-feira','Guilherme','105-0','Bloco A','Semanal','2015','2100','Turma 1 - Noite - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Artes','Santo André','Segunda-feira','Nathália','105-0','Bloco A','Semanal','2115','2200','Turma 1 - Noite - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Química orgânica','Santo André','Segunda-feira','Rodrigo','105-0','Bloco A','Semanal','2200','2245','Turma 1 - Noite - SA');");

                bd.execSQL("INSERT INTO todas_turmas VALUES('Álgebra II','Santo André','Terça-feira','Diego Medeiros','105-0','Bloco A','Semanal','1845','1930','Turma 1 - Noite - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('História do Brasil','Santo André','Terça-feira','Natalia Maróstica','105-0','Bloco A','Semanal','1930','2015','Turma 1 - Noite - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Geometria II','Santo André','Terça-feira','Glaúcia','105-0','Bloco A','Semanal','2015','2100','Turma 1 - Noite - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('História geral','Santo André','Terça-feira','Clóvis','105-0','Bloco A','Semanal','2115','2200','Turma 1 - Noite - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Física térmica/óptica','Santo André','Terça-feira','Carlos','105-0','Bloco A','Semanal','2200','2245','Turma 1 - Noite - SA');");

                bd.execSQL("INSERT INTO todas_turmas VALUES('Algebra I','Santo André','Quarta-feira','Thales','105-0','Bloco A','Semanal','1845','1930','Turma 1 - Noite - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Literatura','Santo André','Quarta-feira','Tainara','105-0','Bloco A','Semanal','1930','2015','Turma 1 - Noite - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Biologia I','Santo André','Quarta-feira','Bárbara','105-0','Bloco A','Semanal','2015','2100','Turma 1 - Noite - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Química inorgânica','Santo André','Quarta-feira','Paulo','105-0','Bloco A','Semanal','2115','2200','Turma 1 - Noite - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Eletromagnetismo','Santo André','Quarta-feira','Renan','105-0','Bloco A','Semanal','2200','2245','Turma 1 - Noite - SA');");

                bd.execSQL("INSERT INTO todas_turmas VALUES('Redação','Santo André','Quinta-feira','Guilherme','105-0','Bloco A','Semanal','1845','1930','Turma 1 - Noite - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Inglês/Sociologia','Santo André','Quinta-feira','Guilherme/Gustavo','105-0','Bloco A','Semanal','1930','2015','Turma 1 - Noite - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Biologia II','Santo André','Quinta-feira','Otávio','105-0','Bloco A','Semanal','2015','2100','Turma 1 - Noite - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Química - FQ','Santo André','Quinta-feira','Danilo','105-0','Bloco A','Semanal','2115','2200','Turma 1 - Noite - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Geografia política','Santo André','Quinta-feira','Alan','105-0','Bloco A','Semanal','2200','2245','Turma 1 - Noite - SA');");

                bd.execSQL("INSERT INTO todas_turmas VALUES('Filosofia','Santo André','Sexta-feira','Gabriel Valim','105-0','Bloco A','Semanal','1845','1930','Turma 1 - Noite - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Física mecânica','Santo André','Sexta-feira','Danyela Lenz','105-0','Bloco A','Semanal','1930','2015','Turma 1 - Noite - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Geografia física','Santo André','Sexta-feira','Gabriel Carneiro','105-0','Bloco A','Semanal','2015','2100','Turma 1 - Noite - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('PEM','Santo André','Sexta-feira','Eduardo','105-0','Bloco A','Semanal','2115','2200','Turma 1 - Noite - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Geometria I','Santo André','Sexta-feira','Gyslla','105-0','Bloco A','Semanal','2200','2245','Turma 1 - Noite - SA');");


                //TURMA NOITE 2 SA
                bd.execSQL("INSERT INTO todas_turmas VALUES('Artes','Santo André','Segunda-feira','Nathália','206-0','Bloco A','Semanal','1845','1930','Turma 2 - Noite - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Álgebra II','Santo André','Segunda-feira','Diego Medeiros','206-0','Bloco A','Semanal','1930','2015','Turma 2 - Noite - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Biologia III','Santo André','Segunda-feira','Carla','206-0','Bloco A','Semanal','2015','2100','Turma 2 - Noite - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('História geral','Santo André','Segunda-feira','Clóvis','206-0','Bloco A','Semanal','2115','2200','Turma 2 - Noite - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Redação','Santo André','Segunda-feira','Anna Carla','206-0','Bloco A','Semanal','2200','2245','Turma 2 - Noite - SA');");

                bd.execSQL("INSERT INTO todas_turmas VALUES('História do Brasil','Santo André','Terça-feira','Petrus','206-0','Bloco A','Semanal','1845','1930','Turma 2 - Noite - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Redação','Santo André','Terça-feira','Anna Carla','206-0','Bloco A','Semanal','1930','2015','Turma 2 - Noite - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Redação','Santo André','Terça-feira','Anna Carla','206-0','Bloco A','Semanal','2015','2100','Turma 2 - Noite - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Física térmica/óptica','Santo André','Terça-feira','Carlos','206-0','Bloco A','Semanal','2115','2200','Turma 2 - Noite - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Química orgânica','Santo André','Terça-feira','Rodrigo','206-0','Bloco A','Semanal','2200','2245','Turma 2 - Noite - SA');");

                bd.execSQL("INSERT INTO todas_turmas VALUES('Biologia I','Santo André','Quarta-feira','Bárbara','206-0','Bloco A','Semanal','1845','1930','Turma 2 - Noite - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Álgebra I','Santo André','Quarta-feira','Thales','206-0','Bloco A','Semanal','1930','2015','Turma 2 - Noite - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Literatura','Santo André','Quarta-feira','Tainara','206-0','Bloco A','Semanal','2015','2100','Turma 2 - Noite - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Eletromagnetismo','Santo André','Quarta-feira','Renan','206-0','Bloco A','Semanal','2115','2200','Turma 2 - Noite - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Química inorgânica','Santo André','Quarta-feira','Paulo','206-0','Bloco A','Semanal','2200','2245','Turma 2 - Noite - SA');");

                bd.execSQL("INSERT INTO todas_turmas VALUES('Biologia II','Santo André','Quinta-feira','Otávio','206-0','Bloco A','Semanal','1845','1930','Turma 2 - Noite - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Inglês/Sociologia','Santo André','Quinta-feira','Guilherme/Gustavo','206-0','Bloco A','Semanal','1930','2015','Turma 2 - Noite - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Geometria II','Santo André','Quinta-feira','Glaúcia','206-0','Bloco A','Semanal','2015','2100','Turma 2 - Noite - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Geografia política','Santo André','Quinta-feira','Alan','206-0','Bloco A','Semanal','2115','2200','Turma 2 - Noite - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Química - FQ','Santo André','Quinta-feira','Danilo','206-0','Bloco A','Semanal','2200','2245','Turma 2 - Noite - SA');");

                bd.execSQL("INSERT INTO todas_turmas VALUES('Física mecânica','Santo André','Sexta-feira','Danyela Lenz','206-0','Bloco A','Semanal','1845','1930','Turma 2 - Noite - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Filosofia','Santo André','Sexta-feira','Gabriel Valim','206-0','Bloco A','Semanal','1930','2015','Turma 2 - Noite - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Geometria I','Santo André','Sexta-feira','Gyslla','206-0','Bloco A','Semanal','2015','2100','Turma 2 - Noite - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Geografia física','Santo André','Sexta-feira','Gabriel Carneiro','206-0','Bloco A','Semanal','2115','2200','Turma 2 - Noite - SA');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('PEM','Santo André','Sexta-feira','Eduardo','206-0','Bloco A','Semanal','2200','2245','Turma 2 - Noite - SA');");

                //TURMA 1 TARDE SBC
                bd.execSQL("INSERT INTO todas_turmas VALUES('Algebra II','São Bernardo','Segunda-feira','Lucas','A2-103','Bloco Alfa 2','Semanal','1330','1420','Turma 1 - Tarde - SBC');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('História geral','São Bernardo','Segunda-feira','Malu','A2-103','Bloco Alfa 2','Semanal','1420','1510','Turma 1 - Tarde - SBC');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Geografia política','São Bernardo','Segunda-feira','Gustavo Lemos','A2-103','Bloco Alfa 2','Semanal','1510','1600','Turma 1 - Tarde - SBC');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Geografia física','São Bernardo','Segunda-feira','Kamylle','A2-103','Bloco Alfa 2','Semanal','1620','1710','Turma 1 - Tarde - SBC');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Redação','São Bernardo','Segunda-feira','Leonardo','A2-103','Bloco Alfa 2','Semanal','1710','1800','Turma 1 - Tarde - SBC');");

                bd.execSQL("INSERT INTO todas_turmas VALUES('Química orgânica','São Bernardo','Terça-feira','Júlia','A2-103','Bloco Alfa 2','Semanal','1330','1420','Turma 1 - Tarde - SBC');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Álgebra I','São Bernardo','Terça-feira','Rodrigo Cosmo','A2-103','Bloco Alfa 2','Semanal','1420','1510','Turma 1 - Tarde - SBC');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Química orgânica','São Bernardo','Terça-feira','Jadis','A2-103','Bloco Alfa 2','Semanal','1510','1600','Turma 1 - Tarde - SBC');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('História do Brasil','São Bernardo','Terça-feira','Helen','A2-103','Bloco Alfa 2','Semanal','1620','1710','Turma 1 - Tarde - SBC');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Eletromagnetismo','São Bernardo','Terça-feira','Marcelo','A2-103','Bloco Alfa 2','Semanal','1710','1800','Turma 1 - Tarde - SBC');");

                bd.execSQL("INSERT INTO todas_turmas VALUES('Filosofia','São Bernardo','Quarta-feira','Vinícius Pintor','A2-103','Bloco Alfa 2','Semanal','1330','1420','Turma 1 - Tarde - SBC');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Redação','São Bernardo','Quarta-feira','Leonardo','A2-103','Bloco Alfa 2','Semanal','1420','1510','Turma 1 - Tarde - SBC');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Redação','São Bernardo','Quarta-feira','Leonardo','A2-103','Bloco Alfa 2','Semanal','1510','1600','Turma 1 - Tarde - SBC');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Biologia II','São Bernardo','Quarta-feira','Diego','A2-103','Bloco Alfa 2','Semanal','1620','1710','Turma 1 - Tarde - SBC');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Inglês/Sociologia','São Bernardo','Quarta-feira','Carol/Pintor','A2-103','Bloco Alfa 2','Semanal','1710','1800','Turma 1 - Tarde - SBC');");

                bd.execSQL("INSERT INTO todas_turmas VALUES('Literatura','São Bernardo','Quinta-feira','João','A2-103','Bloco Alfa 2','Semanal','1330','1420','Turma 1 - Tarde - SBC');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Geometria II','São Bernardo','Quinta-feira','Luísa Basile','A2-103','Bloco Alfa 2','Semanal','1420','1510','Turma 1 - Tarde - SBC');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Biologia III','São Bernardo','Quinta-feira','Vinícius','A2-103','Bloco Alfa 2','Semanal','1510','1600','Turma 1 - Tarde - SBC');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Física mecânica','São Bernardo','Quinta-feira','Daniel','A2-103','Bloco Alfa 2','Semanal','1620','1710','Turma 1 - Tarde - SBC');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Geometria I','São Bernardo','Quinta-feira','Luísa Cristina','A2-103','Bloco Alfa 2','Semanal','1710','1800','Turma 1 - Tarde - SBC');");

                bd.execSQL("INSERT INTO todas_turmas VALUES('Biologia I','São Bernardo','Sexta-feira','Matheus','A2-103','Bloco Alfa 2','Semanal','1330','1420','Turma 1 - Tarde - SBC');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Química - FQ','São Bernardo','Sexta-feira','Bruno','A2-103','Bloco Alfa 2','Semanal','1420','1510','Turma 1 - Tarde - SBC');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Artes','São Bernardo','Sexta-feira','Letícia','A2-103','Bloco Alfa 2','Semanal','1510','1600','Turma 1 - Tarde - SBC');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Física térmica/óptica','São Bernardo','Sexta-feira','Wesley','A2-103','Bloco Alfa 2','Semanal','1620','1710','Turma 1 - Tarde - SBC');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('PEM','São Bernardo','Sexta-feira','Luísa Basile','A2-103','Bloco Alfa 2','Semanal','1710','1800','Turma 1 - Tarde - SBC');");


                //TURMA 2 TARDE SBC
                bd.execSQL("INSERT INTO todas_turmas VALUES('História geral','São Bernardo','Segunda-feira','Malu','A2-105','Bloco Alfa 2','Semanal','1330','1420','Turma 2 - Tarde - SBC');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Álgebra II','São Bernardo','Segunda-feira','Lucas','A2-105','Bloco Alfa 2','Semanal','1420','1510','Turma 2 - Tarde - SBC');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Redação','São Bernardo','Segunda-feira','Lucas','A2-105','Bloco Alfa 2','Semanal','1510','1600','Turma 2 - Tarde - SBC');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Geografia política','São Bernardo','Segunda-feira','Gustavo Lemos','A2-105','Bloco Alfa 2','Semanal','1620','1710','Turma 2 - Tarde - SBC');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Geografia física','São Bernardo','Segunda-feira','Kamylle','A2-105','Bloco Alfa 2','Semanal','1710','1800','Turma 2 - Tarde - SBC');");

                bd.execSQL("INSERT INTO todas_turmas VALUES('Álgebra I','São Bernardo','Terça-feira','Rodrigo Cosmo','A2-105','Bloco Alfa 2','Semanal','1330','1420','Turma 2 - Tarde - SBC');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Química orgânica','São Bernardo','Terça-feira','Jadis','A2-105','Bloco Alfa 2','Semanal','1420','1510','Turma 2 - Tarde - SBC');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Química inorgânica','São Bernardo','Terça-feira','Júlia','A2-105','Bloco Alfa 2','Semanal','1510','1600','Turma 2 - Tarde - SBC');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Eletromagnetismo','São Bernardo','Terça-feira','Marcelo','A2-105','Bloco Alfa 2','Semanal','1620','1710','Turma 2 - Tarde - SBC');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('História do Brasil','São Bernardo','Terça-feira','Helen','A2-105','Bloco Alfa 2','Semanal','1710','1800','Turma 2 - Tarde - SBC');");

                bd.execSQL("INSERT INTO todas_turmas VALUES('Geometria I','São Bernardo','Quarta-feira','Luísa Cristina','A2-105','Bloco Alfa 2','Semanal','1330','1420','Turma 2 - Tarde - SBC');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Filosofia','São Bernardo','Quarta-feira','Vinícius Pintor','A2-105','Bloco Alfa 2','Semanal','1420','1510','Turma 2 - Tarde - SBC');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Biologia II','São Bernardo','Quarta-feira','Diego','A2-105','Bloco Alfa 2','Semanal','1510','1600','Turma 2 - Tarde - SBC');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Biologia I','São Bernardo','Quarta-feira','Matheus','A2-105','Bloco Alfa 2','Semanal','1620','1710','Turma 2 - Tarde - SBC');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Inglês/Sociologia','São Bernardo','Quarta-feira','Carol/Pintor','A2-105','Bloco Alfa 2','Semanal','1710','1800','Turma 2 - Tarde - SBC');");

                bd.execSQL("INSERT INTO todas_turmas VALUES('Geometria II','São Bernardo','Quinta-feira','Luísa Basile','A2-105','Bloco Alfa 2','Semanal','1330','1420','Turma 2 - Tarde - SBC');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Literatura','São Bernardo','Quinta-feira','João','A2-105','Bloco Alfa 2','Semanal','1420','1510','Turma 2 - Tarde - SBC');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Física mecânica','São Bernardo','Quinta-feira','Daniel','A2-105','Bloco Alfa 2','Semanal','1510','1600','Turma 2 - Tarde - SBC');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Biologia III','São Bernardo','Quinta-feira','Vinícius','A2-105','Bloco Alfa 2','Semanal','1620','1710','Turma 2 - Tarde - SBC');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Artes','São Bernardo','Quinta-feira','Letícia','A2-105','Bloco Alfa 2','Semanal','1710','1800','Turma 2 - Tarde - SBC');");

                bd.execSQL("INSERT INTO todas_turmas VALUES('Química - FQ','São Bernardo','Sexta-feira','Bruno','A2-105','Bloco Alfa 2','Semanal','1330','1420','Turma 2 - Tarde - SBC');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Redação','São Bernardo','Sexta-feira','Lucas','A2-105','Bloco Alfa 2','Semanal','1420','1510','Turma 2 - Tarde - SBC');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Redação','São Bernardo','Sexta-feira','Lucas','A2-105','Bloco Alfa 2','Semanal','1510','1600','Turma 2 - Tarde - SBC');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('PEM','São Bernardo','Sexta-feira','Luísa Basile','A2-105','Bloco Alfa 2','Semanal','1620','1710','Turma 2 - Tarde - SBC');");
                bd.execSQL("INSERT INTO todas_turmas VALUES('Física térmica/óptica','São Bernardo','Sexta-feira','Wesley','A2-105','Bloco Alfa 2','Semanal','1710','1800','Turma 2 - Tarde - SBC');");


                bd.execSQL("CREATE TABLE IF NOT EXISTS ra (numero text);");
                bd.execSQL("INSERT INTO ra VALUES('00201700000');");


                bd.execSQL("CREATE TABLE IF NOT EXISTS monitoria (nome text not null,detalhes text not null);");

                bd.execSQL("CREATE TABLE IF NOT EXISTS alunos (nome text not null,email text not null);");
                bd.execSQL("INSERT INTO alunos VALUES('Bianca Pintol Leite. Medicina Veterinária, Metodista','bianca_leite2008@hotmail.com');");
                bd.execSQL("INSERT INTO alunos VALUES('Renata Vital. Arquitetura e Urbanismo, Uninove','renata.vital@live.com');");
                bd.execSQL("INSERT INTO alunos VALUES('Nayra. Engenharia Civil, Metodista','nayra.martins08@gmail.com');");
                bd.execSQL("INSERT INTO alunos VALUES('Rafael Correia. BC&T, UFABC','rafael_correia41@hotmail.com');");
                bd.execSQL("INSERT INTO alunos VALUES('Léo Cardoso. Ciências Contábeis, Esags Fgv','leocardosox@outlook.com');");
                bd.execSQL("INSERT INTO alunos VALUES('Jonathan Fátimo. Farmácia, Unifesp','Jonathanfatimo@outlook.com.br');");
                bd.execSQL("INSERT INTO alunos VALUES('Kelly Maiara. Pedagogia, UEMG','kellymaiara0@gmail.com');");
                bd.execSQL("INSERT INTO alunos VALUES('Yuri. BC&T, UFABC','yuri.pimentel2007@gmail.com');");
                bd.execSQL("INSERT INTO alunos VALUES('Lubs Cristina. Arquitetura e Urbanismo, IFSP','luany.cristina@gmail.com');");
                bd.execSQL("INSERT INTO alunos VALUES('Filipe Marques. Engenharia cívil, Anhembi morumbi','filipemarques193@hotmail.com');");
                bd.execSQL("INSERT INTO alunos VALUES('Giovanna Nolli. Relações Internacionais, UFABC','giovannanollisantos@gmail.com');");
                bd.execSQL("INSERT INTO alunos VALUES('Bruna Gabriela. BC&H, UFABC','Bruuna.g135@gmail.com');");
                bd.execSQL("INSERT INTO alunos VALUES('Aline Murari. Licenciatura Plena em Geografia, Faculdade Sumaré','alinesmurari@hotmail.com');");
                bd.execSQL("INSERT INTO alunos VALUES('Wemerson Silva. Psicologia, Mackenzie','wemerson.fsilva98@gmail.com');");
                bd.execSQL("INSERT INTO alunos VALUES('Francine Oras. Engenharia agronômica, UNESP','francine.souza1008@gmail.com');");
                bd.execSQL("INSERT INTO alunos VALUES('Talissa Taglietti. Enfermagem, Universidade São Judas Tadeu','talissataglietti@gmail.com');");
                bd.execSQL("INSERT INTO alunos VALUES('Você. Curso que você quer, universidade que você quer','seuemail@gmail.com');");

                bd.execSQL("INSERT INTO alunos VALUES('Caique Santos. Engenharia de Materiais, UFCG','Caique.js13@hotmail.com');");
                bd.execSQL("INSERT INTO alunos VALUES('Jow. Medicina veterinária, USP Pirassununga','juelisonmoura@gmail.com');");
                bd.execSQL("INSERT INTO alunos VALUES('Vitória Bárbara. BC&T, UFABC','vitoriabarbaralima@gmail.com');");
                bd.execSQL("INSERT INTO alunos VALUES('Jaqueline Pereira. Direito, UNIP','jaque_pereira_lk@icloud.com');");
                bd.execSQL("INSERT INTO alunos VALUES('Kleverson Nascimento. BC&T, UFABC','nascimentokleverson@gmail.com');");
                //bd.execSQL("INSERT INTO alunos VALUES('','');");
            }

        }
    }

    public void atualizarhorafretado(String estou, String vou){
        fb = bd.query("dias", null, null, null, null, null, null);
        int a=fb.getCount();
        Toast.makeText(this,String.valueOf(a),Toast.LENGTH_SHORT).show();
        calendar = Calendar.getInstance(TimeZone.getTimeZone("Brazil/East"));
        dia = calendar.get(Calendar.DAY_OF_WEEK);
        hora_atual = calendar.get(Calendar.HOUR_OF_DAY);
        minuto_atual = calendar.get(Calendar.MINUTE);
        if (minuto_atual>9) {
            hora_refresh.setText(String.valueOf("Aplicativo atualizado às " + hora_atual + ":" + minuto_atual ));
        } else{
            hora_refresh.setText(String.valueOf("Aplicativo atualizado às " + hora_atual + ":0" + minuto_atual ));
        }
        hora_atual=hora_atual*60;//CONVERTE A HORA PRA MINUTOS
        hora_atual=hora_atual+minuto_atual;
        minuto_atual=hora_atual+60;//USA A VARIAVEL DE MINUTOS PRA IMPOR O LIMITE DE 60 MINUTOS NA BUSCA
        if(hora_atual<300){
            semfretado();
            return;
        }
        //PRIMEIRO VAMOS VER SE É DOMINGO, SE FOR NÃO TEM FRETADO, SÓ DAR A MÁ NOTÍCIA COM CARINHO
        if(dia==1){
            tempoRestanteLayout.setVisibility(View.GONE);
            minuto.setText("");
            linha.setText("Não tem fretado hoje :c");
            txt_foi.setText("Não teve fretado hoje");
            unidade.setText("");
            avancar.setAlpha(0.2f);
            voltar.setAlpha(0.2f);
            return;
        }
        //SE CHEGOU AQUI NÃO É DOMINGO, MAS SE FOR SABADO TEM UM ESQUEMA DIFERENTE, ENTÃO VAMOS VERIFICAR
        else{
            tempoRestanteLayout.setVisibility(View.VISIBLE);
            if(dia==7){
                if(estou.equals("Santo André")){
                    if(vou.equals("Terminal leste")){
                        estou="sa_leste";
                        nome_tabela="fretados_sabado_1";
                        linha_min=0;
                        linha_max=10;
                    } else{
                        estou="sa_leste";
                        nome_tabela="fretados_sabado_1";
                        linha_min=1;
                        linha_max=10;
                    }
                } else {
                    if(estou.equals("Terminal leste")){
                        if(vou.equals("Santo André")){
                            estou="leste_sa";
                            nome_tabela="fretados_sabado_1";
                            linha_min=0;
                            linha_max=10;
                        } else{
                            estou="leste_term";
                            nome_tabela="fretados_sabado_2";
                            linha_min=0;
                            linha_max=10;
                        }
                    } else{
                        if(estou.equals("Terminal São Bernardo")){
                            if(vou.equals("São Bernardo")){
                                estou="term_sbc";
                                nome_tabela="fretados_sabado_2";
                                linha_min=0;
                                linha_max=10;
                            } else{
                                estou="term_leste";
                                nome_tabela="fretados_sabado_2";
                                linha_min=0;
                                linha_max=10;
                            }
                        } else{
                            estou="sbc_term";
                            nome_tabela="fretados_sabado_2";
                            linha_min=0;
                            linha_max=10;
                        }
                    }
                }

            }
            else {
                //SE ENTRA AQUI É PQ É DIA DE SEMANA (SEG-SEXTA)
                if (estou.equals("Santo André")) {
                    if (vou.equals("Terminal leste")) {
                        estou = "sa_leste";
                        nome_tabela = "fretados_semanal_1";
                        linha_min = 0;
                        linha_max = 10;
                    } else {
                        if(vou.equals("Terminal São Bernardo")){
                            sabado();
                            return;
                        }else {
                            estou = "sa_leste";
                            nome_tabela = "fretados_semanal_1";
                            linha_min = 1;
                            linha_max = 10;
                        }
                    }
                } else {
                    if (estou.equals("Terminal leste")) {
                        if (vou.equals("Santo André")) {
                            estou = "leste_sa";
                            nome_tabela = "fretados_semanal_1";
                            linha_min = 0;
                            linha_max = 10;
                        } else {
                            if(vou.equals("Terminal São Bernardo")){
                                sabado();
                                return;
                            }
                            else {
                                estou = "leste_sbc";
                                nome_tabela = "fretados_semanal_2_3_4_exp";
                                linha_min = 0;
                                linha_max = 10;
                            }
                        }
                    } else{
                        if(estou.equals("São Bernardo")){
                            if (vou.equals("Terminal leste")){
                                estou = "sbc_leste";
                                nome_tabela = "fretados_semanal_2_3_4_exp";
                                linha_min = 0;
                                linha_max = 10;
                            } else{
                                if(vou.equals("Santo André")){
                                    estou = "sbc_sa";
                                    nome_tabela = "fretados_semanal_2_3_4_exp";
                                    linha_min = 0;
                                    linha_max = 7;
                                } else{
                                    estou = "sbc_pca";
                                    nome_tabela = "fretados_semanal_5";
                                    linha_min = 0;
                                    linha_max = 10;
                                }
                            }
                        } else{
                            if(estou.equals("Praça dos expedicionários")){
                                if(vou.equals("Terminal São Bernardo")){
                                    estou = "pca_terminal";
                                    nome_tabela = "fretados_semanal_5";
                                    linha_min = 0;
                                    linha_max = 10;
                                } else{
                                    estou = "pca_sbc";
                                    nome_tabela = "fretados_semanal_5";
                                    linha_min = 0;
                                    linha_max = 10;
                                }
                            } else{
                                if(vou.equals("Santo André") || vou.equals("Terminal leste")){
                                    sabado();
                                    return;
                                } else {
                                    estou = "terminal_pca";
                                    nome_tabela = "fretados_semanal_5";
                                    linha_min = 0;
                                    linha_max = 10;
                                }
                            }
                        }
                    }

                }
            }
            try{
                x=calendar.get(Calendar.DAY_OF_YEAR);
                if(x>35) {
                    cursor = bd.rawQuery("SELECT " + estou + ",linha FROM " + nome_tabela + " WHERE " + estou + "<'" + hora_atual + "'  AND linha>'" + linha_min + "' AND linha<'" + linha_max + "' ORDER BY " + estou + " DESC", null);
                    cursor.moveToFirst();
                    foi = hora_atual - cursor.getInt(0);
                    cursor = bd.rawQuery("SELECT " + estou + ",linha FROM " + nome_tabela + " WHERE " + estou + ">='" + hora_atual + "' AND " + estou + "<='" + minuto_atual + "' AND linha>'" + linha_min + "' AND linha<'" + linha_max + "' ORDER BY " + estou + " ASC", null);
                } else{
                    cursor = bd.rawQuery("SELECT " + estou + ",linha FROM " + nome_tabela + " WHERE " + estou + "<'" + hora_atual + "'  AND linha>'" + linha_min + "' AND linha<'" + linha_max + "' AND (linha==1 OR linha==2 OR linha==5) ORDER BY " + estou + " DESC", null);
                    cursor.moveToFirst();
                    foi = hora_atual - cursor.getInt(0);
                    cursor = bd.rawQuery("SELECT " + estou + ",linha FROM " + nome_tabela + " WHERE " + estou + ">='" + hora_atual + "' AND " + estou + "<='" + minuto_atual + "' AND linha>'" + linha_min + "' AND linha<'" + linha_max + "' AND (linha==1 OR linha==2 OR linha==5) ORDER BY " + estou + " ASC", null);
                }
                cursor.moveToFirst();
                proximo_fretado();
            }
            catch (Exception e){
                semfretado();
                return;
            }
        }
    }

    public void semfretado(){
        tempoRestanteLayout.setVisibility(View.GONE);
        minuto.setText("");
        linha.setText("Sem fretado na próxima hora :c");
        unidade.setText("");
        avancar.setAlpha(0.2f);
        voltar.setAlpha(0.2f);
        if (foi < 60 && foi>0) {
            if(foi==1){
                txt_foi.setText("O último fretado nessa linha foi há " + foi + " minuto");
            } else {
                txt_foi.setText("O último fretado nessa linha foi há " + foi + " minutos");
            }
        } else {
            txt_foi.setText("O último fretado nessa linha foi há mais de 1 hora");
        }
    }

    public void sabado(){
        tempoRestanteLayout.setVisibility(View.GONE);
        minuto.setText("");
        linha.setText("Essa linha só de sábado :c");
        unidade.setText("");
        txt_foi.setText("Esta linha não circulou hoje");
        avancar.setAlpha(0.2f);
        voltar.setAlpha(0.2f);
    }

    public void atualizar(View view){
        dia=calendar.get(Calendar.DAY_OF_WEEK);
        atualizarhorafretado(localp,locald);
        preencher_aulinhas();
    }

    public void proximo_fretado(){
        hora_atual = cursor.getInt(0)-hora_atual;
        minuto.setText(String.valueOf(hora_atual));
        pre_linha = String.valueOf(cursor.getInt(1));
        if(pre_linha.equals("6") || pre_linha.equals("7")){
            linha.setText("Expresso");
        }else {
            linha.setText("Linha " + pre_linha);
        }

        if (hora_atual > 1) {
            unidade.setText("minutos");
        } else {
            unidade.setText("minuto");
        }
        if((cursor.getPosition() + 1)==cursor.getCount())
        {
            avancar.setAlpha(0.2f);
        }else{
            avancar.setAlpha(1f);
        }


        if((cursor.getPosition())<1)
        {
            voltar.setAlpha(0.2f);
        } else{
            voltar.setAlpha(1f);
        }

        if (foi < 60 && foi>0) {
            if(foi==1){
                txt_foi.setText("O último fretado nessa linha foi há " + foi + " minuto");
            }else {
                txt_foi.setText("O último fretado nessa linha foi há " + foi + " minutos");
            }
        } else {
            txt_foi.setText("O último fretado nessa linha foi há mais de 1 hora");
        }

    }

    public void avancar_fretado(View view){
        if(avancar.getAlpha()!=0.2f) {
            if ((cursor.getPosition() + 1) < cursor.getCount()) {
                hora_atual = calendar.get(Calendar.HOUR_OF_DAY);
                minuto_atual = calendar.get(Calendar.MINUTE);
                hora_atual = hora_atual * 60;//CONVERTE A HORA PRA MINUTOS
                hora_atual = hora_atual + minuto_atual;
                cursor.moveToNext();
                proximo_fretado();
            }
        }
    }

    public void voltar_fretado(View view){
        if(voltar.getAlpha()!=0.2f) {
            if (cursor.getPosition() > 0) {
                hora_atual = calendar.get(Calendar.HOUR_OF_DAY);
                minuto_atual = calendar.get(Calendar.MINUTE);
                hora_atual = hora_atual * 60;//CONVERTE A HORA PRA MINUTOS
                hora_atual = hora_atual + minuto_atual;
                cursor.moveToPrevious();
                proximo_fretado();
            }
        }
    }

    public void inverter(){
        boolean v=false;
        int g=0;
        String passa;
        while(v==false) {
            if(localp.equals(chegada.getItemAtPosition(g))){
                chegada.setSelection(g);
                v=true;
            } else {
                g=g+1;
            }
        }
        passa=localp;
        localp=locald;
        locald=passa;
        inv="";
        atualizarhorafretado(localp,locald);
    }

    public void mais(View view){
        Intent it=new Intent(this, fretado.class);
        startActivity(it);
    }

    public  void preencher_aulinhas() {
        quinzenal.setText("Não sei");
        quinzenal.setVisibility(View.GONE);
        controle = 0;
        gerencia_aula = bd.rawQuery("SELECT * FROM aulas ORDER BY hora_inicio ASC", null);
        if (gerencia_aula.getCount() == 0) {
            nome_aula.setText("");
            hora_aula.setText("Suas aulas ainda não foram adicionadas.");
            nome_prof.setText("Informe sua turma");
            local_aula.setText("para que possamos adicionar suas aulas.");
            avancar_aula.setAlpha(0.2f);
            voltar_aula.setAlpha(0.2f);
        } else {
            dia = calendar.get(Calendar.DAY_OF_WEEK);
            if (dia == 1) {
                dia_consulta = "Domingo";
            } else {
                if (dia == 2) {
                    dia_consulta = "Segunda-feira";
                } else {
                    if (dia == 3) {
                        dia_consulta = "Terça-feira";
                    } else {
                        if (dia == 4) {
                            dia_consulta = "Quarta-feira";
                        } else {
                            if (dia == 5) {
                                dia_consulta = "Quinta-feira";
                            } else {
                                if (dia == 6) {
                                    dia_consulta = "Sexta-feira";
                                } else {
                                    if(dia==7) {
                                        dia_consulta = "Sábado";
                                    }
                                }
                            }
                        }
                    }
                }
            }

            gerencia_aula = bd.rawQuery("SELECT nome_materia,campus,nome_prof,sala,bloco,hora_inicio,hora_fim FROM aulas WHERE dia=='" + dia_consulta + "'  ORDER BY hora_inicio ASC", null);
            if (gerencia_aula.getCount() == 0) {
                nome_aula.setText("");
                hora_aula.setText("Você não tem aula hoje");
                nome_prof.setText("Que pessoa de sorte.");
                local_aula.setText("");
                avancar_aula.setAlpha(0.2f);
                voltar_aula.setAlpha(0.2f);
            } else {
                hora_atual = calendar.get(Calendar.HOUR_OF_DAY);
                hora_atual=hora_atual*100;
                hora_atual=hora_atual+calendar.get(Calendar.MINUTE);
                gerencia_aula.moveToFirst();
                while (!gerencia_aula.isLast() && controle == 0) {
                    if (gerencia_aula.getInt(5) >= hora_atual || gerencia_aula.getInt(6) > hora_atual) {
                        controle = 1;
                    } else {
                        gerencia_aula.moveToNext();
                    }
                }
                if (gerencia_aula.isLast() && controle == 0) {
                    if (gerencia_aula.getInt(5) >= hora_atual || gerencia_aula.getInt(6) > hora_atual) {
                        controle = 1;
                    }
                }
                if (controle == 1) {
                    nome_aula.setText(gerencia_aula.getString(0));
                    String conserta_minuto=String.valueOf(gerencia_aula.getInt(5)%100);
                    if(conserta_minuto.equals("0")){conserta_minuto="00";}
                    String conserta_minuto2=String.valueOf(gerencia_aula.getInt(6)%100);
                    if(conserta_minuto2.equals("0")){conserta_minuto2="00";}

                    hora_aula.setText(String.valueOf((gerencia_aula.getInt(5))/100) + "h"+conserta_minuto+" às " + String.valueOf((gerencia_aula.getInt(6))/100) + "h"+conserta_minuto2);
                    nome_prof.setText(gerencia_aula.getString(2));
                    local_aula.setText(gerencia_aula.getString(3) + ", " + gerencia_aula.getString(4) + ", " + gerencia_aula.getString(1));
                    if (gerencia_aula.isLast()) {
                        avancar_aula.setAlpha(0.2f);
                    } else {
                        avancar_aula.setAlpha(1.0f);
                    }

                    if (gerencia_aula.isFirst()) {
                        voltar_aula.setAlpha(0.2f);
                    } else {
                        voltar_aula.setAlpha(1.0f);
                    }
                } else {
                    nome_aula.setText("");
                    hora_aula.setText("Hoje suas aulas acabaram");
                    nome_prof.setText("Vá para casa ser feliz.");
                    local_aula.setText("");
                    avancar_aula.setAlpha(0.2f);
                    voltar_aula.setAlpha(1.0f);
                    gerencia_aula.moveToLast();
                }
            }
        }
    }

    public void avanca_aula(View view){
        if(avancar_aula.getAlpha()==1.0f){
            gerencia_aula.moveToNext();
            nome_aula.setText(gerencia_aula.getString(0));
            String conserta_minuto=String.valueOf(gerencia_aula.getInt(5)%100);
            if(conserta_minuto.equals("0")){conserta_minuto="00";}
            String conserta_minuto2=String.valueOf(gerencia_aula.getInt(6)%100);
            if(conserta_minuto2.equals("0")){conserta_minuto2="00";}
            hora_aula.setText(String.valueOf((gerencia_aula.getInt(5))/100) + "h"+conserta_minuto+" às " + String.valueOf((gerencia_aula.getInt(6))/100) + "h"+conserta_minuto2);
            nome_prof.setText(gerencia_aula.getString(2));
            local_aula.setText(gerencia_aula.getString(3)+", "+gerencia_aula.getString(4)+", "+gerencia_aula.getString(1));
            if(gerencia_aula.isLast()){
                avancar_aula.setAlpha(0.2f);
            }
            voltar_aula.setAlpha(1.0f);
        }
    }

    public void volta_aula(View view){
        if(voltar_aula.getAlpha()==1.0f){
            if(hora_aula.getText()!="Hoje suas aulas acabaram") {
                gerencia_aula.moveToPrevious();
            }
            nome_aula.setText(gerencia_aula.getString(0));
            String conserta_minuto=String.valueOf(gerencia_aula.getInt(5)%100);
            if(conserta_minuto.equals("0")){conserta_minuto="00";}
            String conserta_minuto2=String.valueOf(gerencia_aula.getInt(6)%100);
            if(conserta_minuto2.equals("0")){conserta_minuto2="00";}
            hora_aula.setText(String.valueOf((gerencia_aula.getInt(5))/100) + "h"+conserta_minuto+" às " + String.valueOf((gerencia_aula.getInt(6))/100) + "h"+conserta_minuto2);
            nome_prof.setText(gerencia_aula.getString(2));
            local_aula.setText(gerencia_aula.getString(3)+", "+gerencia_aula.getString(4)+", "+gerencia_aula.getString(1));
            if(gerencia_aula.isFirst()){
                voltar_aula.setAlpha(0.2f);
            }
            if(gerencia_aula.isLast()){
                avancar_aula.setAlpha(0.2f);
            }else {
                avancar_aula.setAlpha(1.0f);
            }
        }
    }

    public void abrir_grade(View view) {
        Intent intent = new Intent(this, aulas.class);
        startActivity(intent);
        finish();
    }

    public void rar(){
        TextView r;
        r=(TextView) findViewById(R.id.txt_rar);
        cursor= bd.rawQuery("SELECT numero FROM ra", null);
        cursor.moveToLast();
        r.setText("Meu RA: "+cursor.getString(0));

    }

    public void edita_rar(View view){
        Intent it=new Intent(this, ra.class);
        startActivity(it);
        finish();
    }



}