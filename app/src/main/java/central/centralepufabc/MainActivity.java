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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView hora_refresh,txt_foi,unidade,linha,minuto,quinzenal,nome_aula,nome_prof,hora_aula,local_aula;
    ImageButton avancar,voltar,inverte,voltar_aula,avancar_aula;
    Spinner partida, chegada;
    SQLiteDatabase bd;
    Calendar calendar;
    Cursor cursor,gerencia_aula;
    int hora_atual,minuto_atual,dia,linha_min,linha_max, foi,x,controle;
    LinearLayout tempoRestanteLayout;
    String nome_tabela,localp,locald,pre_linha,inv,adapter,dia_consulta;

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
        } else if (id == R.id.nav_calendario) {
            Intent intent = new Intent(this, Calendario.class);
            startActivity(intent);
        } else if (id == R.id.nav_ru) {

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

        } else if (id == R.id.nav_monitoria) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void criarbancodedados() {
        bd = openOrCreateDatabase("CentralEPUFABCDB", Context.MODE_PRIVATE, null);
        bd.execSQL("CREATE TABLE IF NOT EXISTS fretados_semanal_1 (sa_leste INTEGER, leste_sa INTEGER, linha INTEGER);");
        cursor = bd.query("fretados_semanal_1", null, null, null, null, null, "sa_leste ASC");
        //esse if serve pra ver se a tabela já foi preenchida, ou seja, só vai inserir os dados quando instala o app
        //por isso quando instala e abre pela primeira vez ele demora pra abrir, tá inserindo todos esses dados
        //não consegui pensar em um jeito de melhorar isso, já que a gente precisa inserir os dados na tabela (servidor local)

        if (cursor.getCount() == 0) {

            //Intent intent = new Intent(this, Tutorial.class);
            //startActivity(intent);

            //LINHAS 1, 2 E EXPRESSO QUE VÃO DE SA PRA LESTE
            //LINHA 1
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('0','0','1');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('0','0','3');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('10000','420','1');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('430','436','1');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('446','452','1');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('462','468','1');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('478','484','1');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('494','500','1');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('510','516','1');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('526','532','1');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('542','548','1');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('558','564','1');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('610','616','1');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('662','668','1');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('714','720','1');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('730','736','1');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('746','752','1');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('762','768','1');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('780','786','1');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('810','816','1');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('862','868','1');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('914','920','1');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('976','982','1');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1028','1034','1');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1058','1064','1');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1074','1080','1');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1090','1096','1');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1106','1112','1');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1122','1128','1');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1138','1144','1');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1154','1160','1');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1206','1212','1');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1268','1274','1');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1314','1320','1');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1330','1336','1');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1346','1352','1');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1362','1368','1');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1378','1384','1');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1394','1400','1');");
            //LINHAS INTERUNIDADES (2, 3 E 4)

            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('405','460','3');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('430','485','4');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('445','515','2');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('481','541','3');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('508','568','4');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('535','590','2');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('565','620','3');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('595','650','4');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('625','680','2');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('651','706','3');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('678','733','4');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('705','760','2');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('731','786','3');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('758','813','4');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('785','840','2');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('811','866','3');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('838','893','4');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('865','920','2');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('891','946','3');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('918','973','4');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('945','995','2');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('970','1020','3');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('995','1045','4');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1020','1085','2');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1045','1110','3');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1070','1135','4');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1100','1165','2');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1135','1200','3');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1160','1225','4');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1200','1255','2');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1225','1280','3');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1250','1305','4');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1290','1340','2');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1315','1365','3');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1340','1390','4');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1365','1415','2');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1385','10000','3');");
            //EXPRESSO
            //6 VAI DE SA PRA SBC
            //7 DO LESTE PRA SA

            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('685','10000','6');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('900','10000','6');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1030','10000','6');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('1235','10000','6');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('10000','586','7');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('10000','862','7');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('10000','1006','7');");
            bd.execSQL("INSERT INTO fretados_semanal_1 VALUES('10000','1147','7');");

            //LESTE PARA SBC E VICE-VERSA (INTERUNIDADES)
            bd.execSQL("CREATE TABLE IF NOT EXISTS fretados_semanal_2_3_4_exp (leste_sbc INTEGER, sbc_leste INTEGER, linha INTEGER);");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('0','0','3');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('410','439','3');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('435','464','4');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('450','492','2');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('486','518','3');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('513','545','4');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('540','569','2');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('570','599','3');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('600','629','4');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('630','659','2');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('656','685','3');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('683','712','4');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('710','739','2');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('736','765','3');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('763','792','4');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('790','819','2');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('816','845','3');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('843','872','4');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('870','899','2');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('896','925','3');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('923','952','4');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('950','977','2');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('975','1002','3');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('1000','1027','4');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('1025','1059','2');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('1050','1084','3');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('1075','1109','4');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('1105','1149','2');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('1140','1174','3');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('1165','1199','4');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('1205','1234','2');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('1230','1259','3');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('1255','1284','4');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('1295','1323','2');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('1320','1347','3');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('1345','1372','4');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('1370','1397','2');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('1390','10000','3');");

            //EXPRESSO DA MASSA
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('440','465','7');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('486','515','7');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('536','565','6');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('690','725','7');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('746','771','7');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('802','831','6');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('905','934','7');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('955','985','6');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('1035','1065','7');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('1091','1121','6');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('1240','1266','7');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('1287','1330','7');");
            bd.execSQL("INSERT INTO fretados_semanal_2_3_4_exp VALUES('1351','1385','7');");

            //LINHA 5, SÓ SBC
            bd.execSQL("CREATE TABLE IF NOT EXISTS fretados_semanal_5 (sbc_pca INTEGER, pca_terminal INTEGER, terminal_pca INTEGER, pca_sbc INTEGER, linha INTEGER);");
            bd.execSQL("INSERT INTO fretados_semanal_5 VALUES('0','0','0','0','5');");
            bd.execSQL("INSERT INTO fretados_semanal_5 VALUES('0','0','395','405','5');");
            bd.execSQL("INSERT INTO fretados_semanal_5 VALUES('415','420','430','440','5');");
            bd.execSQL("INSERT INTO fretados_semanal_5 VALUES('450','455','465','475','5');");
            bd.execSQL("INSERT INTO fretados_semanal_5 VALUES('537','542','552','557','5');");
            bd.execSQL("INSERT INTO fretados_semanal_5 VALUES('604','609','619','624','5');");
            bd.execSQL("INSERT INTO fretados_semanal_5 VALUES('671','676','686','691','5');");
            bd.execSQL("INSERT INTO fretados_semanal_5 VALUES('725','730','740','745','5');");
            bd.execSQL("INSERT INTO fretados_semanal_5 VALUES('790','795','805','810','5');");
            bd.execSQL("INSERT INTO fretados_semanal_5 VALUES('857','862','872','877','5');");
            bd.execSQL("INSERT INTO fretados_semanal_5 VALUES('927','932','942','947','5');");
            bd.execSQL("INSERT INTO fretados_semanal_5 VALUES('991','996','1006','1016','5');");
            bd.execSQL("INSERT INTO fretados_semanal_5 VALUES('1029','1034','1044','1059','5');");
            bd.execSQL("INSERT INTO fretados_semanal_5 VALUES('1069','1074','1084','1097','5');");
            bd.execSQL("INSERT INTO fretados_semanal_5 VALUES('1103','1108','1118','1133','5');");
            bd.execSQL("INSERT INTO fretados_semanal_5 VALUES('1165','1170','1180','1193','5');");
            bd.execSQL("INSERT INTO fretados_semanal_5 VALUES('1220','1225','1235','1245','5');");
            bd.execSQL("INSERT INTO fretados_semanal_5 VALUES('1284','1289','1299','1304','5');");
            bd.execSQL("INSERT INTO fretados_semanal_5 VALUES('1330','1335','1345','1350','5');");
            bd.execSQL("INSERT INTO fretados_semanal_5 VALUES('1363','1368','1378','1383','5');");
            bd.execSQL("INSERT INTO fretados_semanal_5 VALUES('1395','1400','1410','1415','5');");


            //SABADO. PRA Q IR SABADO PRA PORRA DA FACULDADE?! FICA A INDAGAÇÃO
            //DIA,SA_LESTE,LESTE_SA,LESTE_SBC,SBC_LESTE,SBC_PCA,PCA_TERMINAL,TERMINAL_PCA,PCA_SBC
            bd.execSQL("CREATE TABLE IF NOT EXISTS fretados_sabado_1 (sa_leste INTEGER, leste_sa, linha INTEGER);");
            bd.execSQL("INSERT INTO fretados_sabado_1 VALUES('0','0','1');");
            bd.execSQL("INSERT INTO fretados_sabado_1 VALUES('465','469','1');");
            bd.execSQL("INSERT INTO fretados_sabado_1 VALUES('585','589','1');");
            bd.execSQL("INSERT INTO fretados_sabado_1 VALUES('615','619','1');");
            bd.execSQL("INSERT INTO fretados_sabado_1 VALUES('720','724','1');");
            bd.execSQL("INSERT INTO fretados_sabado_1 VALUES('825','829','1');");
            bd.execSQL("INSERT INTO fretados_sabado_1 VALUES('960','964','1');");
            bd.execSQL("INSERT INTO fretados_sabado_1 VALUES('420','505','2');");
            bd.execSQL("INSERT INTO fretados_sabado_1 VALUES('530','640','2');");
            bd.execSQL("INSERT INTO fretados_sabado_1 VALUES('650','770','2');");
            bd.execSQL("INSERT INTO fretados_sabado_1 VALUES('785','900','2');");

            //SABADO. INTERUNIDADES. FODEU A TABELA TODA ESSES DESTINOS DIFERENTES!!!!!!!!a
            bd.execSQL("CREATE TABLE IF NOT EXISTS fretados_sabado_2 (leste_term INTEGER, term_sbc INTEGER, sbc_term INTEGER, term_leste INTEGER, linha INTEGER);");
            bd.execSQL("INSERT INTO fretados_sabado_2 VALUES('0','0','0','0','2');");
            bd.execSQL("INSERT INTO fretados_sabado_2 VALUES('425','445','470','485','2');");
            bd.execSQL("INSERT INTO fretados_sabado_2 VALUES('535','560','595','615','2');");
            bd.execSQL("INSERT INTO fretados_sabado_2 VALUES('655','680','725','745','2');");
            bd.execSQL("INSERT INTO fretados_sabado_2 VALUES('790','815','855','875','2');");

            bd.execSQL("CREATE TABLE IF NOT EXISTS local_salvo (local text not null, indice INTEGER);");
            bd.execSQL("INSERT INTO local_salvo VALUES('Terminal leste','1');");
            bd.execSQL("INSERT INTO local_salvo VALUES('Santo André','2');");

            bd.execSQL("CREATE TABLE IF NOT EXISTS dias (desc_dia text not null,dia INTEGER,dia_mes text,mes text,status text,msg text);");
            bd.execSQL("INSERT INTO dias VALUES('Divulgação do edital do Enem','100','10','abril','notificar','Central EPUFABC');");
            bd.execSQL("INSERT INTO dias VALUES('Ínicio das incrições do Enem','127','08','maio','notificar','Central EPUFABC');");
            bd.execSQL("INSERT INTO dias VALUES('Prazo final das incrições do Enem','138','19','maio','notificar','Central EPUFABC');");
            bd.execSQL("INSERT INTO dias VALUES('Primeiro dia do Enem','308','05','nov','notificar','Central EPUFABC');");
            bd.execSQL("INSERT INTO dias VALUES('Segundo dia do Enem','315','12','nov','notificar','Central EPUFABC');");

            bd.execSQL("CREATE TABLE IF NOT EXISTS frentes (area text not null,frente text not null);");
            bd.execSQL("INSERT INTO frentes VALUES('Matemática','Geometria Analitica');");
            bd.execSQL("INSERT INTO frentes VALUES('Matemática','Probabilidade e estatistica');");
            bd.execSQL("INSERT INTO frentes VALUES('Matemática','Matemática básica');");
            bd.execSQL("INSERT INTO frentes VALUES('Matemática','Algebra I');");
            bd.execSQL("INSERT INTO frentes VALUES('Matemática','Algebra II');");
            bd.execSQL("INSERT INTO frentes VALUES('Redação','Redação');");

            bd.execSQL("CREATE TABLE IF NOT EXISTS item (assunto text not null,frente text not null);");
            bd.execSQL("INSERT INTO item VALUES('Potenciação','Matemática básica');");
            bd.execSQL("INSERT INTO item VALUES('Radiciação','Matemática básica');");
            bd.execSQL("INSERT INTO item VALUES('Frações','Matemática básica');");
            bd.execSQL("INSERT INTO item VALUES('Função de 1º grau','Matemática básica');");
            bd.execSQL("INSERT INTO item VALUES('Argumentação','Redação');");
            bd.execSQL("INSERT INTO item VALUES('Introdução','Redação');");
            bd.execSQL("INSERT INTO item VALUES('Desenvolvimento','Redação');");
            bd.execSQL("INSERT INTO item VALUES('Conclusão','Redação');");
            bd.execSQL("INSERT INTO item VALUES('Artigo de opinião','Redação');");

            bd.execSQL("CREATE TABLE IF NOT EXISTS aulas (nome_materia text not null, campus text not null, dia text not null, nome_prof text not null, sala text not null, bloco text, frequencia text not null, hora_inicio INTEGER, hora_fim INTEGER);");

            bd.execSQL("INSERT INTO aulas VALUES('Quimica','Santo André','Segunda-feira','Leonardo','104-0','Bloco A','Semanal','1845','1930');");
            bd.execSQL("INSERT INTO aulas VALUES('Ingles/Sociologia','Santo André','Segunda-feira','Guilherme/Rodrigo','104-0','Bloco A','Semanal','1930','2015');");
            bd.execSQL("INSERT INTO aulas VALUES('Biologia','Santo André','Segunda-feira','Carla','104-0','Bloco A','Semanal','2015','2100');");
            bd.execSQL("INSERT INTO aulas VALUES('Gramatica','Santo André','Segunda-feira','Nathalia','104-0','Bloco A','Semanal','2115','2200');");
            bd.execSQL("INSERT INTO aulas VALUES('Gramatica','Santo André','Segunda-feira','Nathalia','104-0','Bloco A','Semanal','2200','2245');");

            bd.execSQL("INSERT INTO aulas VALUES('História','Santo André','Terça-feira','Maróstica/Gustavo','104-0','Bloco A','Semanal','1845','1930');");
            bd.execSQL("INSERT INTO aulas VALUES('Quimica','Santo André','Terça-feira','RR','104-0','Bloco A','Semanal','1930','2015');");
            bd.execSQL("INSERT INTO aulas VALUES('HA/Literatura','Santo André','Terça-feira','Nathalia/Morena','104-0','Bloco A','Semanal','2015','2100');");
            bd.execSQL("INSERT INTO aulas VALUES('Redação','Santo André','Terça-feira','Anna Carla','104-0','Bloco A','Semanal','2115','2200');");
            bd.execSQL("INSERT INTO aulas VALUES('Redação','Santo André','Terça-feira','Anna Carla','104-0','Bloco A','Semanal','2200','2245');");

            bd.execSQL("INSERT INTO aulas VALUES('História','Santo André','Quarta-feira','Julio','104-0','Bloco A','Semanal','1845','1930');");
            bd.execSQL("INSERT INTO aulas VALUES('Biologia','Santo André','Quarta-feira','Vinicius','104-0','Bloco A','Semanal','1930','2015');");
            bd.execSQL("INSERT INTO aulas VALUES('Matemática','Santo André','Quarta-feira','Paulo','104-0','Bloco A','Semanal','2015','2100');");
            bd.execSQL("INSERT INTO aulas VALUES('Física','Santo André','Quarta-feira','Murillo','104-0','Bloco A','Semanal','2115','2200');");
            bd.execSQL("INSERT INTO aulas VALUES('Matemática','Santo André','Quarta-feira','Paulo Dante','104-0','Bloco A','Semanal','2200','2245');");

            bd.execSQL("INSERT INTO aulas VALUES('Biologia','Santo André','Quinta-feira','Daiane','104-0','Bloco A','Semanal','1845','1930');");
            bd.execSQL("INSERT INTO aulas VALUES('Fisica','Santo André','Quinta-feira','Renan','104-0','Bloco A','Semanal','1930','2015');");
            bd.execSQL("INSERT INTO aulas VALUES('Quimica','Santo André','Quinta-feira','Thais','104-0','Bloco A','Semanal','2015','2100');");
            bd.execSQL("INSERT INTO aulas VALUES('Fisica','Santo André','Quinta-feira','Humberto','104-0','Bloco A','Semanal','2115','2200');");
            bd.execSQL("INSERT INTO aulas VALUES('Matemática','Santo André','Quinta-feira','Passos','104-0','Bloco A','Semanal','2200','2245');");

            bd.execSQL("INSERT INTO aulas VALUES('Diego','Santo André','Sexta-feira','Diego','104-0','Bloco A','Semanal','1845','1930');");
            bd.execSQL("INSERT INTO aulas VALUES('Biologia','Santo André','Sexta-feira','Venâncio','104-0','Bloco A','Semanal','1930','2015');");
            bd.execSQL("INSERT INTO aulas VALUES('Geografia','Santo André','Sexta-feira','Gustavo','104-0','Bloco A','Semanal','2015','2100');");
            bd.execSQL("INSERT INTO aulas VALUES('Matemática','Santo André','Sexta-feira','Barbara','104-0','Bloco A','Semanal','2115','2200');");
            bd.execSQL("INSERT INTO aulas VALUES('Geografia','Santo André','Sexta-feira','Chico','104-0','Bloco A','Semanal','2200','2245');");
        }
    }

    public void atualizarhorafretado(String estou, String vou){
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
                                    estou = "sbc_leste";
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
            hora_aula.setText("Você não adicionou nenhuma aula.");
            nome_prof.setText("Adicione aulas");
            local_aula.setText("para que possamos te ajudar.");
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
    }

}
