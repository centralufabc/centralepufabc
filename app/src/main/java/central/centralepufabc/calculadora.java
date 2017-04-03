package central.centralepufabc;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class calculadora extends AppCompatActivity {
    EditText humanas,natureza,matematica,linguagens,redacao;
    TextView simples,bct,bch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        humanas=(EditText) findViewById(R.id.et_humanas);
        natureza=(EditText) findViewById(R.id.et_natureza);
        matematica=(EditText) findViewById(R.id.et_matematica);
        linguagens=(EditText) findViewById(R.id.et_linguagens);
        redacao=(EditText) findViewById(R.id.et_redacao);

        simples=(TextView) findViewById(R.id.txt_media_simples);
        bct=(TextView) findViewById(R.id.txt_media_bct);
        bch=(TextView) findViewById(R.id.txt_media_bch);

        simples.setVisibility(View.GONE);
        bct.setVisibility(View.GONE);
        bch.setVisibility(View.GONE);

        humanas.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                simples.setVisibility(View.GONE);
                bct.setVisibility(View.GONE);
                bch.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        natureza.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                simples.setVisibility(View.GONE);
                bct.setVisibility(View.GONE);
                bch.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        matematica.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                simples.setVisibility(View.GONE);
                bct.setVisibility(View.GONE);
                bch.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        redacao.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                simples.setVisibility(View.GONE);
                bct.setVisibility(View.GONE);
                bch.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        linguagens.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                simples.setVisibility(View.GONE);
                bct.setVisibility(View.GONE);
                bch.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void voltar(View view) {
        finish();
    }

    public void calc(View view){
        if(humanas.getText().toString().equals("")){
            Toast.makeText(this,"Insira a nota de ciências humanas",Toast.LENGTH_SHORT).show();
            humanas.requestFocus();
        } else if(natureza.getText().toString().equals("")){
            Toast.makeText(this,"Insira a nota de ciências da natureza ",Toast.LENGTH_SHORT).show();
            natureza.requestFocus();
        } else if(matematica.getText().toString().equals("")){
            Toast.makeText(this,"Insira a nota de matemática",Toast.LENGTH_SHORT).show();
            matematica.requestFocus();
        } else if(linguagens.getText().toString().equals("")){
            Toast.makeText(this,"Insira a nota de linguagens",Toast.LENGTH_SHORT).show();
            linguagens.requestFocus();
        } else if(redacao.getText().toString().equals("")){
            Toast.makeText(this,"Insira a nota de redação",Toast.LENGTH_SHORT).show();
            redacao.requestFocus();
        } else{
           Double nota=Double.parseDouble(humanas.getText().toString());
            nota=nota+Double.parseDouble(natureza.getText().toString());
            nota=nota+Double.parseDouble(matematica.getText().toString());
            nota=nota+Double.parseDouble(linguagens.getText().toString());
            nota=nota+Double.parseDouble(redacao.getText().toString());
            nota=nota/5;
            simples.setVisibility(View.VISIBLE);
            simples.setText("A média simples (isto é, considerando todas as áreas com peso 1) da sua nota é: "+String.format(Locale.US,"%.2f",nota));

            nota=Double.parseDouble(humanas.getText().toString());
            nota=nota+(Double.parseDouble(natureza.getText().toString()))*1.5;
            nota=nota+(Double.parseDouble(matematica.getText().toString()))*1.5;
            nota=nota+Double.parseDouble(linguagens.getText().toString());
            nota=nota+(Double.parseDouble(redacao.getText().toString()))*1.5;
            nota=nota/6.5;
            bct.setVisibility(View.VISIBLE);
            bct.setText("A média da sua nota levando em conta os pesos que a UFABC usa na seleção para o BC&T é: "+String.format(Locale.US,"%.2f",nota));

            nota=(Double.parseDouble(humanas.getText().toString()))*1.5;
            nota=nota+Double.parseDouble(natureza.getText().toString());
            nota=nota+Double.parseDouble(matematica.getText().toString());
            nota=nota+(Double.parseDouble(linguagens.getText().toString()))*1.5;
            nota=nota+(Double.parseDouble(redacao.getText().toString()))*1.5;
            nota=nota/6.5;
            bch.setVisibility(View.VISIBLE);
            bch.setText("A média da sua nota levando em conta os pesos que a UFABC usa na seleção para o BC&H é: "+ String.format(Locale.US,"%.2f",nota));

        }
    }


    }

