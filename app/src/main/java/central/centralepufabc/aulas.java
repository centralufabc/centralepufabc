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
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;

public class aulas extends AppCompatActivity {
    TextView nome_materia_segunda,horario_segunda,nome_prof_segunda,localizacao_segunda;
    TextView nome_materia_terca,horario_terca,nome_prof_terca,localizacao_terca;
    TextView nome_materia_quarta,horario_quarta,nome_prof_quarta,localizacao_quarta;
    TextView nome_materia_quinta,horario_quinta,nome_prof_quinta,localizacao_quinta;
    TextView nome_materia_sexta,horario_sexta,nome_prof_sexta,localizacao_sexta;

    ImageButton avancar_segunda,voltar_segunda;
    ImageButton avancar_terca,voltar_terca;
    ImageButton avancar_quarta,voltar_quarta;
    ImageButton avancar_quinta,voltar_quinta;
    ImageButton avancar_sexta,voltar_sexta;

    SQLiteDatabase bd;

    Cursor segunda,terca,quarta,quinta,sexta,sabado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aulas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bd = openOrCreateDatabase("CentralEPUFABCDB", Context.MODE_PRIVATE, null);

        nome_materia_segunda=(TextView) findViewById(R.id.txt_materia_segunda);
        horario_segunda=(TextView) findViewById(R.id.txt_horario_segunda);
        nome_prof_segunda=(TextView) findViewById(R.id.txt_nome_prof_segunda);
        localizacao_segunda=(TextView) findViewById(R.id.txt_localização_segunda);
        avancar_segunda=(ImageButton) findViewById(R.id.proxima_aula_segunda);
        voltar_segunda=(ImageButton) findViewById(R.id.aula_anterior_segunda);

        nome_materia_terca=(TextView) findViewById(R.id.txt_materia_terca);
        horario_terca=(TextView) findViewById(R.id.txt_horario_terca);
        nome_prof_terca=(TextView) findViewById(R.id.txt_nome_prof_terca);
        localizacao_terca=(TextView) findViewById(R.id.txt_localização_terca);
        avancar_terca=(ImageButton) findViewById(R.id.proxima_aula_terca);
        voltar_terca=(ImageButton) findViewById(R.id.aula_anterior_terca);

        nome_materia_quarta=(TextView) findViewById(R.id.txt_materia_quarta);
        horario_quarta=(TextView) findViewById(R.id.txt_horario_quarta);
        nome_prof_quarta=(TextView) findViewById(R.id.txt_nome_prof_quarta);
        localizacao_quarta=(TextView) findViewById(R.id.txt_localização_quarta);
        avancar_quarta=(ImageButton) findViewById(R.id.proxima_aula_quarta);
        voltar_quarta=(ImageButton) findViewById(R.id.aula_anterior_quarta);

        nome_materia_quinta=(TextView) findViewById(R.id.txt_materia_quinta);
        horario_quinta=(TextView) findViewById(R.id.txt_horario_quinta);
        nome_prof_quinta=(TextView) findViewById(R.id.txt_nome_prof_quinta);
        localizacao_quinta=(TextView) findViewById(R.id.txt_localização_quinta);
        avancar_quinta=(ImageButton) findViewById(R.id.proxima_aula_quinta);
        voltar_quinta=(ImageButton) findViewById(R.id.aula_anterior_quinta);

        nome_materia_sexta=(TextView) findViewById(R.id.txt_materia_sexta);
        horario_sexta=(TextView) findViewById(R.id.txt_horario_sexta);
        nome_prof_sexta=(TextView) findViewById(R.id.txt_nome_prof_sexta);
        localizacao_sexta=(TextView) findViewById(R.id.txt_localização_sexta);
        avancar_sexta=(ImageButton) findViewById(R.id.proxima_aula_sexta);
        voltar_sexta=(ImageButton) findViewById(R.id.aula_anterior_sexta);



        preencher_aulinhas();


    }

    public void voltar(View view) {
        finish();
    }

    public void preencher_aulinhas(){
        segunda = bd.rawQuery("SELECT nome_materia,campus,nome_prof,sala,bloco,hora_inicio,hora_fim FROM aulas WHERE dia=='Segunda-feira' ORDER BY hora_inicio ASC", null);
        if (segunda.getCount() == 0) {
            sem_aulinhas(nome_materia_segunda, horario_segunda, nome_prof_segunda, localizacao_segunda, avancar_segunda, voltar_segunda);
        } else {
            segunda.moveToFirst();
            com_aulinhas(segunda, nome_materia_segunda, horario_segunda, nome_prof_segunda, localizacao_segunda, avancar_segunda, voltar_segunda);
        }

        terca = bd.rawQuery("SELECT nome_materia,campus,nome_prof,sala,bloco,hora_inicio,hora_fim FROM aulas WHERE dia=='Terça-feira' ORDER BY hora_inicio ASC", null);
        if (terca.getCount() == 0) {
            sem_aulinhas(nome_materia_terca, horario_terca, nome_prof_terca, localizacao_terca, avancar_terca, voltar_terca);
        } else {
            terca.moveToFirst();
            com_aulinhas(terca, nome_materia_terca, horario_terca, nome_prof_terca, localizacao_terca, avancar_terca, voltar_terca);
        }

        quarta = bd.rawQuery("SELECT nome_materia,campus,nome_prof,sala,bloco,hora_inicio,hora_fim FROM aulas WHERE dia=='Quarta-feira' ORDER BY hora_inicio ASC", null);
        if (quarta.getCount() == 0) {
            sem_aulinhas(nome_materia_quarta, horario_quarta, nome_prof_quarta, localizacao_quarta, avancar_quarta, voltar_quarta);
        } else {
            quarta.moveToFirst();
            com_aulinhas(quarta, nome_materia_quarta, horario_quarta, nome_prof_quarta, localizacao_quarta, avancar_quarta, voltar_quarta);
        }

        quinta = bd.rawQuery("SELECT nome_materia,campus,nome_prof,sala,bloco,hora_inicio,hora_fim FROM aulas WHERE dia=='Quinta-feira' ORDER BY hora_inicio ASC", null);
        if (quinta.getCount() == 0) {
            sem_aulinhas(nome_materia_quinta, horario_quinta, nome_prof_quinta, localizacao_quinta, avancar_quinta, voltar_quinta);
        } else {
            quinta.moveToFirst();
            com_aulinhas(quinta, nome_materia_quinta, horario_quinta, nome_prof_quinta, localizacao_quinta, avancar_quinta, voltar_quinta);
        }

        sexta = bd.rawQuery("SELECT nome_materia,campus,nome_prof,sala,bloco,hora_inicio,hora_fim FROM aulas WHERE dia=='Sexta-feira' ORDER BY hora_inicio ASC", null);
        if (sexta.getCount() == 0) {
            sem_aulinhas(nome_materia_sexta, horario_sexta, nome_prof_sexta, localizacao_sexta, avancar_sexta, voltar_sexta);
        } else {
            sexta.moveToFirst();
            com_aulinhas(sexta, nome_materia_sexta, horario_sexta, nome_prof_sexta, localizacao_sexta, avancar_sexta, voltar_sexta);
        }


    }

    public  void sem_aulinhas(TextView aula,TextView hora, TextView prof, TextView local, ImageButton seta_vai, ImageButton seta_volta){
        aula.setText("");
        hora.setText("Sem aulas neste dia");
        hora.setTextSize(24);
        prof.setText("");
        local.setText("");
        seta_vai.setVisibility(View.GONE);
        seta_volta.setVisibility(View.GONE);
    }

    public void com_aulinhas(Cursor cursor,TextView aula,TextView hora, TextView prof, TextView local, ImageButton seta_vai, ImageButton seta_volta){
        hora.setTextSize(16);
        aula.setText(cursor.getString(0));
        String conserta_minuto=String.valueOf(cursor.getInt(5)%100);
        if(conserta_minuto.equals("0")){conserta_minuto="00";}
        String conserta_minuto2=String.valueOf(cursor.getInt(6)%100);
        if(conserta_minuto2.equals("0")){conserta_minuto2="00";}
        hora.setText(String.valueOf((cursor.getInt(5))/100) + "h"+conserta_minuto+" às " + String.valueOf((cursor.getInt(6))/100) + "h"+conserta_minuto2);
        prof.setText(cursor.getString(2));
        local.setText(cursor.getString(3) + ", " + cursor.getString(4) + ", " + cursor.getString(1));
        if (cursor.isLast()) {
            seta_vai.setVisibility(View.GONE);
        } else {
            seta_vai.setVisibility(View.VISIBLE);
        }

        if (cursor.isFirst()) {
            seta_volta.setVisibility(View.GONE);
        } else {
            seta_volta.setVisibility(View.VISIBLE);
        }
    }

    public void avancar_aulinha(View view){
        if(view.getId()==R.id.proxima_aula_segunda){
            segunda.moveToNext();
            com_aulinhas(segunda, nome_materia_segunda,horario_segunda,nome_prof_segunda,localizacao_segunda,avancar_segunda,voltar_segunda);
        } else {
            if(view.getId()==R.id.proxima_aula_terca){
                terca.moveToNext();
                com_aulinhas(terca, nome_materia_terca,horario_terca,nome_prof_terca,localizacao_terca,avancar_terca,voltar_terca);
            }
            else {
                if(view.getId()==R.id.proxima_aula_quarta) {
                    quarta.moveToNext();
                    com_aulinhas(quarta, nome_materia_quarta, horario_quarta, nome_prof_quarta, localizacao_quarta, avancar_quarta, voltar_quarta);
                } else{
                    if(view.getId()==R.id.proxima_aula_quinta) {
                        quinta.moveToNext();
                        com_aulinhas(quinta, nome_materia_quinta,horario_quinta,nome_prof_quinta,localizacao_quinta,avancar_quinta,voltar_quinta);
                    } else{
                        if(view.getId()==R.id.proxima_aula_sexta) {
                            sexta.moveToNext();
                            com_aulinhas(sexta, nome_materia_sexta,horario_sexta,nome_prof_sexta,localizacao_sexta,avancar_sexta,voltar_sexta);
                        }
                    }
                }
            }
        }
    }

    public void voltar_aulinha(View view){
        if(view.getId()==R.id.aula_anterior_segunda){
            segunda.moveToPrevious();
            com_aulinhas(segunda, nome_materia_segunda,horario_segunda,nome_prof_segunda,localizacao_segunda,avancar_segunda,voltar_segunda);
        } else {
            if(view.getId()==R.id.aula_anterior_terca){
                terca.moveToPrevious();
                com_aulinhas(terca, nome_materia_terca,horario_terca,nome_prof_terca,localizacao_terca,avancar_terca,voltar_terca);
            }
            else {
                if(view.getId()==R.id.aula_anterior_quarta) {
                    quarta.moveToPrevious();
                    com_aulinhas(quarta, nome_materia_quarta, horario_quarta, nome_prof_quarta, localizacao_quarta, avancar_quarta, voltar_quarta);
                } else{
                    if(view.getId()==R.id.aula_anterior_quinta) {
                        quinta.moveToPrevious();
                        com_aulinhas(quinta, nome_materia_quinta,horario_quinta,nome_prof_quinta,localizacao_quinta,avancar_quinta,voltar_quinta);
                    } else{
                        if(view.getId()==R.id.aula_anterior_sexta) {
                            sexta.moveToPrevious();
                            com_aulinhas(sexta, nome_materia_sexta,horario_sexta,nome_prof_sexta,localizacao_sexta,avancar_sexta,voltar_sexta);
                        } 
                    }
                }
            }
        }
    }

}
