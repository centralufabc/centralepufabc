package central.centralepufabc;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class bugs extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bugs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView botaoBugs = (TextView) findViewById(R.id.botao_bugs);
        TextView botaoIdeias = (TextView) findViewById(R.id.botao_ideias);
        TextView botaoAvaliar = (TextView) findViewById(R.id.botao_avaliar);
        ImageButton backButton = (ImageButton) findViewById(R.id.back_button);

        botaoBugs.setOnClickListener(this);
        botaoIdeias.setOnClickListener(this);
        botaoAvaliar.setOnClickListener(this);
        backButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.back_button:
                finish();
                break;

            //Used to open the links
            default:
                String url = (String) view.getTag();

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);

                intent.setData(Uri.parse(url));
                startActivity(intent);
                break;
        }
    }

}
