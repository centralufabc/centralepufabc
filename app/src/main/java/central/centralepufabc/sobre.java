package central.centralepufabc;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class sobre extends AppCompatActivity  implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Kleverson Views
        ImageView linkedInKleverson = (ImageView) findViewById(R.id.linkedinKleverson);
        ImageView gitHubKleverson = (ImageView) findViewById(R.id.githubKleverson);
        ImageView facebookKleverson = (ImageView) findViewById(R.id.facebookKleverson);

        //Rodrigo Views
        ImageView linkedInRodrigo = (ImageView) findViewById(R.id.linkedinRodrigo);
        ImageView gitHubRodrigo = (ImageView) findViewById(R.id.githubRodrigo);
        ImageView facebookRodrigo = (ImageView) findViewById(R.id.facebookRodrigo);

        ImageButton backButton = (ImageButton) findViewById(R.id.back_button);

        linkedInKleverson.setOnClickListener(this);
        gitHubKleverson.setOnClickListener(this);
        facebookKleverson.setOnClickListener(this);


        linkedInRodrigo.setOnClickListener(this);
        gitHubRodrigo.setOnClickListener(this);
        facebookRodrigo.setOnClickListener(this);

        backButton.setOnClickListener(this);

        TextView logoCredito = (TextView) findViewById(R.id.logoCredito);
        logoCredito.setMovementMethod(LinkMovementMethod.getInstance());
        TextView google_icons_credit = (TextView) findViewById(R.id.google_icons_credit);
        google_icons_credit.setMovementMethod(LinkMovementMethod.getInstance());
        TextView dave_icon_credit = (TextView) findViewById(R.id.dave_icon_credit);
        dave_icon_credit.setMovementMethod(LinkMovementMethod.getInstance());
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
